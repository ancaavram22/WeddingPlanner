package am.solution.weddingplanner;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import am.solution.weddingplanner.adapters.TaskAdapter;
import am.solution.weddingplanner.data.DetailsDAO;
import am.solution.weddingplanner.data.DetailsDataBase;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;
import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

public class SummaryFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;

    //to get username;
    private User user;

    private TaskDAO tasksForCurrUser;
    private DetailsDAO detailsForCurrUser;
    Date date;
    String testDate;
    String wedDate;
    String countDate;

    Context context = getContext();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_summary, container, false);
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        CountdownView mCvCountdownView = view.findViewById(R.id.countdownView);
        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        getSavedTasks();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TaskAdapter taskAdapter = new TaskAdapter(getContext(), getSavedTasks());
        recyclerView.setAdapter(taskAdapter);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if(getDate()!=null) {
            countDate = getDate()+" 00:00:00";
        }
        else {
        countDate ="01-01-2000 00:00:00";
        }
        Log.i("Tag", countDate);


       // countDate= "01-01-2024 00:00:00";
        Log.i("Tag", countDate);


        Date now = new Date();
        try {
            //Formatting from String to Date
            Date date = sdf.parse(countDate);
            long currentDate = now.getTime();
            long weddingDateTime = date.getTime() ;
            long countDownToWedding = weddingDateTime - currentDate;
            mCvCountdownView.start(countDownToWedding);

        } catch (ParseException e) {
            e.printStackTrace();
        }




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getSavedTasks();
                RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                TaskAdapter taskAdapter = new TaskAdapter(getContext(), getSavedTasks());
                recyclerView.setAdapter(taskAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private String getDate() {
        //get user name
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        String username = user.getUserName();

        //get all existing task for current user
        Context context = getContext();
        detailsForCurrUser = Room.databaseBuilder(context, DetailsDataBase.class, "details_db.db").allowMainThreadQueries().build().getDetailsDao();

        testDate = detailsForCurrUser.getWeddDate(username);

        return testDate;
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