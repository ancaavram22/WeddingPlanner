package am.solution.weddingplanner.adapters;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.GuestsFragment;
import am.solution.weddingplanner.data.GuestDAO;
import am.solution.weddingplanner.data.GuestDataBase;
import am.solution.weddingplanner.model.Guest;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.MyViewHolder> {

    Context context;
    List<Guest> guestList;
    GuestsFragment guestsFragment;
    Boolean  deleted = false;
    public GuestAdapter(Context context, List<Guest> guestList) {
        this.context = context;
        this.guestList = guestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_guest,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Guest guest = guestList.get(position);
        GuestDAO guestDao;
        guestDao = Room.databaseBuilder(context, GuestDataBase.class, "guest_db.db").allowMainThreadQueries().build().getGuestDao();

        holder.guestName.setText(guest.getGuestName());
        holder.guestAvailablility.setText(guest.getGuestAvailability());
        holder.noOfPers.setText(guest.getNoOfPers());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.getMenu().add("EDIT");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){

                            guestDao.delete(guest);
                            deleted = true;
                            Toast.makeText(context.getApplicationContext(), "Guest deleted",Toast.LENGTH_SHORT).show();
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
        return guestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView guestName;
        TextView guestAvailablility;
        TextView noOfPers;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            guestName = itemView.findViewById(R.id.guestName);
            guestAvailablility = itemView.findViewById(R.id.description);
            noOfPers = itemView.findViewById(R.id.noOfPersons);

        }
    }

    public boolean RefreshAtDetete(){
        if (deleted)
            return true;
        return false;
    }

}

