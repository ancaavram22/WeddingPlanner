package am.solution.weddingplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordByEmailActivity extends AppCompatActivity {

    Button sendEmail;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

       sendEmail = findViewById(R.id.buttonResetPass);
        email = findViewById(R.id.editTextEmail);


    }
}
