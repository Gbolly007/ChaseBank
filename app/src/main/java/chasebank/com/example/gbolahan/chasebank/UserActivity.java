package chasebank.com.example.gbolahan.chasebank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class UserActivity extends AppCompatActivity {
    private TextView txtbal;
    private TextView txtdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtbal = findViewById(R.id.textView6);
        txtdate = findViewById(R.id.textdate);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ");
        String dateString = sdf.format(date);
        txtdate.setText(dateString);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String amt = extras.getString("amount");

            txtbal.setText(amt);
        }

    }
}
