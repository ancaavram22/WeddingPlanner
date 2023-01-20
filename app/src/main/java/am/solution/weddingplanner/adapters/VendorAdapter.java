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
import am.solution.weddingplanner.VendorsFragment;
import am.solution.weddingplanner.bottomSheetFragment.CreateVendorBottomSheetFragment;
import am.solution.weddingplanner.data.VendorDAO;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.Vendor;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.MyViewHolder> {

    Context context;
    List<Vendor> vendorList;
    VendorsFragment vendorsFragment;
    Boolean  deleted = false;
    public VendorAdapter(Context context, List<Vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vendor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vendor vendor = vendorList.get(position);
        VendorDAO vendorDao;
        vendorDao = Room.databaseBuilder(context, VendorDataBase.class, "am_vendors.db").allowMainThreadQueries().build().getVendorDao();

        holder.vendorName.setText(vendor.getVendorName());
        holder.paymentStatus.setText(vendor.getPaymentStatus());
        int amount_str = vendor.getAmount();
        holder.amount.setText(Integer.toString(amount_str));


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

                            vendorDao.delete(vendor);
                            deleted = true;
                            Toast.makeText(context.getApplicationContext(), "Deleted! Please REFRESH!",Toast.LENGTH_SHORT).show();
                        }
                        else if(item.getTitle().equals("EDIT")){

                            CreateVendorBottomSheetFragment fragment = new CreateVendorBottomSheetFragment();
                            fragment.setVendorId(vendor.getId(), true, vendorsFragment);
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
        return vendorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView vendorName;
        TextView paymentStatus;
        TextView amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.vendorName);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
            amount = itemView.findViewById(R.id.amount);

        }
    }

    public boolean RefreshAtDetete(){
        if (deleted)
            return true;
        return false;
    }

}

