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
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;
import edu.csumb.flightapp.model.User;

public class ReserveLoginActivity  extends AppCompatActivity {

    private static final String RESERVELOGIN_ACTIVITY = "LoginActivity";
//    private static final String ADMIN = "!admiM2";
    public static final String CODE = "code";
    public static final String USERNAME = "username";
//    public static final String ISADMIN="isAdmin";

    private int errorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(RESERVELOGIN_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        final String strDeparture = getIntent().getStringExtra("departure");
        final String strArrival = getIntent().getStringExtra("arrival");
        final String strTicketAmount = getIntent().getStringExtra("ticketAmount");
        final String flightNo = getIntent().getStringExtra("flightNo");
        final String strPrice = getIntent().getStringExtra("price");

        String state  = "california";

        String weather = null;

        if (strDeparture.equals("Seattle")){
            weather = "57⁰ F, mostly cloudy";
        }

        if (strDeparture.equals("Los Angeles")){
            weather = "72⁰ F, Sunny";
        }

        if (strDeparture.equals("Monterey")){
            weather = "62⁰ F, Sunny";
        }

        System.out.println(flightNo);
        System.out.println("flightNo: ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        Button login_button = findViewById(R.id.login_button);
        final String finalWeather = weather;
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdmin = false;

                Log.d(RESERVELOGIN_ACTIVITY, "onClick called");



                final int ticketAmountNum = Integer.parseInt(strTicketAmount);

                double price =  Double.parseDouble(strPrice);

                final Double totalCost = ticketAmountNum * price;

                EditText usertv = findViewById(R.id.username);
                final String username = usertv.getText().toString();

                EditText passtv = findViewById(R.id.password);
                String password = passtv.getText().toString();


                    // verify username and password
                    FlightDao dao =
                            FlightRoom.getFlightRoom(ReserveLoginActivity.this).dao();
                    final User user = dao.getUserByUsernamePassword(username, password);
                    if (user == null) {
                        // username not found
                        errorCount++;
                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(ReserveLoginActivity.this);
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
                // username and password are now validated
                AlertDialog.Builder builder2 =
                        new AlertDialog.Builder(ReserveLoginActivity.this);

                builder2.setTitle("Confirm Reservation for " + username);
                builder2.setMessage("\nFlight No: " + flightNo + "\nDeparture: "+ strDeparture + "\nArrival: "
                        + strArrival + "\nCurrent " + strArrival + " Weather: " + finalWeather +"\nTickets: "
                        + ticketAmountNum + "total cost: $" + totalCost);
                builder2.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FlightDao dao = FlightRoom.getFlightRoom(ReserveLoginActivity.this).dao();
                                Reservation newRes = new Reservation(dao.getUserByUsername(username), dao.getFlightByFlightNo(flightNo), ticketAmountNum, totalCost);

                                dao.addReservation(newRes);

                                // xDone  write a record to Log table with message that new Reservation has been created.
                                LogRecord newLog = new LogRecord(2, username, "New Reservation: "+ newRes.getId());
                                dao.addLog(newLog);

                                System.out.println(dao.getAllReservations());
                                System.out.println(dao.getAllLogs());

                                finish();
                            }
                        });
                AlertDialog dialog2 = builder2.create();

                dialog2.show();

            }
        });

    }
}


