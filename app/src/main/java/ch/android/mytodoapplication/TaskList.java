package ch.android.mytodoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ADD_TASK = 1;
    private AppDatabase db;
    private ListView listView;
    private TaskAdapter adapter;
    private List<Task> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        listView = findViewById(R.id.list_tasks);
        tasksList = new ArrayList<>();
        adapter = new TaskAdapter(this,tasksList);

        listView.setAdapter(adapter);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_db"
        ).build();

        loadTasks();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = tasksList.get(position);
            // Implementiere hier die Logik für die Auswahl einer Aufgabe in der Liste
        });

    }

    @Override
    public void onClick(View v) {

    }

    public void  btnAddTask(View v){
        Intent intent = new Intent(this, AddNewTask.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            loadTasks();
        }
    }

    public void onNewTask(View v) {
        Intent intent = new Intent(TaskList.this, AddNewTask.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
    }



    private void loadTasks() {
        new Thread(() -> {
            List<Task> tasks = db.taskDao().selectAll();

            runOnUiThread(() -> {
                tasksList.clear();
                tasksList.addAll(tasks);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

}

