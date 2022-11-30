package am.solution.weddingplanner.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.TasksFragment;
import am.solution.weddingplanner.bottomSheetFragment.CreateTaskBottomSheetFragment;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    List<Task> taskList;
    TasksFragment tasksFragment;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.US);

    Date date = null;
    String outputDateString = null;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        TaskDAO taskDao;
        taskDao = Room.databaseBuilder(context, TaskDataBase.class, "task_db.db").allowMainThreadQueries().build().getTaskDao();

        holder.titleOutput.setText(task.getTaskTitle());
        holder.descriptionOutput.setText(task.getTaskDescription());

        try {
            date = inputDateFormat.parse(task.getTaskDate());
            outputDateString = dateFormat.format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.dayOutput.setText(day);
            holder.dateOutput.setText(dd);
            holder.monthOutput.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //String formatedTime = DateFormat.getDateTimeInstance().format(task.getTaskTime());
        //holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){

                            taskDao.delete(task);

                            Toast.makeText(context.getApplicationContext(), "Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView dateOutput;
        TextView dayOutput;
        TextView monthOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.title);
            descriptionOutput = itemView.findViewById(R.id.description);
            dayOutput = itemView.findViewById(R.id.day);
            dateOutput = itemView.findViewById(R.id.date);
            monthOutput = itemView.findViewById(R.id.month);
        }
    }

}

