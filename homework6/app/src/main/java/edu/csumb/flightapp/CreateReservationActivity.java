package edu.csumb.flightapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.Flight;
import edu.csumb.flightapp.model.FlightRoom;

public class CreateReservationActivity extends AppCompatActivity {

    private static final String CREATERESERVATION_ACTIVITY = "CreateReserveActivity";
    private String ticketAmountbought;

    List<Flight> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CREATERESERVATION_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createreservation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String strDeparture = getIntent().getStringExtra("departure");
        String strArrival = getIntent().getStringExtra("arrival");
        String strTicketAmount = getIntent().getStringExtra("ticketAmount");

        int ticketAmountNum = Integer.parseInt(strTicketAmount);

        ticketAmountbought = strTicketAmount;

        Button return_main_button = findViewById(R.id.return_button);
        return_main_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(CREATERESERVATION_ACTIVITY, "onClick return called");
                finish();
            }
        });

        // get all relevant flights from database and assign to flights array
        flights = FlightRoom.getFlightRoom(this).dao().searchFlight(strDeparture, strArrival, ticketAmountNum);
        Log.d(CREATERESERVATION_ACTIVITY, "flights count "+flights.size());

        ListView flights_view = findViewById(R.id.flight_list);
        flights_view.setAdapter( new FlightListAdapter( this, flights) );




    }

    public class FlightListAdapter extends ArrayAdapter<Flight> {

        public FlightListAdapter (Activity context, List<Flight> flights){
            super(context, R.layout.reserve_layout , flights);
        }

        /*
           given an index into the flights array - position
           create a rowView and set TextView contained in the row
           to the flight data.
         */
        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            LayoutInflater inflater=CreateReservationActivity.this.getLayoutInflater();
            view=inflater.inflate(R.layout.reserve_layout, null,true);
            TextView rowField = view.findViewById(R.id.reserve_row_id);


            //set the value of a row in the ListView to the flight info using toString()
            rowField.setText("Flight No." + flights.get(position).getFlightNo() + "\nDeparture: " + flights.get(position).getDeparture()
                             + "\nArrival: " + flights.get(position).getArrival() + "\nDeparture time: " + flights.get(position).getDepartureTime()
                             + "\nFlight Capacity: " + flights.get(position).getAvailableSeats() + "\nPrice per seat: $" + flights.get(position).getPrice());

            Button reserveBtn=(Button) view.findViewById(R.id.reserve_btn);

            reserveBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Done link loginActivity

                    String strPrice = String.valueOf(flights.get(position).getPrice());
                    Log.d(CREATERESERVATION_ACTIVITY, "onClick Reserve flight called");
                    Intent intent = new Intent(CreateReservationActivity.this, ReserveLoginActivity.class);
                    intent.putExtra("departure", flights.get(position).getDeparture());
                    intent.putExtra("arrival", flights.get(position).getArrival());
                    intent.putExtra("flightNo", flights.get(position).getFlightNo());
                    intent.putExtra("ticketAmount", ticketAmountbought);
                    intent.putExtra("price", strPrice);
                    startActivity(intent);
                }
            });


            return view;
        }

    }

}
