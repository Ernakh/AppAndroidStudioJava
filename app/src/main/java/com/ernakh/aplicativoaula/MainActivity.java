package com.ernakh.aplicativoaula;

import android.content.Intent;
import android.net.Uri;
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

    public  void eventoBotao(View view)
    {
        Toast.makeText(this, "O botão foi clicado!", Toast.LENGTH_SHORT).show();
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

    public void carregarActivityAPI_CRUD(View view)
    {
        Intent intent = new Intent(MainActivity.this, API_CRUD_Activity.class);
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

    public void carregarActivityInternacionalizacao(View view)
    {
        Intent intent = new Intent(MainActivity.this, InternacionalizacaoActivity.class);
        startActivity(intent);
    }

    public void tocarMP3(View view)
    {
        Uri link = Uri.parse("https://cdn.freesound.org/previews/130/130809_1648170-lq.mp3");
        Intent it = new Intent(Intent.ACTION_VIEW, link);
        it.setType("audio/*");
        startActivity(it);
    }

    public void verContato(View view)
    {
        /*Uri uri = Uri.parse("content://com.android.contacts/contacts/1");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);*/

        Uri uri = Uri.parse("content://com.android.contacts/contacts/");
        Intent it = new Intent(Intent.ACTION_PICK, uri);
        startActivity(it);
    }

    public void verMaps(View view)
    {
        /*Uri uriGeo = Uri.parse("geo:0,0?q=Floriano+Peixoto,Curitiba");
        Intent it = new Intent(Intent.ACTION_VIEW, uriGeo);
        startActivity(it);*/

        /*Uri uriGeo = Uri.parse("geo:-25.443195,-49.280977");
        Intent it = new Intent(Intent.ACTION_VIEW, uriGeo);
        startActivity(it);*/

        String partida = "-25.443195,-49.290877";
        String destino = "-25.442207,-49.278403";
        String url = "http://maps.google.com/maps?f=d&saddr="+partida+"&daddr="+destino+"&hl=pt";
        Uri uriGeo = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uriGeo);
        startActivity(it);

    }

    public void carregarActivityCamera(View view)
    {
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void carregarActivityEndereco(View view)
    {
        Intent intent = new Intent(MainActivity.this, ViaCepActivity.class);
        startActivity(intent);
    }

    public void carregarActivityThreads(View view)
    {
        Intent intent = new Intent(MainActivity.this, ThreadsActivity.class);
        startActivity(intent);
    }

    public void carregarActivityNotification(View view)
    {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        startActivity(intent);
    }

    public void carregarActivitySqlLite(View view)
    {
        Intent intent = new Intent(MainActivity.this, SqlLiteActivity.class);
        startActivity(intent);
    }

    public void carregarActivityLayout(View view)
    {
        Intent intent = new Intent(MainActivity.this, ScrollViewActivity.class);
        //Intent intent = new Intent(MainActivity.this, ConstraintLayoutActivity.class);
        startActivity(intent);
    }

    public void abrirNavegador(View view)
    {
        Uri uri = Uri.parse("http://www.google.com.br");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }

    public void fazerLigacao(View view)
    {
        Uri uri = Uri.parse("tel:55984093600");
        Intent it = new Intent(Intent.ACTION_CALL, uri);
        startActivity(it);
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