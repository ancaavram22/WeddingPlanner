package am.solution.weddingplanner;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import java.util.Calendar;

import am.solution.weddingplanner.Alarms.Notification_receiver;
import am.solution.weddingplanner.bottomSheetFragment.ChangePassFragment;
import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.model.User;

public class ProfileFragment extends Fragment {
    TextView changePassword, wedDetails;
    Button logOutButton;
    Switch notificationSwitch;
    private User user;
    private UserDAO userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

       changePassword = view.findViewById(R.id.changePass);
       wedDetails = view.findViewById(R.id.wedDetails);
       logOutButton = view.findViewById(R.id.logOutButton);
       notificationSwitch = view.findViewById(R.id.notification_switch);

       user = (User) getActivity().getIntent().getSerializableExtra("User");
       userDao = Room.databaseBuilder(getContext(), UserDataBase.class, "am_users.db").allowMainThreadQueries().build().getUserDao();
       createNotificationChannel();
       Context context = getContext();

       if(getNotificationsStatus()){

           notificationSwitch.setChecked(true);
           Calendar alarmTime = Calendar.getInstance();
           //set the time for the daily notification
           alarmTime.setTimeInMillis(System.currentTimeMillis());
           alarmTime.clear();
           alarmTime.set(Calendar.HOUR_OF_DAY, 18);
           alarmTime.set(Calendar.MINUTE, 20);
           alarmTime.set(Calendar.SECOND, 2);

           Intent intent = new Intent(context, Notification_receiver.class );
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
           AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
           alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);

       }

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

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //turn ON the notifications
                if(isChecked){

                    Toast.makeText(context, "Notification ON!", Toast.LENGTH_SHORT).show();

                    userDao.updateNotificationsStatus(true, user.getUserName());
                    Calendar alarmTime = Calendar.getInstance();
                    //set the time for the daily notification
                    alarmTime.setTimeInMillis(System.currentTimeMillis());
                    alarmTime.clear();
                    alarmTime.set(Calendar.HOUR_OF_DAY, 18);
                    alarmTime.set(Calendar.MINUTE, 30);
                    alarmTime.set(Calendar.SECOND, 2);

                    Intent intent = new Intent(context, Notification_receiver.class );
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
                }
                //turn OFF the notifications
                else{
                    userDao.updateNotificationsStatus(false, user.getUserName());

                    Intent intent = new Intent(context, Notification_receiver.class );
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name= "WeddingPlannerReminderChannel";
            String description = "Channel for WeddingPlanner Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("weddplan", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean getNotificationsStatus(){
        return user.isNotifications();
    }

}