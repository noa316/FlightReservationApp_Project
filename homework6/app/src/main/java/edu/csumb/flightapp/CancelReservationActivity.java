package edu.csumb.flightapp;

import android.app.Activity;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import edu.csumb.flightapp.model.FlightDao;
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;
import edu.csumb.flightapp.model.Reservation;

public class CancelReservationActivity extends AppCompatActivity {

    private static final String CANCELRES_ACTIVITY = "CancelResActivity";

    List<Reservation> Reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CANCELRES_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelreservation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // login the user before continuing.
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivityForResult(intent, 0);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int rc = bundle.getInt(LoginActivity.CODE);
            String username = bundle.getString((LoginActivity.USERNAME));
            if (rc == 0) {
                continueCreate(username);
                return;

            }
        }
        // login was unsuccessful or user is not admin
        finish();
    }

    private void continueCreate(String username) {
        // after LOGIN by admin is successful
        // activate add flight button and show list of logrecrods.



        // get all log records from database
        Reservations = FlightRoom.getFlightRoom(this).dao().getReservationsByUsername(username);
        Log.d(CANCELRES_ACTIVITY, "Reservations read  " + Reservations.size());

        ListView records_view = findViewById(R.id.res_list);
        records_view.setAdapter(new ResListAdapter(CancelReservationActivity.this, Reservations));

        Button return_main_button = findViewById(R.id.return_button);
        return_main_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(CANCELRES_ACTIVITY, "onClick return called");
                finish();
            }
        });
    }

    public class ResListAdapter extends ArrayAdapter<Reservation> {

        public ResListAdapter(Activity context, List<Reservation> Reservations) {
            super(context, R.layout.cancel_layout, Reservations);
        }

        /*
           given an index into the records array - position
           create a rowView and set TextView contained in the row
           to the record data.
         */
        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            LayoutInflater inflater = CancelReservationActivity.this.getLayoutInflater();
            view = inflater.inflate(R.layout.cancel_layout, null, true);
            TextView rowField = view.findViewById(R.id.cancel_row_id);
            rowField.setText(Reservations.get(position).toString());


            rowField.setText("Reservation No." + Reservations.get(position).getId() + "\nDeparture: " + Reservations.get(position).getDeparture()
                    + "\nArrival: " + Reservations.get(position).getArrival() + "\nDeparture time: " + Reservations.get(position).getDepartureTime()
                    + "\nFlight No: " + Reservations.get(position).getFlightNum() + "\nTickets: " + Reservations.get(position).getTicketAmount());

            Button cancelBtn=(Button) view.findViewById(R.id.cancel_btn);

            cancelBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //Done confirm cancelation
                    AlertDialog.Builder builder2 =
                            new AlertDialog.Builder(CancelReservationActivity.this);

                    builder2.setTitle("Confirm Reservation Cancelation for " + Reservations.get(position).getUsername());
                    builder2.setMessage("Reservation num: "+ Reservations.get(position).getId()+ "\nFlight No: " + Reservations.get(position).getFlightNum()
                            + "\nDeparture: "+ Reservations.get(position).getDeparture() + "\nArrival: " + Reservations.get(position).getArrival()
                            + "\nTickets: "  + Reservations.get(position).getTicketAmount() + "\ntotal cost: $" + Reservations.get(position).getCost());
                    builder2.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FlightDao dao = FlightRoom.getFlightRoom(CancelReservationActivity.this).dao();
                                    LogRecord newLog = new LogRecord(1, Reservations.get(position).getUsername(), "Cancel Reservation: "+ Reservations.get(position).getId());
                                    dao.addLog(newLog);
                                    dao.deleteReservation(Reservations.get(position));


                                    System.out.println(dao.getAllReservations());
                                    System.out.println(dao.getAllLogs());

                                    finish();
                                }
                            });
                    AlertDialog dialog2 = builder2.create();

                    dialog2.show();

                }
            });




            return view;
        }

    }
}
