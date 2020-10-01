package edu.csumb.flightapp.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class LogRecord {
    //made with new user, reservation made  or canceled

    public static final int LOG_TYPE_CANCEL = 1;
    public static final int LOG_TYPE_RESERVE = 2;
    public static final int LOG_TYPE_NEWUSER = 3;


    @PrimaryKey(autoGenerate = true)
    private long id;

    private int type;
    private String time;
    private String username;
    private String msg;

    public LogRecord() {}

    @Ignore
    public LogRecord(int type, String username, String msg){
        this.type = type;

        Date now = new Date();
        this.time = now.toString();

        this.username = username;
        this.msg = msg;

    }

    private String typeString() {
        switch (type) {
            case 1: return "CANCEL";
            case 2: return "RESERVE";
            case 3: return "NEW_USER";
            default: return "INVALID";
        }
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "id=" + id +
                ", time=" + time +
                ", type=" + typeString() +
                ", username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    //has date and time log record was created
    //username involved
    //reservation number
    //type (1 for new user, 2 for create reservation, 3 cancel reservation
}
