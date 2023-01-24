package am.solution.weddingplanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import am.solution.weddingplanner.adapters.TaskAdapter;
import am.solution.weddingplanner.data.DetailsDAO;
import am.solution.weddingplanner.data.DetailsDataBase;
import am.solution.weddingplanner.data.GuestDAO;
import am.solution.weddingplanner.data.GuestDataBase;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.data.VendorDAO;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.Vendor;
import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

public class SummaryFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;
    TextView spent;
    TextView left;
    //to get username;
    private User user;

    private TaskDAO tasksForCurrUser;
    private DetailsDAO detailsForCurrUser;
    private VendorDAO alreadySpentMoney;
    private GuestDAO guests;
    static int noOfTasksToDO;
    Date date;
    String testDate;
    String wedDate;
    String countDate;
    TextView guestsConfirmedSoFar;
    TextView guestsAwaiting;

    private PieChart chart;
    private int moneyLeft = 50;
    private int moneySpent = 50;
    int budget;
    int paidVendors;
    int noOfConfirmedGuests;
    int noOfMaximumGuests;
    int noOfGuestsInvitedSoFar;

    Context context = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_summary, container, false);
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        user = (User) getActivity().getIntent().getSerializableExtra("User");

        chart = view.findViewById(R.id.pie_chart);
        spent =view.findViewById(R.id.spent);
        left = view.findViewById(R.id.left);
        guestsConfirmedSoFar = view.findViewById(R.id.confirmedGuests3);
        guestsAwaiting = view.findViewById(R.id.confirmedGuests2);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        CountdownView mCvCountdownView = view.findViewById(R.id.countdownView);
        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        getSavedTasks();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TaskAdapter taskAdapter = new TaskAdapter(getContext(), getSavedTasks());
        recyclerView.setAdapter(taskAdapter);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if(getWeddDetails()!=null) {
            countDate = getWeddDetails()+" 00:00:00";
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
        getAlreadySpentMoney();
        addToPieChart();
        getConfirmedGuests();
        return view;
    }

    private String getWeddDetails() {

        String username = user.getUserName();

        //get all existing task for current user
        Context context = getContext();
        detailsForCurrUser = Room.databaseBuilder(context, DetailsDataBase.class, "am_details.db").allowMainThreadQueries().build().getDetailsDao();

        testDate = detailsForCurrUser.getWeddDate(username);
        budget = detailsForCurrUser.getWeddBudget(username);
        noOfMaximumGuests = detailsForCurrUser.noOfGuestsExpected(username);
        return testDate;
    }

    private List<Task> getSavedTasks() {

        String username = user.getUserName();

        //get all existing task for current user
        Context context = getContext();
        tasksForCurrUser = Room.databaseBuilder(context, TaskDataBase.class, "am_tasks.db").allowMainThreadQueries().build().getTaskDao();

        //list with tasks
        List<Task> taskList = tasksForCurrUser.getAllTasks(username);
        noOfTasksToDO = taskList.size();
        return taskList;
    }

    private void addToPieChart() {
        // add to pie chart

        chart.addPieSlice(new PieModel("Money Left", paidVendors, Color.parseColor("#FFDA9E48")));
        chart.addPieSlice(new PieModel("Spent Money", budget-paidVendors, Color.parseColor("#FFECC7AD")));
        left.setText(Integer.toString(budget-paidVendors) + " left");
        spent.setText(Integer.toString(paidVendors) + " spent");
        chart.startAnimation();

    }

    private void getAlreadySpentMoney(){

        paidVendors = 0;
        String username = user.getUserName();

        //get all existing task for current user
        Context context = getContext();
        alreadySpentMoney = Room.databaseBuilder(context, VendorDataBase.class, "am_vendors.db").allowMainThreadQueries().build().getVendorDao();

        List<Vendor> paidVendorsList = alreadySpentMoney.getAllPaidVendors(username, "Paid");
        for (int i = 0; i < paidVendorsList.size(); i++) {
            Vendor vendor = paidVendorsList.get(i);
            paidVendors = paidVendors + vendor.getAmount();
        }

        //System.out.println(paidVendors + "!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private void getConfirmedGuests(){

        noOfConfirmedGuests = 0;
        noOfGuestsInvitedSoFar = 0;
        String username = user.getUserName();

        Context context = getContext();
        guests = Room.databaseBuilder(context, GuestDataBase.class, "am_guests.db").allowMainThreadQueries().build().getGuestDao();

        List<Guest> allGuests = guests.getAllGuests(username);
        for (int i = 0; i < allGuests.size(); i++) {
            Guest guest = allGuests.get(i);
            noOfGuestsInvitedSoFar = noOfGuestsInvitedSoFar + guest.getNoOfPers();

        }

        List<Guest> allConfirmedGuests = guests.getAllConfirmedGuests(username, "Confirmed");
        for (int i = 0; i < allConfirmedGuests.size(); i++) {
            Guest guest = allConfirmedGuests.get(i);
            noOfConfirmedGuests = noOfConfirmedGuests + guest.getNoOfPers();

        }
        guestsConfirmedSoFar.setText(noOfConfirmedGuests + "\nconfirmed" );
        guestsAwaiting.setText(noOfGuestsInvitedSoFar-noOfConfirmedGuests + "\nawaiting" );

    }
}