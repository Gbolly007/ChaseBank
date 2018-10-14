package chasebank.com.example.gbolahan.chasebank;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;


public class LoginActivity extends Activity {
    public static String amount = null;
    public static String id = null;
    public static String accno = null;
    private EditText usernames;
    private EditText passwords;
    private Button log;
    private CheckBox remeber;
    private ImageView reg;
    private ImageView set;
    private ImageView help;
    private AlertDialog progressDialog;
    private DroidDialog errdlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context mContext = this;
        usernames = findViewById(R.id.txtuser);
        passwords = findViewById(R.id.txtpwd);
        log = findViewById(R.id.button);
        remeber = findViewById(R.id.checkBox);
        reg = findViewById(R.id.registerimg);
        set = findViewById(R.id.settingsimg);
        help = findViewById(R.id.helpimg);
        progressDialog = new SpotsDialog(mContext, R.style.Custom);
        progressDialog.setCancelable(false);


    }

    public void loginUser(View view) {
        // Get NAme ET control value
        String username = usernames.getText().toString();
        // Get Email ET control value
        String password = passwords.getText().toString();
        // Get Password ET control value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (Utility.isNotNull(username) && Utility.isNotNull(password)) {
            // When Email entered is Valid

            // Put Http parameter name with value of Name Edit View control
            params.put("user", username);
            // Put Http parameter username with value of Email Edit View control
            params.put("password", password);
            // Put Http parameter password with value of Password Edit View control

            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);
        }
        // When Email is invalid


        // When any of the Edit View control left blank
        else {
            Context mContext = this;
            errdlg = new DroidDialog.Builder(mContext).title("Error").content("Login details missing")
                    .cancelable(true, true)
                    .color(ContextCompat.getColor(mContext, R.color.orange), ContextCompat.getColor(mContext, R.color.white),
                            ContextCompat.getColor(mContext, R.color.dark_indigo))
                    .icon(R.drawable.ic_action_close)

                    .animation(AnimUtils.AnimFadeInOut).show();
        }

    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog
        progressDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.8.100:36550/ChaseBank/login/dologin", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                progressDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has s tatus boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        // Set Default Values for Edit View controls
                        amount = Double.toString(obj.getDouble("amount"));
                        id = Integer.toString(obj.getInt("id"));
                        accno = obj.getString("accno");

                        Intent intent = new Intent(getApplicationContext(), NameActivity.class);
                        intent.putExtra("names", obj.getString("name"));

                        startActivity(intent);
                        finish();
                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else {
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                progressDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
