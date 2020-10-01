package edu.csumb.flightapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;
import edu.csumb.flightapp.model.User;


public class TestdbActivity extends AppCompatActivity {

    private static final String TESTDB_ACTIVITY = "TestdbActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TESTDB_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button return_main_button = findViewById(R.id.return_button);
        return_main_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TESTDB_ACTIVITY, "onClick return called");
                finish();
            }
        });

        Flight flight = new Flight("Otter 101", "from city", "to city", "0800 AM", 10, 95.75);

        FlightDao dao = FlightRoom.getFlightRoom(this).dao();
        long flightId = dao.addFlight(flight);
        flight.setId( flightId);
        List<Flight> flights = dao.getAllFlights();
        Log.d(TESTDB_ACTIVITY, "flights size "+flights.size());
        TextView flight_testdb = findViewById(R.id.flights_testdb);
        if (flights.size() >= 1) flight_testdb.setTextColor(Color.GREEN);

        // create and retrieve a Reservation
         Reservation reservation = new Reservation();
         dao.addReservation(reservation);
         List<Reservation> reserves = dao.getAllReservations();
         TextView reservation_testdb = findViewById(R.id.reserve_testdb);
         if (reserves.size() >= 1) reservation_testdb.setTextColor(Color.GREEN);

        // list users
         User user = new User("Mike", "Tyson");
         dao.addUser(user);
         List<User> users = dao.getAllUsers();
         TextView user_testdb = findViewById(R.id.users_testdb);
         if (users.size() >= 1) user_testdb.setTextColor(Color.GREEN);

//         create and retrieve LogRecord
         LogRecord record = new LogRecord(1, "Mike", "none");
         dao.addLog(record);
         List<LogRecord> records  = dao.getAllLogs();
         TextView LogRecord_testdb = findViewById(R.id.logrec_testdb);
         if (records.size() >= 1) LogRecord_testdb.setTextColor(Color.GREEN);


    }

}
