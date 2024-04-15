package com.example.edith.adapters;

import static java.time.format.DateTimeFormatter.ofPattern;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edith.R;
import com.example.edith.controllers.TaskController;
import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.fragments.UpdateTaskBottomFragment;
import com.example.edith.models.Task;
import com.example.edith.models.TaskRequests.deleteTaskRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private FirebaseFirestore firestore;
    DatabaseOperations db;

    LayoutInflater mInflater;
    Context context;

    // Context object is the super class of MainActivity: See the Docs
    // constructor takes in the data class!
    public TaskAdapter(Context context, DatabaseOperations db){
        FirebaseOperations dbOperations = new FirebaseOperations();
        dbOperations.setAdapter(this);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    // Tell android what/where your layout for each list item is
    // Creates an instance of the layout and TaskHolder in memory
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /** this is pretty much standard code so we will leave it here
         *  inflates the xml layout for each list item and is ready for the data to be added */
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_layout, parent, false);
        //View itemView = mInflater.inflate(R.layout.view_task_layout, parent, false);
        //firestore = FirebaseFirestore.getInstance();
        return new TaskViewHolder(itemView, this);
    }


    public void deleteTask(int position){
        // position automatically provided by RecyclerView, and should be a list? provided by db
        Task Task = db.getTask(position);

        TaskController taskController = new TaskController();
        deleteTaskRequest deleteTaskRequest = new deleteTaskRequest(Task.getEntityID());
        taskController.deleteTask(deleteTaskRequest);
        //db.removeTask(Task.getEntityID());
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        Task Task = db.getTask(position);

        Bundle bundle = new Bundle();
        bundle.putString("taskTitle", Task.getEntityTitle());
        bundle.putString("taskDescription", Task.getDescription());
        // TODO : add back after startTime is converted to String
        //bundle.putString("taskDueDate", Task.getStartTime());
        //bundle.putString("orderDate", Task.getOrderDate());
        bundle.putString("taskID", Task.getEntityID());

        UpdateTaskBottomFragment updateTask = new UpdateTaskBottomFragment();
        updateTask.setArguments(bundle);
        updateTask.show(((FragmentActivity) context).getSupportFragmentManager(), updateTask.getTag());
        //Log.i("TaskAdapter", "Task ID:" + Task.getEntityID());
    }

    @Override
    public int getItemCount() {
        // return the number of data points
        Log.d("TaskAdapter", "get size from adapter " + db.getSize());

        return db.getSize();
    }

    @Override
    // Tell android what data you want to go on which widget in the layout of the list item
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //TODO 2 get the data point at position from the data source and assign it to the Viewholder
        Task Task = db.getTask(position);
        Log.i("TaskAdapter", "The task at this position is : " + db.getTask(position).toString());
        Log.d("TaskAdapter", "The task id of this is : " + db.getTask(position).getEntityID());
        holder.taskName.setText(db.getTask(position).getEntityTitle());
        holder.taskDescription.setText(db.getTask(position).getDescription());
        holder.taskDate.setText("Do on " + db.getTask(position).getStartTime());

        boolean isChecked = Task.isCompleted();
        holder.checkBox.setChecked(isChecked);

        //holder.checkBox.setChecked(toBoolean(Task.getTaskStatus()));

        // Apply or remove the strikethrough effect based on the task's status
        if (isChecked) {
            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    db.updateTaskStatus(Task.getEntityID(), true);
                    //firestore.collection("tasks").document(Task.TaskID).update("taskStatus", 1);
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    db.updateTaskStatus(Task.getEntityID(), false);
                    //firestore.collection("tasks").document(Task.TaskID).update("taskStatus", 0);
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView taskDescription;
        private TextView taskDate;
        private CheckBox checkBox;
        private ImageView deleteTask;
        private ImageView editTask;

        public TaskViewHolder(@NonNull View itemView, TaskAdapter adapter) {
            super(itemView);
            taskName = itemView.findViewById(R.id.titleTxt);
            taskDescription = itemView.findViewById(R.id.descTxt);
            taskDate = itemView.findViewById(R.id.dateTxt);
            deleteTask = itemView.findViewById(R.id.ic_delete);
            editTask = itemView.findViewById(R.id.ic_edit);
            checkBox = itemView.findViewById(R.id.checkBox);

            deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.deleteTask(getAdapterPosition());
                }
            });

            editTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.editTask(getAdapterPosition());
                }
            });
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
