package edu.csumb.flightapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.LogRecord;

public class SearchFlightActivity extends AppCompatActivity {

    private static final String CREATERESERVATION_ACTIVITY = "CreateReserveActivity";

    List<LogRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATERESERVATION_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchflight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button create_button = findViewById(R.id.flight_search_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText departure = findViewById(R.id.departure);
                EditText arrival = findViewById(R.id.arrival);
                EditText ticketAmount = findViewById(R.id.tickets);

                String strDeparture = departure.getText().toString();
                String strArrival = arrival.getText().toString();
                String strTicketAmount = ticketAmount.getText().toString();



                // move on to CreateReservation.
                Intent intent = new Intent(v.getContext(), CreateReservationActivity.class);
                intent.putExtra("departure", strDeparture);
                intent.putExtra("arrival", strArrival);
                intent.putExtra("ticketAmount", strTicketAmount);
                startActivity(intent);
            }


        });
    }
    }
