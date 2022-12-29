package am.solution.weddingplanner.bottomSheetFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import am.solution.weddingplanner.MainActivity;
import am.solution.weddingplanner.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.TasksFragment;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;



public class CreateTaskBottomSheetFragment extends BottomSheetDialogFragment {


    EditText addTaskTitle;
    EditText addTaskDescription;
    EditText addTaskDate;
    EditText addTaskTime;
    EditText addTaskEvent;
    Button createTaskbutton;

    int mYear, mMonth, mDay;
    int mHour, mMinute;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    TasksFragment activity;

    private TaskDAO taskDao;
    private User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        addTaskTitle = view.findViewById(R.id.addGuestName);
        addTaskDescription = view.findViewById(R.id.addNoOfPersons);
        addTaskDate = view.findViewById(R.id.taskDate);
        addTaskTime = view.findViewById(R.id.taskTime);
        addTaskEvent = view.findViewById(R.id.taskEvent);
        createTaskbutton = view.findViewById(R.id.createGuest);

        addTaskDate.setOnTouchListener((v, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            addTaskDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        addTaskTime.setOnTouchListener((v, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),
                        (view12, hourOfDay, minute) -> {
                            addTaskTime.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        Context context = getContext();
        taskDao = Room.databaseBuilder(context, TaskDataBase.class, "task_db.db").allowMainThreadQueries().build().getTaskDao();
        user = (User) getActivity().getIntent().getSerializableExtra("User");


        createTaskbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields() == true) {

                    String taskUser = user.getUserName();
                    String taskTitle = addTaskTitle.getText().toString().trim();
                    String taskDescription = addTaskDescription.getText().toString().trim();
                    String taskDate = addTaskDate.getText().toString().trim();
                    String taskTime = addTaskTime.getText().toString().trim();
                    String taskEvent = addTaskEvent.getText().toString().trim();

                    Task task = new Task(taskUser, taskTitle, taskDescription, taskDate, taskTime, taskEvent);
                    taskDao.insert(task);
                    Toast.makeText(context, "Task created!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new TasksFragment());
                    fr.commit();
                }
                else {
                    //empty fields
                    Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public boolean validateFields() {
        if(addTaskTitle.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(addTaskDescription.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(addTaskDate.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(addTaskTime.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(addTaskEvent.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else {
            return true;
        }
    }
}
