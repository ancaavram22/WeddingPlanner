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
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.data.TaskDAO;
import am.solution.weddingplanner.data.TaskDataBase;
import am.solution.weddingplanner.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    List<Task> taskList;

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
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
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
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.title);
            descriptionOutput = itemView.findViewById(R.id.description);
            //timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}

