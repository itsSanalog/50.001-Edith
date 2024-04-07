package com.example.edith.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import com.example.edith.R;
import com.example.edith.activities.MainActivity;
import com.example.edith.models.ToDoModel;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private List<ToDoModel> toDoList;
    private FragmentActivity toDoActivity;
    private FirebaseFirestore firestore;

    LayoutInflater mInflater;
    Context context;

    // Context object is the super class of MainActivity: See the Docs
    // constructor takes in the data class!
    public TaskAdapter(FragmentActivity toDoActivity, List<ToDoModel> toDoList){
        this.toDoList = toDoList;
        toDoActivity = toDoActivity;
        mInflater = LayoutInflater.from( toDoActivity);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /** this is pretty much standard code so we will leave it here
         *  inflates the xml layout for each list item and is ready for the data to be added */
        View itemView = mInflater.inflate(R.layout.view_task_layout, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new TaskViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //TODO 2 get the data point at position from the data source and assign it to the Viewholder
        ToDoModel toDoModel = toDoList.get(position);
        holder.taskName.setText(toDoModel.getTaskTitle());
        holder.taskDescription.setText(toDoModel.getTaskDescription());
        holder.taskDate.setText("Due On " + toDoModel.getTaskDueDate());

        holder.checkBox.setChecked(toBoolean(toDoModel.getTaskStatus()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    firestore.collection("tasks").document(toDoModel.TaskID).update("taskStatus", 1);
                }
                else {
                    firestore.collection("tasks").document(toDoModel.TaskID).update("taskStatus", 0);
                }
            }
        });
        //holder.getTaskName().setText(data.getTaskName(position));
        //holder.getTaskDescription().setText(data.getTaskDescription(position));
        //holder.getTaskDate().setText(data.getTaskDate(position));
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        // return the number of data points
        return toDoList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView taskDescription;
        private TextView taskDate;

        // private TextView taskTime;

        private CheckBox checkBox;
        private ImageView deleteTask;
        private ImageView editTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.titleTxt);
            taskDescription = itemView.findViewById(R.id.descTxt);
            taskDate = itemView.findViewById(R.id.dateTxt);
            //deleteTask = itemView.findViewById(R.id.ic_delete);
            //editTask = itemView.findViewById(R.id.ic_edit);
            //taskTime = itemView.findViewById(R.id.taskTime);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public TextView getTaskName() {
            return taskName;
        }

        public TextView getTaskDescription() {
            return taskDescription;
        }

        public TextView getTaskDate() {
            return taskDate;
        }
    }

}
