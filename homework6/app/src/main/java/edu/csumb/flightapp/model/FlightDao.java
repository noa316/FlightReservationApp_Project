package edu.csumb.flightapp.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


//xTODO update this class to include
//   query method to read LogRecords
//      use the annotation @Query("select * from LogRecord order by datetime desc")
//   insert method to insert new LogRecord into database

@Dao
public interface FlightDao {

    @Query("select * from Flight")
    List<Flight> getAllFlights();
    @Query("select * from Flight where flightNo=:flightNo")
    Flight getFlightByFlightNo(String flightNo);

    @Query("select * from Flight where departure=:departure and arrival=:arrival and availableSeats>=:availableSeats")
    List<Flight> searchFlight(String departure, String arrival, int availableSeats);

    // the generated id value is returned
    @Insert
    long addFlight(Flight flight);

    // return the number of rows actually updated.  Should be 1
    @Update
    int updateFlight(Flight flight);

    // xTODO -- remove comments on following


    @Query("select * from User")
    List<User> getAllUsers();

    @Query("select * from User where username = :username and  password= :password")
    User login(String username, String password);

    @Query("select * from User where username = :username")
    User getUserByUsername(String username);

    @Query("select * from User where username = :username and  password= :password")
    User getUserByUsernamePassword(String username, String password);

    @Insert
    void addUser(User user);


    @Query("select * from Reservation")
    List<Reservation> getAllReservations();

//    @Query("select * from Reservation where id = :id")
//    User getWithReservationNum(int id);

    @Query("select * from Reservation where username = :username")
    List<Reservation> getReservationsByUsername(String username);

    @Insert
    void addReservation(Reservation reservation);

    @Delete
    void deleteReservation(Reservation reservation);


    @Query("select * from LogRecord order by time desc")
    List<LogRecord> getAllLogs();

    @Query("select * from LogRecord order by time desc")
    List<LogRecord> getLogRecordOrderByTimeDesc();

    @Insert
    void addLog(LogRecord LogRecord);

}
