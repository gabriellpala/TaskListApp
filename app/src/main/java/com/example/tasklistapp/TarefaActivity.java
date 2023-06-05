package com.example.tasklistapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TarefaActivity extends AppCompatActivity {
    private EditText taskInput;
    private ListView taskListView;
    private ArrayAdapter<String> taskAdapter;
    private ArrayList<String> taskList;

    private SharedPreferences sharedPreferences;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        // Inicialização das views do layout
        taskInput = findViewById(R.id.task_input);
        taskListView = findViewById(R.id.task_list_view);

        // Criação da lista de tarefas e do adaptador para exibição na ListView
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);

        // Configuração do listener para o clique do botão de adicionar tarefa
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        // Inicialização do SharedPreferences e obtenção do usuário atual
        sharedPreferences = getSharedPreferences("TaskListApp", MODE_PRIVATE);
        currentUser = sharedPreferences.getString("USER", "");

        // Carrega as tarefas do usuário atual do SharedPreferences
        Set<String> savedTasks = sharedPreferences.getStringSet(currentUser, new HashSet<String>());
        taskList.addAll(savedTasks);
        taskAdapter.notifyDataSetChanged();
    }

    private void addTask() {
        // Obtém a tarefa digitada pelo usuário
        String task = taskInput.getText().toString();
        if (!task.isEmpty()) {
            // Adiciona a tarefa à lista e notifica o adaptador para atualizar a exibição
            taskList.add(task);
            taskAdapter.notifyDataSetChanged();
            taskInput.setText("");
            showSnackbar("Task added: " + task);

            // Salva as tarefas do usuário atual no SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(currentUser, new HashSet<>(taskList));
            editor.apply();
        } else {
            showSnackbar("Please enter a task");
        }
    }

    private void showSnackbar(String message) {
        // Exibe uma Snackbar com a mensagem fornecida
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
