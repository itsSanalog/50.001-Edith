package com.example.edith.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edith.R;
import com.example.edith.controllers.TaskController;
import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.TaskRequests.addTaskRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddTaskBottomFragment extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private String id = "";
    private EditText addTaskTitle;
    private EditText addTaskDescription;
    private TextView addTaskDate;
    private Button addButton;
    private NumberPicker addDuration;
    private Context context;
    private String dueDate = "";

    // TODO: Do not know if it is necessary
    private String dueDateUpdate = "";
    private FirebaseFirestore firestore;
    private String orderDate = "";
    private String orderDateUpdate = "";

    private static AddTaskBottomFragment newInstance() {
        return new AddTaskBottomFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view with the layout;
        View view = inflater.inflate(R.layout.activity_main_bottom_sheet_layout, container, false);
        // Configure UI elements
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("taskBundle", getArguments());
    }

    @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTaskTitle = view.findViewById(R.id.addTaskTitle);
        addTaskDescription = view.findViewById(R.id.addTaskDescription);
        addTaskDate = view.findViewById(R.id.addDueDateTxt);
        addDuration = view.findViewById(R.id.durationPicker);
        addButton = view.findViewById(R.id.addTaskButton);

        DatabaseOperations db = FirebaseOperations.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Configure the Duration NumberPicker
        addDuration.setMinValue(1); // Minimum duration
        addDuration.setMaxValue(120); // Maximum duration


        // TODO: Not necessary for AddTaskBottom Fragment check with Andrew
        // Configuring the Update Task
        boolean isUpdate = false;
        Bundle bundle;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("taskBundle");
        } else {
            bundle = getArguments();
        }
        //final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String taskTitle = bundle.getString("taskTitle");
            String taskDesc = bundle.getString("taskDescription");
            id = bundle.getString("taskID");
            dueDateUpdate = bundle.getString("taskDueDate");
            orderDateUpdate = bundle.getString("orderDate");

            addTaskTitle.setText(taskTitle);
            addTaskDescription.setText(taskDesc);
            addTaskDate.setText(dueDateUpdate);

            if (taskTitle.length() > 0){
                addButton.setEnabled(false);
                addButton.setBackgroundColor(Color.GRAY);
            }
        }

        // Configure the Task Title EditText
        addTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    addButton.setEnabled(false);
                    addButton.setBackgroundColor(Color.GRAY);
                } else {
                    addButton.setEnabled(true);
                    addButton.setBackgroundColor(getResources().getColor(R.color.spacegrey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(s.toString().length() != 0);
            }
        });
        // Configure the Due Date TextView
        addTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dueDate = date;
                        addTaskDate.setText(date);
                        // Convert date to "YYYY/MM/DD" format
                        String[] parts = date.split("/");
                        orderDate = parts[2] + "/" + parts[1] + "/" + parts[0];
                    }
                }, YEAR, MONTH, DAY);

                datePickerDialog.show();
            }
        });
        // Configure the Add Button
        boolean finalIsUpdate = isUpdate;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = addTaskTitle.getText().toString();
                String taskDesc = addTaskDescription.getText().toString();
                int duration = addDuration.getValue();
                // TODO: Convert date to "YYYY/MM/DD" format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                String taskDueDate = LocalDate.parse(dueDate, formatter).atStartOfDay().toString();

                // TODO: pass the fields to TaskRequest
                addTaskRequest addTaskRequest = new addTaskRequest(taskTitle, taskDesc, taskDueDate, duration);
                Log.i(TAG, "Task ID:" + id);

                if (taskTitle.isEmpty()) {
                    Toast.makeText(context, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (taskDueDate.isEmpty()) {
                    Toast.makeText(context, "Please input due date!", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Convert strings received to Task object and send task controller
                    TaskController.addTask(addTaskRequest);

//                    Map<String, Object> taskMap = new HashMap<>();
//                    taskMap.put("taskTitle", taskTitle);
//                    taskMap.put("taskDescription", taskDesc);
//                    taskMap.put("taskDueDate", taskDueDate);
//                    taskMap.put("taskStatus", 0);
//                    taskMap.put("orderDate", orderDate);
//
//                    firestore.collection("tasks")
//                            .add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentReference> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(context, "New Task Has Been Added", Toast.LENGTH_SHORT).show();
//                                        // Notify RecyclerView about the data change
//                                        MainActivity mainActivity = MainActivity.class.cast(getActivity());
//                                        if (mainActivity != null) {
//                                            todoList toDoListFragment = mainActivity();
//                                            if (toDoListFragment != null) {
//                                                toDoListFragment.getAdapter().notifyDataSetChanged();
//                                            }
//                                        }
//                                        //((MainActivity) getActivity()).getToDoListFragment().getAdapter().notifyDataSetChanged();
//                                        dismiss();
//                                    } else {
//                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
                }
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListener) {
            ((onDialogCloseListener) activity).onDialogClose(dialog);
        }

    }
}
