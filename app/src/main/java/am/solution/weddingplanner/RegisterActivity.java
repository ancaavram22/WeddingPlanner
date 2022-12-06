package am.solution.weddingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText editEmail, editLastName, editFirstName, editUsername,  editPassword, editRole;
    Button buttonRegister;
    Spinner spinnerRoles;

    TextView textViewLogin;
    private UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinnerRoles = findViewById(R.id.roleSelectionSpinner);
        editEmail = findViewById(R.id.email_input);
        editFirstName = findViewById(R.id.firstName_input);
        editLastName = findViewById(R.id.lastName_input);
        editUsername = findViewById(R.id.username_input);
        editPassword = findViewById(R.id.password_input);
        //editRole = findViewById(R.id.firstName_input);
        buttonRegister = findViewById(R.id.buttonRegister);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.roles));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerRoles.setAdapter(myAdapter);

        userDao = Room.databaseBuilder(this, UserDataBase.class, "users_new2.db").allowMainThreadQueries()
                .build().getUserDao();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editUsername.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();

                    User user = new User(userName,  password,email, firstName, lastName);
                    userDao.insert(user);
                  //  Toast.makeText(RegisterActivity.this, "Registered! You can log in!", Toast.LENGTH_SHORT).show();

                    Intent moveToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(moveToLogin);

            }
        });
    }
}