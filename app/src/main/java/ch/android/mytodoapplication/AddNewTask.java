package ch.android.mytodoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputEditText;

public class AddNewTask extends AppCompatActivity implements View.OnClickListener {

    private Task item;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_db").build();

        Intent givenIntent = getIntent();
        int item_id = givenIntent.getIntExtra("id", -1);

        if (item_id > 0) {
            new Thread(() -> {
                item = db.taskDao().selectById(item_id);
                runOnUiThread(() -> loadEditData(item));
            }).start();
        }

        //Find The Inputs
        TextInputEditText txtTaskName = findViewById(R.id.task_name);
        TextInputEditText txtTaskDescription = findViewById(R.id.task_description);
        TextInputEditText txtTaskStatus = findViewById(R.id.task_status);

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = txtTaskName.getText().toString();
                String taskDescription = txtTaskDescription.getText().toString();
                String taskStatus = txtTaskStatus.getText().toString();

                if (item == null) {
                    item = new Task(taskName, taskDescription, taskStatus);
                } else {
                    item.setTaskName(taskName);
                    item.setTaskDescription(taskDescription);
                    item.setTaskStatus(taskStatus);
                }

                new Thread(() -> {
                    if (item.getId() > 0) {
                        db.taskDao().update(item);
                        Log.d("UpdateActivity", "Updated Item: " + item.toString());
                    } else {
                        db.taskDao().insert(item);
                        Log.d("InsertActivity", "Saved Item: " + item.toString());
                    }
                    db.close();
                    setResult(RESULT_OK);
                    finish();
                }).start();
            }
        });
    }

    public void loadEditData(Task task) {
        TextInputEditText txtTaskName = findViewById(R.id.task_name);
        TextInputEditText txtTaskDescription = findViewById(R.id.task_description);
        TextInputEditText txtTaskStatus = findViewById(R.id.task_status);

        txtTaskName.setText(task.getTaskName());
        txtTaskDescription.setText(task.getTaskDescription());
        txtTaskStatus.setText(task.getTaskStatus());
        item = task;
    }

    @Override
    public void onClick(View v) {
        // Implementiere hier die Logik für Klickereignisse, falls erforderlich
    }

    public void btnCancel(View v) {
        finish();
    }
}