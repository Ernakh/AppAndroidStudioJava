package com.ernakh.aplicativoaula;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100; //identificar o Intent
    private ImageView imageView;

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
            {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    imageView.setImageBitmap(photo);

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }

                    try
                    {
                        saveImageToStorage(photo);
                        Toast.makeText(this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(this, "Erro ao salvar a foto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void saveImageToStorage(Bitmap bitmap) throws IOException {

        // Gera um nome único para o arquivo
        String filename = "IMG_" + UUID.randomUUID().toString() + ".jpg";

        // Android 10 ou superior - Scoped Storage
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try (FileOutputStream out = (FileOutputStream) getContentResolver().openOutputStream(uri)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                }
                values.put(MediaStore.Images.Media.IS_PENDING, false);
                getContentResolver().update(uri, values, null, null);
            }
        } else {
            // Android 9 ou inferior
            File directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyApp");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, filename);
            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.imageView);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void abrirCamera(View view)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permissão não concedida, solicitar ao usuário
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    public void voltar(View view)
    {
        finish();
    }
}