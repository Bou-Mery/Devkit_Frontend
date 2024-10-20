package ma.ensa.mobile.devkit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private List<Framework> frameworks; // Store the list of frameworks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rc = findViewById(R.id.rc);
        plus = findViewById(R.id.plus);

        Log.d("Step 1", "onCreate: good 1");

        fs = FrameworkService.getInstance(this);
        frameworks = new ArrayList<>(); // Initialize the frameworks list

        // Fetch frameworks using the callback
        fs.findAll(new FrameworkService.FrameworkCallback() {
            @Override
            public void onFrameworksLoaded(List<Framework> fetchedFrameworks) {
                frameworks.clear(); // Clear existing list
                frameworks.addAll(fetchedFrameworks); // Add fetched frameworks
                Log.d("Step 2", "onFrameworksLoaded: good 2");
                adapter = new FrameworkAdapter(MainActivity.this, frameworks); // Change context to MainActivity
                rc.setAdapter(adapter);
                rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                attachSwipeToDelete();
            }

            @Override
            public void onError(String message) {
                Log.e("Error", "Error fetching frameworks: " + message);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFrameworkActivity.class);
                startActivityForResult(intent, ADD_FRAMEWORK_REQUEST); // Use startActivityForResult
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FRAMEWORK_REQUEST && resultCode == RESULT_OK && data != null) {
            // Assuming that the added framework data is passed back in the Intent
            Framework newFramework = (Framework) data.getSerializableExtra("newFramework");
            if (newFramework != null) {
                frameworks.add(newFramework); // Add new framework to the list
                adapter.notifyItemInserted(frameworks.size() - 1); // Notify the adapter
                Toast.makeText(this, "Framework added: " + newFramework.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void attachSwipeToDelete() {
        SwipeToEditDeleteCallback swipeCallback = new SwipeToEditDeleteCallback(new SwipeToEditDeleteCallback.OnSwipeActionListener() {
            @Override
            public void onEdit(int position) {
                Framework framework = frameworks.get(position);
                // Handle edit logic here
            }

            @Override
            public void onDelete(int position) {
                Framework framework = frameworks.get(position);
                frameworks.remove(position); // Remove the framework from the list
                adapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Deleted: " + framework.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReset(int position) {
                adapter.notifyItemChanged(position);
            }
        }, this);

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(rc);
    }
}



















//package ma.ensa.mobile.devkit;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ma.ensa.mobile.devkit.adapter.FrameworkAdapter;
//import ma.ensa.mobile.devkit.beans.Framework;
//import ma.ensa.mobile.devkit.services.FrameworkService;
//import ma.ensa.mobile.devkit.swipe.SwipeToEditDeleteCallback;
//
//public class MainActivity extends AppCompatActivity {
//
//    private RecyclerView rc;
//    private FloatingActionButton plus;
//    private FrameworkAdapter adapter;
//    private FrameworkService fs;
//    private List<Framework> frameworks; // Store the list of frameworks
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        rc = findViewById(R.id.rc);
//        plus = findViewById(R.id.plus);
//
//        Log.d("Step 1", "onCreate: good 1");
//
//        fs = FrameworkService.getInstance(this);
//
//        // Fetch frameworks using the callback
//        fs.findAll(new FrameworkService.FrameworkCallback() {
//            @Override
//            public void onFrameworksLoaded(List<Framework> frameworks) {
//                MainActivity.this.frameworks = frameworks; // Initialize the frameworks list
//                Log.d("Step 2", "onFrameworksLoaded: good 2");
//                adapter = new FrameworkAdapter(getApplicationContext(), frameworks);
//                rc.setAdapter(adapter);
//                rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                attachSwipeToDelete();
//            }
//
//            @Override
//            public void onError(String message) {
//                Log.e("Error", "Error fetching frameworks: " + message);
//            }
//        });
//
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddFrameworkActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void attachSwipeToDelete() {
//        SwipeToEditDeleteCallback swipeCallback = new SwipeToEditDeleteCallback(new SwipeToEditDeleteCallback.OnSwipeActionListener() {
//            @Override
//            public void onEdit(int position) {
//                Framework framework = frameworks.get(position);
//                // Handle edit logic here
//            }
//
//            @Override
//            public void onDelete(int position) {
//                Framework framework = frameworks.get(position);
//                frameworks.remove(position); // Remove the framework from the list
//                adapter.notifyItemRemoved(position);
//                Toast.makeText(MainActivity.this, "Deleted: " + framework.getName(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onReset(int position) {
//                adapter.notifyItemChanged(position);
//            }
//        }, this);
//
//        new ItemTouchHelper(swipeCallback).attachToRecyclerView(rc);
//    }
//}
