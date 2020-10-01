package edu.csumb.flightapp.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


//Done  update this class to include LogRecord as entity

@Database(entities={Flight.class, User.class, Reservation.class, LogRecord.class}, version=1)
public abstract class FlightRoom extends RoomDatabase {
    // singleton
    private static FlightRoom instance;

    public abstract FlightDao dao();

    public static FlightRoom getFlightRoom(final Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FlightRoom.class,
                    "FlightDB") // database name
                    .allowMainThreadQueries()  // temporary for now
                    .build();
        }
        return instance;
    }

    public void loadData(Context context){

        // if flight table is empty, then load data for  flights
        List<Flight> flight_list = FlightRoom.getFlightRoom(context).dao().getAllFlights();
        if (flight_list.size() == 0) {
            Log.d("FlightRoom", "loading data ");
            loadFlights(context);
        }
        //  Done  do similar for users
        List<User> user_list = FlightRoom.getFlightRoom(context).dao().getAllUsers();
        if (user_list.size() == 0) {
            Log.d("FlightRoom", "loading data ");
            loadUsers(context);
        }

        List<Reservation> reservation_list = FlightRoom.getFlightRoom(context).dao().getAllReservations();
        if (reservation_list.size() == 0) {
            Log.d("FlightRoom", "loading data ");
            loadReservations(context);
        }

        List<LogRecord> LogRecords_list = FlightRoom.getFlightRoom(context).dao().getAllLogs();
        if (LogRecords_list.size() == 0) {
            Log.d("FlightRoom", "loading data ");
            loadLogRecords(context);
        }

    }

    private void loadLogRecords(Context context){
        FlightDao dao = getFlightRoom(context).dao();

        LogRecord log1 = new LogRecord();
        dao.addLog(log1);

        Log.d("FlightRoom", "1 Log Record added to database");
    }

    private void loadReservations(Context context){
        FlightDao dao = getFlightRoom(context).dao();

        Reservation Res1 = new Reservation();
        dao.addReservation(Res1);

        Log.d("FlightRoom", "1 Reservation added to database");
    }

    private void loadUsers(Context context){
        FlightDao dao = getFlightRoom(context).dao();

        User otter1 = new User("A@lice5", "@cSit100");
        dao.addUser(otter1);
        User otter2 = new User("$BriAn7", "123aBc##");
        dao.addUser(otter2);
        User otter3 = new User("!chriS12!", "CHrIS12!!");
        dao.addUser(otter3);


        Log.d("FlightRoom", "3 Users added to database");


    }

    private void loadFlights(Context context){
        FlightDao dao = getFlightRoom(context).dao();

        Flight otter101 = new Flight("Otter101", "Monterey", "Los Angeles", "10:30(am)", 10, 150);
        dao.addFlight(otter101);
        Flight otter102 = new Flight("Otter102", "Los Angeles", "Monterey", "1:00(pm)", 10, 150);
        dao.addFlight(otter102);
        Flight otter201 = new Flight("Otter201", "Monterey", "Seattle", "11:00(am)", 5, 200.50);
        dao.addFlight(otter201);
        Flight otter205 = new Flight("Otter205", "Monterey", "Seattle", "3:45(pm)", 15, 150.00);
        dao.addFlight(otter205);
        Flight otter202 = new Flight("Otter202", "Seattle", "Monterey", "2:10(pm)", 5, 200.50);
        dao.addFlight(otter202);
        Log.d("FlightRoom", "5 flights added to database");


    }
}
