package am.solution.weddingplanner.bottomSheetFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.List;

import am.solution.weddingplanner.ProfileFragment;
import am.solution.weddingplanner.R;
import am.solution.weddingplanner.data.DetailsDAO;
import am.solution.weddingplanner.data.DetailsDataBase;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.WeddDetails;


public class WeddDetailsFragment extends BottomSheetDialogFragment {

    User user;
    UserDAO userDao;
    DetailsDAO detailsDao;

    EditText bride, groom, guests, budget, date, location;
    Button buttonSave;

    int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;

    int firstInsert = 0;
    int detailsId;
    WeddDetailsFragment activity;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_wedding_details, container, false);


        Context context = getContext();
        user = (User) getActivity().getIntent().getSerializableExtra("User");
        userDao = Room.databaseBuilder(context, UserDataBase.class, "users_new2.db").allowMainThreadQueries().build().getUserDao();
        detailsDao = Room.databaseBuilder(context, DetailsDataBase.class, "details_db.db").allowMainThreadQueries().build().getDetailsDao();
        List<WeddDetails> detailsList = detailsDao.getAllDetails(user.getUserName());

        bride = view.findViewById(R.id.bride_input);
        groom = view.findViewById(R.id.groom_input);
        guests = view.findViewById(R.id.guests_input);
        budget = view.findViewById(R.id.budget_input);
        date = view.findViewById(R.id.date_input);
        location = view.findViewById(R.id.location_input);
        buttonSave = view.findViewById(R.id.buttonSave);



        if(detailsList.size()!=0){

            WeddDetails getDetails = detailsDao.selectDataFromUser(user.getUserName());
            bride.setText(getDetails.getBride());
            groom.setText(getDetails.getGroom());
            int guests_int = getDetails.getNoOfGuests();
            guests.setText(Integer.toString(guests_int));
            int budget_int = getDetails.getBudget();
            budget.setText(Integer.toString(budget_int));
            date.setText(getDetails.getDate());
            location.setText(getDetails.getLocation());

        }

        date.setOnTouchListener((v, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstInsert++;
                System.out.println(firstInsert);
                if(validateFields()){

                    String detailsUser= user.getUserName();
                    String groom = WeddDetailsFragment.this.groom.getText().toString().trim();
                    String bride = WeddDetailsFragment.this.bride.getText().toString().trim();
                    String date = WeddDetailsFragment.this.date.getText().toString().trim();
                    String location = WeddDetailsFragment.this.location.getText().toString().trim();

                    String budget_str = WeddDetailsFragment.this.budget.getText().toString().trim();
                    int budget = Integer.parseInt(budget_str);
                    String guests_str = WeddDetailsFragment.this.guests.getText().toString().trim();
                    int guests = Integer.parseInt(guests_str);

                    if(detailsList.size()==0){
                        WeddDetails details = new WeddDetails(detailsUser, groom, bride, date, location, budget, guests);
                        detailsDao.insert(details);
                        Toast.makeText(context, "Details added!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new ProfileFragment());
                        fr.commit();
                    }
                    else {
                        detailsDao.updateAnExistingRow(user.getUserName(), groom, bride, date, location, budget, guests);
                        Toast.makeText(context, "Details updated!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new ProfileFragment());
                        fr.commit();
                    }
                }
                else {
                    Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        return view;
    }
    public boolean validateFields() {
        if(groom.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(bride.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(location.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(date.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(guests.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else if(budget.getText().toString().equalsIgnoreCase("")) {

            return false;
        }
        else {
            return true;
        }
    }
}
