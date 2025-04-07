package com.ernakh.aplicativoaula;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.xml.transform.Result;

public class ThreadsActivity extends AppCompatActivity {

    TextView texto;
    ProgressBar progresso;
    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_threads);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MinhaThread t = new MinhaThread();
        t.start();

        texto = findViewById(R.id.texto);
        progresso = findViewById(R.id.progresso);
        botao = findViewById(R.id.botao);

        botao.setOnClickListener(view -> {
            new MinhaTask().execute();
        });
    }


    class MinhaThread extends Thread {

        public void run() {
            Log.i("teste", "thread iniciada");
        }
    }

    class MinhaTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresso.setVisibility(View.VISIBLE);
            texto.setText("Iniciando...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i * 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Concluído!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            texto.setText("Progresso: " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            progresso.setVisibility(View.GONE);
            texto.setText(resultado);
        }
    }
}