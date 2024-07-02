package ch.android.mytodoapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Task> mTasks;

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {

        super(context, 0, tasks);
        mContext = context;
        mTasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.row_layout, parent, false);
        }


        Task currentTask = mTasks.get(position);

        TextView textName = listItemView.findViewById(R.id.textTaskName);
        TextView textStatus = listItemView.findViewById(R.id.textTaskStatus);
        Button btnDone = listItemView.findViewById(R.id.btnDone);
        Button btn_edit = listItemView.findViewById(R.id.btn_edit);


        textName.setText(currentTask.getTaskName());
        textStatus.setText(currentTask.getTaskStatus());

        btn_edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddNewTask.class);
            intent.putExtra("id", currentTask.getId());
            ((TaskList) mContext).startActivityForResult(intent, 2); // Sie können hier eine Konstante anstelle von 2 verwenden
        });


        btnDone.setOnClickListener(v -> {
            if (mContext instanceof TaskList) {
                ((TaskList) mContext).delete(currentTask);
                // Aufrufen der Methode in MainActivity
            }
        });

        return listItemView;
    }


}

