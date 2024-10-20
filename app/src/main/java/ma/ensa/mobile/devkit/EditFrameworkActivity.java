package ma.ensa.mobile.devkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.services.FrameworkService;

public class EditFrameworkActivity extends AppCompatActivity {

    private EditText id2 ,name2 , descreption2 , depend2 ;
    private Spinner domain2 ;
    private Button fadd2 , fcancel2 ;
    private FrameworkService fs;

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

        Framework framework = (Framework) getIntent().getSerializableExtra("framework");

        if (framework != null) {
            id2.setText(String.valueOf(framework.getId()));
            id2.setEnabled(false);
            name2.setText(framework.getName() != null ? framework.getName() : "");
            descreption2.setText(framework.getDescreption() != null ? framework.getDescreption() : "");
            depend2.setText(framework.getDependencies() != null ? framework.getDependencies() : "");

        } else {

            Toast.makeText(this, "Framework data is missing", Toast.LENGTH_SHORT).show();
        }


        fadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Framework framework = new Framework(
                        Integer.parseInt(id2.getText().toString()),
                        name2.getText().toString() ,
                        descreption2.getText().toString(),
                        domain2.getSelectedItem().toString(),
                        depend2.getText().toString(),
                        "" );
                fs= FrameworkService.getInstance(getApplicationContext());
                fs.updateFramework(framework, new FrameworkService.UpdateFrameworkCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(EditFrameworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(EditFrameworkActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });



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