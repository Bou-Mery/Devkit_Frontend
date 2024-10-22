package ma.ensa.mobile.devkit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.mobile.devkit.adapter.FrameworkAdapter;
import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.services.FrameworkService;
import ma.ensa.mobile.devkit.swipe.SwipeToEditDeleteCallback;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_FRAMEWORK_REQUEST = 1;
    private RecyclerView rc;
    private FloatingActionButton plus;
    private FrameworkAdapter adapter;
    private FrameworkService fs;
    private List<Framework> frameworks;
    private Toolbar toolbar;

    public RecyclerView getRc() {
        return rc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rc = findViewById(R.id.rc);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        plus = findViewById(R.id.plus);

        // Set toolbar overflow icon color
        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        fs = FrameworkService.getInstance(this);
        frameworks = new ArrayList<>();

        // Fetch frameworks using the callback
        loadFrameworks();

        plus.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddFrameworkActivity.class);
            startActivityForResult(intent, ADD_FRAMEWORK_REQUEST);
        });
    }

    private void loadFrameworks() {
        fs.findAll(new FrameworkService.FrameworkCallback() {
            @Override
            public void onFrameworksLoaded(List<Framework> fetchedFrameworks) {
                frameworks.clear();
                frameworks.addAll(fetchedFrameworks);
                if (adapter == null) {
                    adapter = new FrameworkAdapter(MainActivity.this, frameworks);
                    rc.setAdapter(adapter);
                    rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    attachSwipeToDelete();
                } else {
                    adapter.notifyDataSetChanged(); // Notify adapter if it already exists
                }
            }

            @Override
            public void onError(String message) {
                Log.e("Error", "Error fetching frameworks: " + message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FRAMEWORK_REQUEST && resultCode == RESULT_OK && data != null) {
            Framework newFramework = (Framework) data.getSerializableExtra("newFramework");
            if (newFramework != null) {
                fs.addFramework(newFramework, new FrameworkService.AddFrameworkCallback() {
                    @Override
                    public void onSuccess(String message) {
                        loadFrameworks(); // Refresh the list of frameworks
                        Toast.makeText(MainActivity.this, "Framework ajouté avec succès.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error adding framework: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Error: No framework data received.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void attachSwipeToDelete() {
        SwipeToEditDeleteCallback swipeCallback = new SwipeToEditDeleteCallback(new SwipeToEditDeleteCallback.OnSwipeActionListener() {
            @Override
            public void onEdit(int position) {
                Framework framework = frameworks.get(position);
                Intent intent = new Intent(MainActivity.this, EditFrameworkActivity.class);
                intent.putExtra("id", String.valueOf(framework.getId()));
                intent.putExtra("name", framework.getName());
                intent.putExtra("descreption", framework.getDescreption());
                intent.putExtra("dependencies", framework.getDependencies());
                intent.putExtra("domain", framework.getDomain());
                startActivity(intent);
            }

            @Override
            public void onDelete(int position) {
                Framework framework = frameworks.get(position);
                fs.deleteFramework(framework.getId(), new FrameworkService.DeleteFrameworkCallback() {

                    @Override
                    public void onSuccess(String message) {
                        frameworks.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(MainActivity.this, "Deleted: " + framework.getName(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error deleting framework: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onReset(int position) {
                adapter.notifyItemChanged(position);
            }
        }, this);

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(rc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setBackgroundResource(R.drawable.searchview_bg);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
}
