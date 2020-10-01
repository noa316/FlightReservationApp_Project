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
import edu.csumb.flightapp.model.FlightRoom;
import edu.csumb.flightapp.model.LogRecord;

public class ManageSystemActivity extends AppCompatActivity {

    private static final String MANAGESYSTEM_ACTIVITY = "ManageSystemActivity";

    List<LogRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MANAGESYSTEM_ACTIVITY, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
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
            if (rc == 0) {
                // check that user is admin
                boolean isAdmin = bundle.getBoolean(LoginActivity.ISADMIN);
                if (isAdmin) {
                    continueCreate();
                    return;
                }
            }
        }
        // login was unsuccessful or user is not admin
        finish();
    }

    private void continueCreate() {
        // after LOGIN by admin is successful
        // activate add flight button and show list of logrecrods.
        Button return_main_button = findViewById(R.id.add_flight_button);
        return_main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MANAGESYSTEM_ACTIVITY, "onClick add flight called");
                Intent intent =
                        new Intent(ManageSystemActivity.this, CreateFlightActivity.class);
                startActivity(intent);
            }
        });

        // get all log records from database
        records = FlightRoom.getFlightRoom(this).dao().getLogRecordOrderByTimeDesc();
        Log.d(MANAGESYSTEM_ACTIVITY, "records read  " + records.size());

        ListView records_view = findViewById(R.id.log_list);
        records_view.setAdapter(new LogListAdapter(this, records));
    }

    public class LogListAdapter extends ArrayAdapter<LogRecord> {

        public LogListAdapter(Activity context, List<LogRecord> records) {
            super(context, R.layout.row_layout, records);
        }

        /*
           given an index into the records array - position
           create a rowView and set TextView contained in the row
           to the record data.
         */
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = ManageSystemActivity.this.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.row_layout, null, true);
            TextView rowField = rowView.findViewById(R.id.row_id);
            rowField.setText(records.get(position).toString());
            return rowView;
        }

    }
}
