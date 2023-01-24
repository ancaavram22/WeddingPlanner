package am.solution.weddingplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import am.solution.weddingplanner.R;
import am.solution.weddingplanner.GuestsFragment;
import am.solution.weddingplanner.bottomSheetFragment.CreateGuestBottomSheetFragment;
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
        guestDao = Room.databaseBuilder(context, GuestDataBase.class, "am_guests.db").allowMainThreadQueries().build().getGuestDao();

        holder.guestName.setText(guest.getGuestName());
        holder.guestAvailablility.setText(guest.getGuestAvailability());
        int pers_str = guest.getNoOfPers();
        holder.noOfPers.setText(Integer.toString(pers_str));


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
                            Toast.makeText(context.getApplicationContext(), "Deleted! Please REFRESH!",Toast.LENGTH_SHORT).show();
                        }
                        else if(item.getTitle().equals("EDIT")){

                            CreateGuestBottomSheetFragment fragment = new CreateGuestBottomSheetFragment();
                            fragment.setGuestId(guest.getId(), true, guestsFragment);
                            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, fragment);
                            ft.commit();
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
            guestName = itemView.findViewById(R.id.name);
            guestAvailablility = itemView.findViewById(R.id.availability);
            noOfPers = itemView.findViewById(R.id.persons);

        }
    }

    public boolean RefreshAtDetete(){
        if (deleted)
            return true;
        return false;
    }

}

