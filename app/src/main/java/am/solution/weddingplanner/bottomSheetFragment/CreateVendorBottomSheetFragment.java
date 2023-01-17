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
import am.solution.weddingplanner.R;
import am.solution.weddingplanner.VendorsFragment;
import am.solution.weddingplanner.data.VendorDAO;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.Vendor;


public class CreateVendorBottomSheetFragment extends BottomSheetDialogFragment {

    EditText vendorName;
    CheckBox paymentStatus;
    EditText amount;

    Button createVendorbutton;

    private VendorDAO vendorDAO;
    private User user;

    VendorsFragment activity;
    boolean isEdit;
    int vendorId;

    public void setVendorId(int vendorID, boolean isEdit, VendorsFragment activity) {
        this.vendorId = vendorID;
        this.isEdit = isEdit;
        this.activity = activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_vendor, container, false);
        vendorName = view.findViewById(R.id.addVendorName);
        paymentStatus = view.findViewById(R.id.checkBox);
        amount = view.findViewById(R.id.addAmount);
        createVendorbutton = view.findViewById(R.id.createVendor);


        Context context = getContext();
        vendorDAO = Room.databaseBuilder(context, VendorDataBase.class, "am_vendors.db").allowMainThreadQueries().build().getVendorDao();
        user = (User) getActivity().getIntent().getSerializableExtra("User");

        if(isEdit){
            Vendor vendorEdit = vendorDAO.selectDataFromAnId(vendorId);
            vendorName.setText(vendorEdit.getVendorName());
            if(vendorEdit.getPaymentStatus().equalsIgnoreCase("Paid"))
            {
                paymentStatus.setChecked(true);
                System.out.println(vendorEdit.getPaymentStatus());
            }
            else
            {
                paymentStatus.setChecked(false);
            }
            int amount_int = vendorEdit.getAmount();
            amount.setText(Integer.toString(amount_int));
        }
        createVendorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields() == true) {

                    String vendorUser = user.getUserName();
                    String vendorName = CreateVendorBottomSheetFragment.this.vendorName.getText().toString().trim();
                    String paymentStatus;
                    if(CreateVendorBottomSheetFragment.this.paymentStatus.isChecked()){
                        paymentStatus = "Paid";
                    }
                    else
                    {
                        paymentStatus = "Not paid";
                    }
                    String amount_str = CreateVendorBottomSheetFragment.this.amount.getText().toString().trim();
                    int amount = Integer.parseInt(amount_str);

                    if(!isEdit) {
                        Vendor vendor = new Vendor(vendorUser, vendorName, paymentStatus, amount);
                        vendorDAO.insert(vendor);
                        Toast.makeText(context, "Vendor added!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new VendorsFragment());
                        fr.commit();
                    }
                    else {
                        vendorDAO.updateAnExistingRow(vendorId, vendorUser, vendorName, paymentStatus, amount);
                        Toast.makeText(context, "Vendor updated!", Toast.LENGTH_SHORT).show();

                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.container, new VendorsFragment());
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
        if(vendorName.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(amount.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else {
            return true;
        }
    }
}
