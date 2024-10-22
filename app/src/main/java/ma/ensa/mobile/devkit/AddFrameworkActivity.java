package ma.ensa.mobile.devkit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ma.ensa.mobile.devkit.adapter.FrameworkAdapter;
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
    private FrameworkAdapter adapter ;
    MainActivity main ;
private String encodeImageToBase64(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
    byte[] byteArray = byteArrayOutputStream.toByteArray();
    // Prepend the MIME type for a valid base64 format
    return "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
}

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
        frameworkImage = findViewById(R.id.frameworkImage);
        selectImageButton = findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        fadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageBase64 = selectedImage != null ? encodeImageToBase64(selectedImage) : null;

                Framework framework = new Framework(0,
                        name.getText().toString(),
                        description.getText().toString(),
                        domain.getSelectedItem().toString(),
                        depend.getText().toString(),
                        imageBase64);

                fs = FrameworkService.getInstance(getApplicationContext());
                fs.addFramework(framework, new FrameworkService.AddFrameworkCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(AddFrameworkActivity.this, message, Toast.LENGTH_LONG).show();
                        main.getRc().setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        finish();

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
