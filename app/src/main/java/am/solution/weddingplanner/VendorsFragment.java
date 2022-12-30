package am.solution.weddingplanner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import am.solution.weddingplanner.adapters.GuestAdapter;
import am.solution.weddingplanner.adapters.VendorAdapter;
import am.solution.weddingplanner.bottomSheetFragment.CreateGuestBottomSheetFragment;
import am.solution.weddingplanner.bottomSheetFragment.CreateVendorBottomSheetFragment;
import am.solution.weddingplanner.data.VendorDAO;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.Vendor;
import butterknife.BindView;

public class VendorsFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;

    //to get username;
    private User user;

    private VendorDAO vendorsForCurrUser;

    Context context = getContext();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendors, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        ImageButton addVendor = view.findViewById(R.id.addVendorButton);


        addVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr =getParentFragmentManager().beginTransaction();
                fr.replace(R.id.container, new CreateVendorBottomSheetFragment());
                fr.commit();
            }
        });

        getSavedVendors();

        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VendorAdapter vendorAdapter = new VendorAdapter(getContext(),getSavedVendors());
        recyclerView.setAdapter(vendorAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getSavedVendors();

                RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                VendorAdapter vendorAdapter = new VendorAdapter(getContext(),getSavedVendors());
                recyclerView.setAdapter(vendorAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private List<Vendor> getSavedVendors() {
        //get user name
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        String username = user.getUserName();

        //get all existing guests for current user
        Context context = getContext();
        vendorsForCurrUser = Room.databaseBuilder(context, VendorDataBase.class, "vendor_db.db").allowMainThreadQueries().build().getVendorDao();

        //list with guests
        List<Vendor> vendorList = vendorsForCurrUser.getAllVendors(username);

        return vendorList;
    }


}