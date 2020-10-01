package edu.csumb.flightapp;

import android.content.DialogInterface;
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
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.User;

public class CreateAccountActivity  extends AppCompatActivity {

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private static final String CREATE_ACCOUNT_ACTIVITY = "CreateAccountActivity";

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATE_ACCOUNT_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button create_button = findViewById(R.id.create_account_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlightDao dao = FlightRoom.getFlightRoom(CreateAccountActivity.this).dao();

                Log.d(CREATE_ACCOUNT_ACTIVITY, "onClick called");

                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();


                //try catch to check each of the requirements, if it catches error,
                // than dont send it to database and just output error message.

                // xDone check that username and password meets requirements
                //   one special char, one uppercase and one lowercase letters, one digit
                //   display error message using dialog

                if ((dao.getUserByUsername(strUsername) !=  null) || (strUsername.equals("!admiM2"))){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setTitle("Username already in use, please try again.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                if (!validate(strUsername) ||  !validate(strPassword) ){
                    // Username or password false, display and an error

                    AlertDialog.Builder builder  = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setTitle("Invalid Username/password, please try again.");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;

                }

                // xDone create new User object and add to database.

                User newUser = new User(strUsername,strPassword);

                dao.addUser(newUser);

                // xDone  write a record to Log table with message that new Account has been created.
                //  include username (but not password) in the message.

                LogRecord newLog = new LogRecord(3, strUsername, "New User: "+ strUsername);

                dao.addLog(newLog);

                System.out.println(dao.getAllUsers());
                System.out.println(dao.getAllLogs());


                AlertDialog.Builder builder  = new AlertDialog.Builder(CreateAccountActivity.this);
                builder.setTitle("Account created Successfully");
                builder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                return;

            }

            private boolean validate(String text){

                // xDone FINISH VALIDATE FUNCTION
                boolean hasUppercase = !text.equals(text.toLowerCase());
                boolean hasLowercase = !text.equals(text.toUpperCase());
                if(!hasUppercase){

                    System.out.println("Must have an uppercase Character");
                    return false;
                }
                if(!hasLowercase){
                    System.out.println("Must have a lowercase Character");
                    return false;
                }

                if (text.equals("!admiM2")){
                    System.out.println("Username reserved for admin");
                    return false;
                }

                if (!text.contains("!") && !text.contains("@") && !text.contains("#") && !text.contains("$")){return false;}

                return true;
            }
        });
    }
}