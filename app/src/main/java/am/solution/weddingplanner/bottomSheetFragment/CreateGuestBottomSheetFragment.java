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

    boolean isEdit;
    int guestId;

    public void setGuestId(int guestId, boolean isEdit, GuestsFragment activity) {
        this.guestId = guestId;
        this.isEdit = isEdit;
        this.activity = activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_guest, container, false);

        guestName = view.findViewById(R.id.addGuestName);
        noOfPersons = view.findViewById(R.id.addPersons);
        guestAvailability = view.findViewById(R.id.checkBox);
        createGuestbutton = view.findViewById(R.id.createGuest);


        Context context = getContext();
        guestDao = Room.databaseBuilder(context, GuestDataBase.class, "am_guests.db").allowMainThreadQueries().build().getGuestDao();
        user = (User) getActivity().getIntent().getSerializableExtra("User");

        if(isEdit){
            Guest guestEdit = guestDao.selectDataFromAnId(guestId);
            guestName.setText(guestEdit.getGuestName());

            if(guestEdit.getGuestAvailability().equalsIgnoreCase("Confirmed"))
            {
                guestAvailability.setChecked(true);
                System.out.println(guestEdit.getGuestAvailability());

            }
            else
            {
                guestAvailability.setChecked(false);
            }
            int pers_str = guestEdit.getNoOfPers();
            noOfPersons.setText(Integer.toString(pers_str));
        }

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
                    String noOfPersons_str = CreateGuestBottomSheetFragment.this.noOfPersons.getText().toString().trim();
                    int noOfPersons = Integer.parseInt(noOfPersons_str);

                    if(!isEdit) {
                        Guest guest = new Guest(guestUser, guestName, guestConfirmation, noOfPersons);
                        guestDao.insert(guest);
                        Toast.makeText(context, "Guest added!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new GuestsFragment());
                        fr.commit();
                    }
                    else {
                        guestDao.updateAnExistingRow(guestId, guestUser, guestName, guestConfirmation, noOfPersons);
                        Toast.makeText(context, "Guest updated!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new GuestsFragment());
                        fr.commit();
                    }
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
