package com.example.tasklistapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Credenciais fakes
    private static final String USER = "gabriel";
    private static final String PW = "123";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização do objeto SharedPreferences para armazenar dados
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Obtenção do botão de login do layout
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Configuração do listener para o clique do botão de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenção dos campos de usuário e senha do layout
                EditText editTextUsuario = findViewById(R.id.editTextUser);
                String usuarioStr = editTextUsuario.getText().toString();

                EditText editTextPW = findViewById(R.id.editTextPW2);
                String senhaStr = editTextPW.getText().toString();

                // Verificação se o usuário ou a senha estão vazios
                if (usuarioStr.equals("") || senhaStr.equals("")) {
                    Toast toast = Toast.makeText(view.getContext(), "Usuario ou senha inválida", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // Verificação das credenciais
                if (usuarioStr.equals(USER) && senhaStr.equals(PW)) {
                    // Salvar usuário no SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USER", usuarioStr);
                    editor.apply();

                    // Criar uma Intent para iniciar a próxima atividade (TarefaActivity)
                    Intent intent = new Intent(getBaseContext(), TarefaActivity.class);

                    // Inicializar a próxima atividade
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
