package am.solution.weddingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import am.solution.weddingplanner.bottomSheetFragment.ChangePassFragment;
import am.solution.weddingplanner.bottomSheetFragment.CreateTaskBottomSheetFragment;
import am.solution.weddingplanner.bottomSheetFragment.WeddDetailsFragment;

public class ProfileFragment extends Fragment {
    TextView changePassword, wedDetails;
    Button logOutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

       changePassword = view.findViewById(R.id.changePass);
       wedDetails = view.findViewById(R.id.wedDetails);
       logOutButton = view.findViewById(R.id.logOutButton);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr =getParentFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ChangePassFragment());
                fr.commit();
            }
        });

        wedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr =getParentFragmentManager().beginTransaction();
                fr.replace(R.id.container, new WeddDetailsFragment());
                fr.commit();
            }
        });



        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}