package com.ernakh.aplicativoaula;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("CicloDeVida", "onCreate() chamado");
        Toast.makeText(this, "onCreate() chamado", Toast.LENGTH_SHORT).show();

        if (savedInstanceState != null) {
            // Restaura os valores do estado salvo
            int mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            int mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            // inicializa as variáveis com valores padrão
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sortearNumero(View view)
    {
        try
        {
            Random random = new Random();
            int valor = random.nextInt(100);
            TextView textView = findViewById(R.id.txvNumero);
            textView.setText(String.valueOf(valor));
        }
        catch (Exception e)
        {
            var originalException = e.getCause();
            Log.d("Erro", "Original exception: $originalException");
        }
    }

    public void carregarActivityLerUsuario(View view)
    {
        Intent intent = new Intent(MainActivity.this, LerDoUsuarioActivity.class);
        startActivity(intent);
    }

    public void carregarActivityGPS(View view)
    {
        Intent intent = new Intent(MainActivity.this, GPS_Activity.class);
        startActivity(intent);
    }

    public void carregarActivityConsumirAPI(View view)
    {
        Intent intent = new Intent(MainActivity.this, ConsumirAPIActivity.class);
        startActivity(intent);
    }

    public void carregarActivityCamera(View view)
    {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void carregarActivitySqlLite(View view)
    {
        Intent intent = new Intent(MainActivity.this, SqlLiteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("CicloDeVida", "onSaveInstanceState() chamado");

        // Salva o estado atual do jogador
        savedInstanceState.putInt(STATE_SCORE, 100);
        savedInstanceState.putInt(STATE_LEVEL, 1);
        // Invoca a super classe, para que seja possível salvar o estado
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("CicloDeVida", "onStart() chamado");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("CicloDeVida", "onRestart() chamado");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("CicloDeVida", "onResume() chamado");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("CicloDeVida", "onPause() chamado");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("CicloDeVida", "onStop() chamado");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("CicloDeVida", "onDestroy() chamado");
    }
}