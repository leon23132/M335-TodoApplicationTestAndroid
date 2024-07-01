package ch.android.mytodoapplication;

import android.content.Context;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.row_layout, parent, false);
        }

        Task currentTask = mTasks.get(position);

        TextView textName = listItemView.findViewById(R.id.textTask_Name);
        TextView textStatus = listItemView.findViewById(R.id.textTaskStatus);
        Button btnDone = listItemView.findViewById(R.id.btnDone);

        textName.setText(currentTask.getTaskName());
        textStatus.setText(currentTask.getTaskStatus());


        btnDone.setOnClickListener(v -> {
            // Implementiere die Logik für den Done-Button hier
            // Beispiel: Markieren der Aufgabe als erledigt oder Löschen aus der Liste
        });

        return listItemView;
    }


}

