package am.solution.weddingplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class WeddingDetailsActivity extends AppCompatActivity {


    EditText bride, groom, guests, budget, date;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_details);

        bride = findViewById(R.id.bride_input);
        groom = findViewById(R.id.groom_input);
        guests = findViewById(R.id.guests_input);
        budget = findViewById(R.id.budget_input);
        date = findViewById(R.id.date_input);

        buttonSave = findViewById(R.id.buttonSave);



    }
}