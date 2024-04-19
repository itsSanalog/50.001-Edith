package com.example.edith.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edith.R;
import com.example.edith.controllers.TaskController;
import com.example.edith.data.DatabaseOperations;
import com.example.edith.data.FirebaseOperations;
import com.example.edith.models.TaskRequests.updateTaskRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * UpdateTaskBottomFragment is a BottomSheetDialogFragment that handles the updating of existing tasks.
 * It provides a form for the user to edit the task details.
 */
public class UpdateTaskBottomFragment extends BottomSheetDialogFragment {

    public static final String TAG = "UpdateTaskBottomFragment";

    private String id;
    private EditText editTaskTitle;
    private EditText editTaskDescription;
    private NumberPicker editTaskDuration;
    private TextView updateDueDateText;
    private TextView updateDeadlineTime;
    private Button updateTaskButton;
    private DatabaseOperations db;
    private Context context;
    private String dueDateUpdate;

    /**
     * Creates a new instance of UpdateTaskBottomFragment.
     * @return a new instance of UpdateTaskBottomFragment.
     */
    public static UpdateTaskBottomFragment newInstance() {
        return new UpdateTaskBottomFragment();
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.update_task_dialog, container, false);
        // Configure UI elements
        return view;
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it can later be reconstructed in a new instance of its process is restarted.
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("bundle", getArguments());
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the UI elements
        editTaskTitle = view.findViewById(R.id.editTaskTitle);
        editTaskDescription = view.findViewById(R.id.editTaskDescription);
        editTaskDuration = view.findViewById(R.id.editDurationPicker);
        updateDueDateText = view.findViewById(R.id.updateDueDateTxt);
        updateDeadlineTime = view.findViewById(R.id.updateDeadlineTime);
        updateTaskButton = view.findViewById(R.id.updateTaskButton);

        // get the database operations
        db = FirebaseOperations.getInstance();

        // Configure the editTaskDuration NumberPicker
        editTaskDuration.setMinValue(1); // Minimum duration
        editTaskDuration.setMaxValue(120); // Maximum duration

        // get the previous task details
        boolean isUpdate = false;
        Bundle bundle;
        if (savedInstanceState != null){
            bundle = savedInstanceState.getBundle("bundle");
        } else {
            bundle = getArguments();

        }
        if (bundle != null){
            isUpdate = true;
            id = bundle.getString("id");
            String taskTitle = bundle.getString("taskTitle");
            String taskDescription = bundle.getString("taskDescription");
            String dueDate = bundle.getString("taskDueDate");
            String deadlineTime = bundle.getString("taskDeadlineTime");

            // set the previous task details
            editTaskTitle.setText(taskTitle);
            editTaskDescription.setText(taskDescription);
            updateDueDateText.setText(dueDate);
            updateDeadlineTime.setText(deadlineTime);


        }

        // Set the addTextChangeListener for the editTaskTitle
        editTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    updateTaskButton.setEnabled(false);
                    updateTaskButton.setBackgroundColor(Color.GRAY);
                } else {
                    updateTaskButton.setEnabled(true);
                    updateTaskButton.setBackgroundColor(getResources().getColor(R.color.spacegrey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTaskButton.setEnabled(s.toString().length() != 0);
            }
        });

        updateDueDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DAY_OF_MONTH);

                // show the date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueDateUpdate = LocalDate.of(year, month + 1, dayOfMonth).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        updateDueDateText.setText(dueDateUpdate);
                    }
                }, YEAR, MONTH, DAY);

                datePickerDialog.show();
            }
        });

        updateDeadlineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int HOUR = calendar.get(Calendar.HOUR);
                int MINUTE = calendar.get(Calendar.MINUTE);

                // show the time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.TimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LocalTime time = LocalTime.of(hourOfDay, minute);
                        updateDeadlineTime.setText(time.toString());
                    }
                }, HOUR, MINUTE, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        boolean finalIsUpdate = isUpdate;
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = editTaskTitle.getText().toString();
                String taskDescription = editTaskDescription.getText().toString();
                int taskDuration = editTaskDuration.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(dueDateUpdate + " " + updateDeadlineTime.getText().toString(), formatter);
                String taskDueDate = localDateTime.toString();


                updateTaskRequest updateTaskRequest = new updateTaskRequest(id, taskTitle, taskDescription, taskDueDate, taskDuration);
                if (id != null && finalIsUpdate){
                    TaskController.updateTask(updateTaskRequest);
                    dismiss();
                }
            }
        });

    }

    /**
     * Called when a fragment is first attached to its context.
     * @param context The context being attached to.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * This method will be invoked when the dialog is dismissed.
     * @param dialog The dialog that was dismissed will be passed into the method.
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListener) {
            ((onDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}