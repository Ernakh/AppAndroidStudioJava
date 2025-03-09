package com.ernakh.aplicativoaula;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class LerDoUsuarioActivity extends AppCompatActivity
{

    private List<CheckBox> checkBoxList = new ArrayList<>();

    private Spinner spinner;
    private Button btnSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ler_do_usuario);

        LinearLayout checkBoxContainer = findViewById(R.id.checkBoxContainer);
        Button btnCheck = findViewById(R.id.btnCheck);

        String[] opcoes = {"Opção 1", "Opção 2", "Opção 3", "Opção 4", "Opção 5"};

        for (String opcao : opcoes)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(opcao);
            checkBoxContainer.addView(checkBox);
            checkBoxList.add(checkBox);
        }

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selecionados = new StringBuilder("Selecionado: ");
                for (CheckBox checkBox : checkBoxList) {
                    if (checkBox.isChecked()) {
                        selecionados.append(checkBox.getText()).append(", ");
                    }
                }

                if (selecionados.toString().equals("Selecionado: ")) {
                    selecionados = new StringBuilder("Nenhuma opção selecionada!");
                } else {
                    selecionados.setLength(selecionados.length() - 2);
                }

                Toast.makeText(getApplicationContext(), selecionados.toString(), Toast.LENGTH_SHORT).show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);
        btnSpinner = findViewById(R.id.btnSpinner);

        // Criar um ArrayAdapter usando o array de strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.opcoes_spinner, android.R.layout.simple_spinner_item);

        // Define o layout para os itens do spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associa o adapter ao Spinner
        spinner.setAdapter(adapter);

        // Configuração do clique do botão
        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o item selecionado no Spinner
                String selectedItem = spinner.getSelectedItem().toString();

                // Exibe a seleção em um Toast
                Toast.makeText(LerDoUsuarioActivity.this, "Selecionado: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
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

    public void checkRadios(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(LerDoUsuarioActivity.this, "Selecione uma opção", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString();

            Toast.makeText(LerDoUsuarioActivity.this, "Selecionado: " + selectedText, Toast.LENGTH_SHORT).show();
        }
    }
}