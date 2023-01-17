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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import am.solution.weddingplanner.adapters.GuestAdapter;
import am.solution.weddingplanner.adapters.TaskAdapter;
import am.solution.weddingplanner.bottomSheetFragment.CreateGuestBottomSheetFragment;
import am.solution.weddingplanner.data.GuestDAO;
import am.solution.weddingplanner.data.GuestDataBase;
import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.User;
import butterknife.BindView;

public class GuestsFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;
    @BindView(R.id.noDataImage)
    ImageView noDataImage;
    TaskAdapter taskAdapter;
    List<Guest> guests = new ArrayList<>();

    //to get username;
    private User user;

    private GuestDAO guestsForCurrUser;

    Context context = getContext();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guests, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);

        ImageButton addGuest = view.findViewById(R.id.addGuestButton);


        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr =getParentFragmentManager().beginTransaction();
                fr.replace(R.id.container, new CreateGuestBottomSheetFragment());
                fr.commit();
            }
        });

        getSavedGuests();

        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GuestAdapter guestAdapter = new GuestAdapter(getContext(),getSavedGuests());
        recyclerView.setAdapter(guestAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getSavedGuests();

                RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                GuestAdapter guestAdapter = new GuestAdapter(getContext(),getSavedGuests());
                recyclerView.setAdapter(guestAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private List<Guest> getSavedGuests() {
        //get user name
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        String username = user.getUserName();

        //get all existing guests for current user
        Context context = getContext();
        guestsForCurrUser = Room.databaseBuilder(context, GuestDataBase.class, "am_guests.db").allowMainThreadQueries().build().getGuestDao();

        //list with guests
        List<Guest> guestList = guestsForCurrUser.getAllGuests(username);

        return guestList;
    }


}