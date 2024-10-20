package ma.ensa.mobile.devkit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.services.FrameworkService;

public class AddFrameworkActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText name, description, depend;
    private Spinner domain;
    private Button fadd, fcancel, selectImageButton;
    private ImageView frameworkImage;
    private Bitmap selectedImage;
    private FrameworkService fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_framework);

        name = findViewById(R.id.name);
        description = findViewById(R.id.descreption);
        depend = findViewById(R.id.depend);
        domain = findViewById(R.id.domain);
        fadd = findViewById(R.id.fadd);
        fcancel = findViewById(R.id.fcancel);
        frameworkImage = findViewById(R.id.frameworkImage); // Initialize the ImageView
        selectImageButton = findViewById(R.id.selectImageButton); // Initialize button for selecting image

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(); // Method to open image gallery
            }
        });

        fadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Framework framework = new Framework(0,
                        name.getText().toString(),
                        description.getText().toString(),
                        domain.getSelectedItem().toString(),
                        depend.getText().toString(),
                        selectedImage != null ? "Image added" : "No image"); // Replace with actual image path if needed
                fs = FrameworkService.getInstance(getApplicationContext());
                fs.addFramework(framework, new FrameworkService.AddFrameworkCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(AddFrameworkActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(AddFrameworkActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        fcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                frameworkImage.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
