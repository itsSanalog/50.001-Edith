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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.edith.R;
import com.example.edith.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.testng.reporters.jq.Main;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BottomFragment extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private EditText taskName;
    private EditText taskDescription;
    private TextView setTaskDate;
    private Button saveButton;
    private FirebaseFirestore firestore;
    private Context context;
    private String dueDate = "";
    private String orderDate = "";
    private String id = "";
    private String dueDateUpdate = "";
    private String orderDateUpdate = "";

    private static BottomFragment newInstance() {
        return new BottomFragment();
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
        taskName = view.findViewById(R.id.editTaskTitle);
        taskDescription = view.findViewById(R.id.editTaskDescription);
        setTaskDate = view.findViewById(R.id.setDueDateTxt);
        saveButton = view.findViewById(R.id.addTaskButton);

        firestore = FirebaseFirestore.getInstance();

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

            taskName.setText(taskTitle);
            taskDescription.setText(taskDesc);
            setTaskDate.setText(dueDateUpdate);

            if (taskTitle.length() > 0){
                saveButton.setEnabled(false);
                saveButton.setBackgroundColor(Color.GRAY);
            }
        }


        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);
                } else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.spacegrey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveButton.setEnabled(s.toString().length() != 0);
            }
        });

        setTaskDate.setOnClickListener(new View.OnClickListener() {
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
                        setTaskDate.setText(date);
                        // Convert date to "YYYY/MM/DD" format
                        String[] parts = date.split("/");
                        orderDate = parts[2] + "/" + parts[1] + "/" + parts[0];
                    }
                }, YEAR, MONTH, DAY);

                datePickerDialog.show();
            }
        });

        boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = taskName.getText().toString();
                String taskDesc = taskDescription.getText().toString();
                String taskDueDate = dueDate;
                Log.i("BottomFragment", "Task ID:" + id);
                if (id != null && finalIsUpdate){
                    firestore.collection("tasks").document(id).update("taskTitle", taskTitle, "taskDescription", taskDesc, "taskDueDate", taskDueDate, "orderDate", orderDate);
                    Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (taskTitle.isEmpty()) {
                        Toast.makeText(context, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (taskDueDate.isEmpty()) {
                        Toast.makeText(context, "Please input due date!", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put("taskTitle", taskTitle);
                        taskMap.put("taskDescription", taskDesc);
                        taskMap.put("taskDueDate", taskDueDate);
                        taskMap.put("taskStatus", 0);
                        taskMap.put("orderDate", orderDate);

                        firestore.collection("tasks")
                                .add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "New Task Has Been Added", Toast.LENGTH_SHORT).show();
                                            // Notify RecyclerView about the data change
                                            ((MainActivity) getActivity()).getToDoListFragment().getAdapter().notifyDataSetChanged();
                                            dismiss();
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
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
