package com.ernakh.aplicativoaula;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LerDoUsuarioActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ler_do_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void voltar(View view)
    {
        finish();
    }

    public void processarDados(View view)
    {
        String resultado = "";
        TextView textView = findViewById(R.id.txvResposta);

        EditText txtNome = findViewById(R.id.ptxNome);
        EditText txtAno = findViewById(R.id.ptxAno);

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);

        resultado = "Seu nome é " + txtNome.getText().toString() + " e você tem " + (anoAtual - Integer.parseInt((txtAno.getText().toString()))) + " anos de vida!";

        textView.setText(resultado);
    }
}