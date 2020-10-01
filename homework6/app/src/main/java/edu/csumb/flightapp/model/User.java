package edu.csumb.flightapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

        @PrimaryKey(autoGenerate = true)
        private long id;
        private String username;
        private String password;

        //no arg constructor
        public User() {}

        @Ignore
        public User(String username, String password){
                this.username = username;
                this.password = password;
        }

        //getters and setters
        public void setId(long id) { this.id = id; }

        public void setUsername(String username) { this.username = username; }

        public void setPassword(String password) { this.password = password; }

        public long getId() { return id; }

        public String getUsername() { return username; }

        public String getPassword() { return password; }

        //toString method

        @Override
        public String toString() {
                return "User{" +
                        "id=" + id +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        '}';
        }


}
