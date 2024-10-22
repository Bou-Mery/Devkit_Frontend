package ma.ensa.mobile.devkit.swipe;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import ma.ensa.mobile.devkit.EditFrameworkActivity;
import ma.ensa.mobile.devkit.MainActivity;
import ma.ensa.mobile.devkit.adapter.FrameworkAdapter;
import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.services.FrameworkService;

public class SwipeToEditDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final Paint paint = new Paint();
    private final OnSwipeActionListener listener;
    private final Context context ;
    private FrameworkService fs ;
    MainActivity main ;
    private FrameworkAdapter adapter ;

    public interface OnSwipeActionListener {
        void onEdit(int position);
        void onDelete(int position);
        void onReset(int position);
    }

    public SwipeToEditDeleteCallback(OnSwipeActionListener listener , Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.listener = listener;
        this.context = context;
        this.fs =  FrameworkService.getInstance(context) ;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Log.d("position", "onSwiped: "+position);
        if (direction == ItemTouchHelper.LEFT) {
            showDeleteConfirmationDialog(position);
        } else if (direction == ItemTouchHelper.RIGHT) {

            listener.onEdit(position);
            resetItem(position);
        }
    }

    public void resetItem(int position) {
        listener.onReset(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        float height = itemView.getBottom() - itemView.getTop();
        float width = height / 3;

        if (dX > 0) { // Swipe vers la droite pour Edit
            paint.setColor(Color.parseColor("#01f7eb"));
            RectF background = new RectF(itemView.getLeft(), itemView.getTop(), dX, itemView.getBottom());
            canvas.drawRect(background, paint);
            drawText("Edit", canvas, itemView, width,Color.parseColor("#000000"));
        } else if (dX < 0) { // Swipe vers la gauche pour Delete
            paint.setColor(Color.parseColor("#dd5151"));
            RectF background = new RectF(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            canvas.drawRect(background, paint);
            drawText("Delete", canvas, itemView, -width, -Color.parseColor("#000000"));
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void drawText(String text, Canvas canvas, View itemView, float width, int textColor) {
        paint.setColor(textColor);
        paint.setTextSize(48);
        float textWidth = paint.measureText(text);
        float x = itemView.getLeft() + width;
        float y = itemView.getTop() + itemView.getHeight() / 2 + 20;
        canvas.drawText(text, x, y, paint);
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    listener.onDelete(position);
                    Log.d("alert", "showDeleteConfirmationDialog: "+position);
                    fs.deleteFramework(position+1, new FrameworkService.DeleteFrameworkCallback() {
                        @Override
                        public void onSuccess(String message) {

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            main.getRc().setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onError(String error) {
                            Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });



                })
                .setNegativeButton("No", (dialog, which) -> {
                    resetItem(position);
                    dialog.dismiss();

                })
                .show();
    }


}

