package am.solution.weddingplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.data.VendorDataBase;
import am.solution.weddingplanner.model.User;
import am.solution.weddingplanner.model.Vendor;

public class RegisterActivity extends AppCompatActivity {
    EditText editEmail, editLastName, editFirstName, editUsername,  editPassword;
    Button buttonRegister;
    private UserDAO userDao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.email_input);
        editFirstName = findViewById(R.id.firstName_input);
        editLastName = findViewById(R.id.lastName_input);
        editUsername = findViewById(R.id.username_input);
        editPassword = findViewById(R.id.password_input);
        buttonRegister = findViewById(R.id.buttonRegister);

        userDao = Room.databaseBuilder(this, UserDataBase.class, "am_users.db").allowMainThreadQueries()
                .build().getUserDao();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int validateF = validateFields();
                if(validateF!=0) {

                    if(validateF == 1)
                    {
                        Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                    }
                    if(validateF == 2)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
                    }
                    if(validateF == 3)
                    {
                        Toast.makeText(getApplicationContext(), "Password must have min 8 chars!", Toast.LENGTH_SHORT).show();
                    }
                    if(validateF == 4)
                    {
                        Toast.makeText(getApplicationContext(), "Username taken!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    String userName = editUsername.getText().toString().trim();
                    String email = editEmail.getText().toString().trim();
                    String password = editPassword.getText().toString().trim();
                    String firstName = editFirstName.getText().toString().trim();
                    String lastName = editLastName.getText().toString().trim();

                    User user = new User(userName, password, email, firstName, lastName, false);
                    userDao.insert(user);

                    Intent moveToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(moveToLogin);

                    Toast.makeText(getApplicationContext(), "User created! Please Log In!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private boolean checkValidUsername(){


        user = (User) this.getIntent().getSerializableExtra("User");

        List<User> userList = userDao.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            User validUsername = userList.get(i);
            if(editUsername.getText().toString().trim().equals(validUsername.getUserName()))
                return false;
        }
        return true;
    }
    public int validateFields() {
        if(editEmail.getText().toString().equalsIgnoreCase("")) {
            return 1;
        }
        if(!editEmail.getText().toString().contains("@")) {
            Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return 2;
        }
        else if(editFirstName.getText().toString().equalsIgnoreCase("")) {
            return 1;
        }
        else if(editLastName.getText().toString().equalsIgnoreCase("")) {
            return 1;
        }
        else if(editPassword.getText().toString().equalsIgnoreCase("")) {
            return 1;
        }
        else if(editPassword.getText().toString().length() <8) {
            return 3;
        }
        else if(!checkValidUsername()) {
            return 4;
        }
        else
            return 0;
    }
}