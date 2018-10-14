package chasebank.com.example.gbolahan.chasebank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends Activity {
    private TextView txtname;
    private Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        txtname = findViewById(R.id.textname);
        cont = findViewById(R.id.button2);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String myString = extras.getString("names");
            txtname.setText(myString);
        }

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("amount", LoginActivity.amount);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}
