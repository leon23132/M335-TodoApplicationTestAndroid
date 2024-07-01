package ch.android.mytodoapplication;

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

                Task item = new Task(taskName, taskDescription, taskStatus);

                AppDatabase db = Room.databaseBuilder(getApplicationContext()
                                , AppDatabase.class,
                                "task_db")
                        .build();

                new Thread(() -> {
                    db.taskDao().insert(item);
                    db.close();
                    Log.d("InsertActivity", "Saved Item" + item.toString());
                    finish();
                }).start();


            }
        });


    }


    @Override
    public void onClick(View v) {

    }

    public void btnCancel(View v) {
        finish();
    }
}