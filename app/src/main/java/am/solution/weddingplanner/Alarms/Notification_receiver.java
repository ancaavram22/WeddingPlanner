package am.solution.weddingplanner.Alarms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import java.util.List;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.SummaryFragment;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.User;

public class Notification_receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

//        User user = (User) intent.getSerializableExtra("User");
//        int noOftasks = 0;
//        TaskDAO tasksForCurrUser = Room.databaseBuilder(context, TaskDataBase.class, "task_db.db").allowMainThreadQueries().build().getTaskDao();
//        List<Task> taskList = tasksForCurrUser.getAllTasks(user.getUserName());
//        noOftasks = taskList.size();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "weddplan")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("You have tasks to do!")
                .setContentText("Check the app!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
    }

}


