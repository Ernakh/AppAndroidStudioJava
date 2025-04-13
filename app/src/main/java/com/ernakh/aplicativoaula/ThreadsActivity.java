package com.ernakh.aplicativoaula;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.xml.transform.Result;

public class ThreadsActivity extends AppCompatActivity {

    TextView texto;
    ProgressBar progresso;
    Button botao, btnIniciar;

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

        /*MinhaThread t = new MinhaThread();
        t.start();*/

        MinhaThread2 t2 = new MinhaThread2();
        t2.start();

        texto = findViewById(R.id.texto);
        progresso = findViewById(R.id.progresso);
        botao = findViewById(R.id.botao);

        botao.setOnClickListener(view -> {
            new MinhaTask().execute();
        });

        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MeuServico.class);
            startService(intent);
        });

        Intent intent = new Intent(this, MinhaIntentService.class);
        this.startService(intent);
    }


    class MinhaThread extends Thread {

        public void run() {
            Log.i("teste", "thread iniciada");
        }
    }

    class MinhaThread2 extends Thread {

        public void run() {
            try {
                Thread.sleep(3000);
                TextView t = (TextView) findViewById(R.id.texto);
                t.setText("Thread Iniciada");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            progresso.setProgress(values[0]);
            texto.setText("Progresso: " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            progresso.setVisibility(View.GONE);
            texto.setText(resultado);
        }
    }

    //deixar ela static ou tornar uma classe externa
    public static class MeuServico extends Service {

        private Handler handler = new Handler();
        private int segundos = 0;

        @Override
        public void onCreate() {

        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            segundos = 0;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("Service", "run...");
                    segundos++;
                    if (segundos < 10) {
                        handler.postDelayed(this, 1000);
                    } else {
                        Log.i("Service", "10 segundos depois...");
                        Toast.makeText(getApplicationContext(), "10 segundos se passaram!", Toast.LENGTH_LONG).show();
                        stopSelf();
                    }
                }
            }, 1000);


            return Service.START_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    //deixar ela static ou tornar uma classe externa
    public static class MinhaIntentService extends IntentService {

        @Override
        public void onCreate() {
            super.onCreate();
            Log.i("teste", "Intent Service criada");

        }

        @Override
        protected void onHandleIntent(Intent arg0) {
            Log.i("teste", "IntentService iniciado");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toast.makeText(getApplicationContext(), "Tarefa finalizada!", Toast.LENGTH_SHORT).show());
        }

        public MinhaIntentService() {
            super("MinhaIntentService");
        }
    }
}