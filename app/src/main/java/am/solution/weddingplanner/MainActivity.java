package am.solution.weddingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import am.solution.weddingplanner.data.UserDAO;
import am.solution.weddingplanner.data.UserDataBase;
import am.solution.weddingplanner.model.User;


public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister, textViewReset;
    UserDAO db;
    UserDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

      //  textViewReset = findViewById(R.id.textViewReset);
        textViewRegister = findViewById(R.id.textViewRegister);

        dataBase = Room.databaseBuilder(this, UserDataBase.class, "am_users.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();

        textViewRegister.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

      //  textViewReset.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ResetPassByMailActivity.class)));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()) {
                    String username = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();

                    User user = db.getUserByUsername(username, password);

                    if (user != null) {
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        i.putExtra("User", user);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Unregistered user, or incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public boolean validateFields() {
        if(editTextEmail.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else if(editTextPassword.getText().toString().equalsIgnoreCase("")) {
            return false;
        }
        else {
            return true;
        }
    }
}