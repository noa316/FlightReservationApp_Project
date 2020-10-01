package edu.csumb.flightapp;
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
import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightDao;
import static edu.csumb.flightapp.model.FlightRoom.getFlightRoom;
public class CreateFlightActivity extends AppCompatActivity {
    private static final String CREATE_FLIGHT_ACTIVITY = "CreateFlightActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATE_FLIGHT_ACTIVITY, "onCreate called");
        final FlightDao dao = getFlightRoom(this).dao();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createflight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final int[] count = {0};
        String message = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateFlightActivity.this);
        Button create = findViewById(R.id.add_flight_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(CREATE_FLIGHT_ACTIVITY, "onClick called");
                EditText flightNo = findViewById(R.id.flight_number);
                String flightNoStr = flightNo.getText().toString();

                EditText departureInfo = findViewById(R.id.departure_info);
                String departureInfoStr = departureInfo.getText().toString();

                EditText arrivalInfo = findViewById(R.id.arrival_info);
                String arrivalStr = arrivalInfo.getText().toString();

                EditText departureTime = findViewById(R.id.departure_time);
                String departureTimeStr = departureTime.getText().toString();

                EditText flightCapacity = findViewById(R.id.flight_capacity);
                String flightCapacityStr = flightCapacity.getText().toString();

                int flightCapacityInt = Integer.parseInt(flightCapacityStr);
                EditText price = findViewById(R.id.price_info);
                String priceInfo = price.getText().toString();

                double priceDouble = Double.parseDouble(priceInfo);
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateFlightActivity.this);
                Flight newFlight = new Flight();

                try {
                    newFlight = new Flight(flightNoStr, departureInfoStr, arrivalStr, departureTimeStr, flightCapacityInt, priceDouble);
                } catch(Exception e){
                    builder.setTitle("Flight Already Exists");
                    builder.setMessage("Invalid Information").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreateFlightActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                builder.setTitle("Confirm this flight information to add");
                final Flight finalNewFlight = newFlight;
                for(int i = 0; i < dao.getAllFlights().size(); i++){
                    boolean checker = dao.getAllFlights().get(i).equals(finalNewFlight);
                    if(checker){
                        builder.setTitle("Flight Already Exists");
                        builder.setMessage("Re-enter a new flight").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CreateFlightActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
                Log.d(CREATE_FLIGHT_ACTIVITY, "onClick return called");
                builder = new AlertDialog.Builder(CreateFlightActivity.this);
                builder.setTitle("Does all the information look correct?");
                builder.setMessage(finalNewFlight.toString());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Log.d(CREATE_FLIGHT_ACTIVITY, "onClick show flights called");
                        //grab and add info here (try/catch)
                        dao.addFlight(finalNewFlight);
                        Intent intent = new Intent(CreateFlightActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent intent = new Intent(CreateFlightActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return;
            }
        });
    }
}