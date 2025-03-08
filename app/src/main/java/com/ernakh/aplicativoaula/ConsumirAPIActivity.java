package com.ernakh.aplicativoaula;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConsumirAPIActivity extends AppCompatActivity
{
    //https://jsonplaceholder.typicode.com/photos?id=3
    //https://picsum.photos/300/300
    private ImageView imagePhoto;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consumir_apiactivity);

        textTitle = findViewById(R.id.textTitle);
        imagePhoto = findViewById(R.id.imagePhoto);

        /*Spinner spinner = findViewById(R.id.spinner);
        Button btnCheck = findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opcaoSelecionada = spinner.getSelectedItem().toString();
                Toast.makeText(ConsumirAPIActivity.this, "Selecionado: " + opcaoSelecionada, Toast.LENGTH_SHORT).show();
            }
        });*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buscarFoto(3);
    }

    private void buscarFoto(int id) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://jsonplaceholder.typicode.com/photos?id=" + id;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API_ERROR", "Erro ao buscar foto: " + e.getMessage());
                runOnUiThread(() ->
                        Toast.makeText(ConsumirAPIActivity.this, "Erro ao carregar foto!", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();

                    // Converte o JSON para um objeto Photo
                    Photo[] photos = new Gson().fromJson(jsonResponse, Photo[].class);
                    if (photos.length > 0)
                    {
                        Photo photo = photos[0];

                        // Atualiza a UI na Thread principal
                        new Handler(Looper.getMainLooper()).post(() -> {
                            textTitle.setText(photo.getTitle());

                            // Carrega a imagem com Glide
                            Glide.with(ConsumirAPIActivity.this)
                                    .load("https://picsum.photos/300/300")//Deixei fixo pq a url das fotos da api completa ta quebrado
                                    //.load(photo.getUrl())
                                    .into(imagePhoto);
                        });
                    }
                }
            }
        });
    }

    public void voltar(View view)
    {
        finish();
    }
}