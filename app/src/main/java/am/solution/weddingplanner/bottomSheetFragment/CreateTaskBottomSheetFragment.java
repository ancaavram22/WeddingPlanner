package am.solution.weddingplanner.bottomSheetFragment;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import am.solution.weddingplanner.MainActivity;
import am.solution.weddingplanner.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import am.solution.weddingplanner.RegisterActivity;
import am.solution.weddingplanner.TasksFragment;
import am.solution.weddingplanner.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateTaskBottomSheetFragment extends BottomSheetDialogFragment {
    EditText addTaskTitle;
    EditText addTaskDescription;
    EditText addTaskDate;
    EditText addTaskTime;
    EditText addTaskEvent;
    Button createTaskbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        addTaskTitle = view.findViewById(R.id.addTaskTitle);
        addTaskDescription = view.findViewById(R.id.addTaskDescription);
        addTaskDate = view.findViewById(R.id.taskDate);
        addTaskTime = view.findViewById(R.id.taskTime);
        addTaskEvent = view.findViewById(R.id.taskEvent);

        createTaskbutton = view.findViewById(R.id.createTask);
        createTaskbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitle = addTaskTitle.getText().toString().trim();
                String taskDescription = addTaskDescription.getText().toString().trim();
                String taskDate = addTaskDate.getText().toString().trim();
                String taskTime = addTaskTime.getText().toString().trim();
                String taskEvent = addTaskEvent.getText().toString().trim();
//                System.out.println("verify if data is extracted" + taskTitle);
//                System.out.println("verify if data is extracted" + taskDescription);
//                System.out.println("verify if data is extracted" + taskDate);
//                System.out.println("verify if data is extracted" + taskTime);
            }
        });
        return view;
    }

}
