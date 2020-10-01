package edu.csumb.flightapp;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.User;

public class LoginActivity  extends AppCompatActivity {

    private static final String LOGIN_ACTIVITY = "LoginActivity";
    private static final String ADMIN = "!admiM2";
    public static final String CODE = "code";
    public static final String USERNAME = "username";
    public static final String ISADMIN="isAdmin";

    private int errorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGIN_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdmin = false;

                Log.d(LOGIN_ACTIVITY, "onClick called");

                EditText usertv = findViewById(R.id.username);
                String username = usertv.getText().toString();

                EditText passtv = findViewById(R.id.password);
                String password = passtv.getText().toString();

                if (username.equals(ADMIN) && password.equals(ADMIN)) {
                    isAdmin = true;
                } else {
                    // verify username and password
                    FlightDao dao =
                            FlightRoom.getFlightRoom(LoginActivity.this).dao();
                    User user = dao.getUserByUsernamePassword(username, password);
                    if (user == null) {
                        // username not found
                        errorCount++;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Username password not valid.");
                        builder.setPositiveButton("Continue",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (errorCount > 1) {
                                            // set bad return code
                                            Intent intent = new Intent();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(CODE, -1);
                                            intent.putExtras(bundle);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        } else {
                                            return;
                                        }
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }
                // username and password are now validated

                // return data back to MainActivity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CODE, 0);
                bundle.putString(USERNAME, username);
                bundle.putBoolean(ISADMIN, isAdmin);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}

