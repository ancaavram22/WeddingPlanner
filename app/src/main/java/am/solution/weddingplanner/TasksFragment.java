package am.solution.weddingplanner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import am.solution.weddingplanner.adapters.TaskAdapter;
import am.solution.weddingplanner.bottomSheetFragment.CreateTaskBottomSheetFragment;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;
import butterknife.BindView;

public class TasksFragment extends Fragment {


    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;
    @BindView(R.id.noDataImage)
    ImageView noDataImage;
    TaskAdapter taskAdapter;
    List<Task> tasks = new ArrayList<>();

    //to get username;
    private User user;

    private TaskDAO tasksForCurrUser;

    Context context = getContext();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        ImageButton addTask = view.findViewById(R.id.addTaskButton);
        ImageButton deleteTask = view.findViewById(R.id.deleteTaskButton);



        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr =getParentFragmentManager().beginTransaction();
                fr.replace(R.id.container, new CreateTaskBottomSheetFragment());
                fr.commit();
            }
        });

        getSavedTasks();

        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TaskAdapter taskAdapter = new TaskAdapter(getContext(),getSavedTasks());
        recyclerView.setAdapter(taskAdapter);

        return view;
    }

    private List<Task> getSavedTasks() {
        //get user name
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        String username = user.getUserName();

        //get all existing task for current user
        Context context = getContext();
        tasksForCurrUser = Room.databaseBuilder(context, TaskDataBase.class, "task_db.db").allowMainThreadQueries().build().getTaskDao();

        //list with tasks
        List<Task> taskList = tasksForCurrUser.getAllTasks(username);

        return taskList;
    }


}