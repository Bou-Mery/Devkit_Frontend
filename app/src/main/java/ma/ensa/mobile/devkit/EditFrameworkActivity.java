package ma.ensa.mobile.devkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ma.ensa.mobile.devkit.adapter.FrameworkAdapter;
import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.services.FrameworkService;

public class EditFrameworkActivity extends AppCompatActivity {

    private EditText id2, name2, descreption2, depend2;
    private Spinner domain2;
    private Button fadd2, fcancel2;
    private FrameworkService fs;
    MainActivity main ;
    private FrameworkAdapter adapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_framework);

        id2 = findViewById(R.id.id2);
        name2 = findViewById(R.id.name2);
        descreption2 = findViewById(R.id.descreption2);
        depend2 = findViewById(R.id.depend2);
        domain2 = findViewById(R.id.domain2);

        fadd2 = findViewById(R.id.fadd2);
        fcancel2 = findViewById(R.id.fcancel2);

        // Récupération des données de l'intent
        id2.setText(getIntent().getStringExtra("id"));
        id2.setEnabled(false);
        name2.setText(getIntent().getStringExtra("name"));
        descreption2.setText(getIntent().getStringExtra("descreption"));
        depend2.setText(getIntent().getStringExtra("dependencies"));

        fs = FrameworkService.getInstance(getApplicationContext());

        fadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Framework framework = new Framework(
                            Integer.parseInt(id2.getText().toString()),
                            name2.getText().toString(),
                            descreption2.getText().toString(),
                            domain2.getSelectedItem().toString(),
                            depend2.getText().toString(),
                            ""
                    );

                    // Appel de la mise à jour du framework
                    fs.updateFramework(framework, new FrameworkService.UpdateFrameworkCallback() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(EditFrameworkActivity.this, message, Toast.LENGTH_SHORT).show();
                            main.getRc().setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            finish();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(EditFrameworkActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (NumberFormatException e) {
                    Toast.makeText(EditFrameworkActivity.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fcancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
