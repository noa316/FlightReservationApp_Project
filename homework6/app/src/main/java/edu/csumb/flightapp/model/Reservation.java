package edu.csumb.flightapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String username;
    private String flightNum;
    private String departure;
    private String arrival;
    private String departureTime;
    private int ticketAmount;
    private double cost;

    public Reservation() {}

    @Ignore
    public Reservation(User user, Flight flight, int ticketAmount, double cost){
        this.username = user.getUsername();
        this.flightNum = flight.getFlightNo();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.departureTime  = flight.getDepartureTime();
        this.ticketAmount = ticketAmount;
        this.cost = cost;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFlightNum() { return flightNum; }

    public void setFlightNum(String flightNum) { this.flightNum = flightNum; }

    public int getTicketAmount() { return ticketAmount; }

    public void setTicketAmount(int ticketAmount) { this.ticketAmount = ticketAmount; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getDeparture() { return departure; }

    public void setDeparture(String departure) { this.departure = departure; }

    public String getArrival() { return arrival; }

    public void setArrival(String arrival) { this.arrival = arrival; }

    public String getDepartureTime() { return departureTime; }

    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    @Override
    public String toString() {
        return "reservation number: "+ id + " user:  " + username + "\nflight: " + flightNum + " arrival: " + arrival + " departure: " + departure
                + "departure time: " + departureTime +  "\nticket amount: " + ticketAmount + " total cost: " + cost;
    }


}
