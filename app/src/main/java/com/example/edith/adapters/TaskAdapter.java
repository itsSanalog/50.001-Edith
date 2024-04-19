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
import com.example.edith.models.CalendarEntities.Task;
import com.example.edith.models.TaskRequests.deleteTaskRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * TaskAdapter is a RecyclerView.Adapter that binds the data to the RecyclerView.
 * It handles the deletion and editing of tasks.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private FirebaseFirestore firestore;
    DatabaseOperations db;

    LayoutInflater mInflater;
    Context context;

    /**
     * Constructor for TaskAdapter.
     * @param context The context in which the TaskAdapter is used.
     * @param db The database operations instance.
     */
    public TaskAdapter(Context context, DatabaseOperations db){
        FirebaseOperations dbOperations = FirebaseOperations.getInstance();
        dbOperations.setAdapter(this);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_layout, parent, false);
        return new TaskViewHolder(itemView, this);
    }

    /**
     * Deletes a task from the RecyclerView and the database.
     * @param position The position of the task in the RecyclerView.
     */
    public void deleteTask(int position){
        Task Task = db.getTask(position);
        TaskController taskController = new TaskController();
        deleteTaskRequest deleteTaskRequest = new deleteTaskRequest(db.getTask(position).getEntityID());
        taskController.deleteTask(deleteTaskRequest);
        notifyItemRemoved(position);
    }

    /**
     * Opens the UpdateTaskBottomFragment for the task at the given position.
     * @param position The position of the task in the RecyclerView.
     */
    public void editTask(int position){
        Task Task = db.getTask(position);

        Bundle bundle = new Bundle();
        bundle.putString("taskTitle", db.getTask(position).getEntityTitle());
        bundle.putString("taskDescription", db.getTask(position).getDescription());
        bundle.putString("taskDueDate", db.getTask(position).getDate());
        bundle.putString("taskDeadlineTime", db.getTask(position).getTime());
        bundle.putString("id", db.getTask(position).getEntityID());

        UpdateTaskBottomFragment updateTask = new UpdateTaskBottomFragment();
        updateTask.setArguments(bundle);

        updateTask.show(((FragmentActivity) context).getSupportFragmentManager(), updateTask.getTag());
    }

    @Override
    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    public int getItemCount() {
        return db.getSize();
    }

    @Override
    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task Task = db.getTask(position);

        holder.taskName.setText(db.getTask(position).getEntityTitle());
        holder.taskDescription.setText(db.getTask(position).getDescription());
        holder.taskDate.setText("Do on " + db.getTask(position).convertStartDate());

        boolean isChecked = Task.isCompleted();
        holder.checkBox.setChecked(isChecked);

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
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    db.updateTaskStatus(Task.getEntityID(), false);
                    holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView taskDescription;
        private TextView taskDate;
        private CheckBox checkBox;
        private ImageView deleteTask;
        private ImageView editTask;

        /**
         * Constructor for TaskViewHolder.
         * @param itemView The view that represents the data.
         * @param adapter The adapter that binds the data to the RecyclerView.
         */
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