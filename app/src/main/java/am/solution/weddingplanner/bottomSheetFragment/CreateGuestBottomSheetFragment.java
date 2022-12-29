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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import am.solution.weddingplanner.GuestsFragment;
import am.solution.weddingplanner.R;
import am.solution.weddingplanner.data.GuestDAO;
import am.solution.weddingplanner.data.GuestDataBase;
import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.User;


public class CreateGuestBottomSheetFragment extends BottomSheetDialogFragment {


    EditText guestName;
    CheckBox guestAvailability;
    EditText noOfPersons;

    Button createGuestbutton;

    GuestsFragment activity;

    private GuestDAO guestDao;
    private User user;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_guest, container, false);
        guestName = view.findViewById(R.id.addGuestName);

        guestAvailability = view.findViewById(R.id.checkBox);
        noOfPersons = view.findViewById(R.id.addNoOfPersons);
        createGuestbutton = view.findViewById(R.id.createGuest);


        Context context = getContext();
        guestDao = Room.databaseBuilder(context, GuestDataBase.class, "guest_db.db").allowMainThreadQueries().build().getGuestDao();
        user = (User) getActivity().getIntent().getSerializableExtra("User");


        createGuestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields() == true) {

                    String guestUser = user.getUserName();
                    String guestName = CreateGuestBottomSheetFragment.this.guestName.getText().toString().trim();
                    String guestConfirmation;
                    if(guestAvailability.isChecked()){
                        guestConfirmation = "Confirmed";
                    }
                    else
                    {
                        guestConfirmation = "Not confirmed";
                    }
                    String noOfPersons = CreateGuestBottomSheetFragment.this.noOfPersons.getText().toString().trim();

                    Guest guest = new Guest(guestUser, guestName, guestConfirmation, noOfPersons);
                    guestDao.insert(guest);
                    Toast.makeText(context, "Guest added!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new GuestsFragment());
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
        if(guestName.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(noOfPersons.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else {
            return true;
        }
    }
}
