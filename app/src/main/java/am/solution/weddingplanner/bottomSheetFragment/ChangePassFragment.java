package am.solution.weddingplanner.bottomSheetFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import am.solution.weddingplanner.GuestsFragment;
import am.solution.weddingplanner.ProfileFragment;
import am.solution.weddingplanner.R;
import am.solution.weddingplanner.VendorsFragment;
import am.solution.weddingplanner.data.GuestDataBase;
import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.data.VendorDAO;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.Vendor;


public class ChangePassFragment extends BottomSheetDialogFragment {

    User user;
    UserDAO userDao;
    Button changePassword;
    EditText newPassword, confirmNewPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_change_pass, container, false);


        Context context = getContext();
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        userDao = Room.databaseBuilder(context, UserDataBase.class, "users_new2.db").allowMainThreadQueries().build().getUserDao();


        changePassword = view.findViewById(R.id.buttonChangePass);
        newPassword = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmNewPassword);


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){

                    System.out.println(user.getUserName());
                    userDao.updateAnExistingRow(newPassword.getText().toString(), user.getUserName());
                    Toast.makeText(context, "Password changed!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new ProfileFragment());
                    fr.commit();
                }

            }
        });


        return view;
    }
    public boolean validateFields() {
        if(newPassword.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(confirmNewPassword.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if((confirmNewPassword.getText().toString().equals(newPassword.getText().toString())) != true) {
            return false;
        }
        else {
            return true;
        }
    }
}
