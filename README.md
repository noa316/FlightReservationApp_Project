# FlightReservationApp_Project
This was a Project I finished for my Software Design class where I created an Android App that acts as a Flight Reservation Simulator.

Otter Airways Flight Reservation System:

This is a Project done on Android Studio, mimicking a Flight reservation Android App. User’s are able to make an account, log in/log out of their account, and are able to search for flights and reserve flights if they have enough room for the amount of seats the user wants. The user can see how much their reservation will cost and they can also cancel before/after making a reservation. More explanation including graphs, use cases and screenshots below. 

Technologies: Java, Room Database, Android Studio.

Objective:
· Implement an object-oriented android application with multiple activities using RoomDB.
· Read and understand application requirements presented in the form of use cases.
· There are 10 test cases for this application. Below is a summary of the 15 test cases along with 15 screen shots showing that my application works for each test.
· The application should starts with some pre-set usernames and passwords and a special admin userid “!admiM2”.
· The application also starts with the 5 pre-defined flights.

UseCase Diagram

![Use Case Diagram](homework6/FlightApp_Images/UseCaseDiagram.png?raw=true "Use Case Diagram")
<img src="https://github.com/noa316/FlightReservationApp_Project/blob/main/homework6/FlightApp_Images/UseCaseDiagram.png?raw=true" width="400" height="500">

UML DIAGRAM:

![UML Diagram](homework6/FlightApp_Images/UMLDiagram.png?raw=true "UML Diagram")


Test Case descriptions (with Screenshots from Android studio Emulator)
(1)   When your app starts, it should provide the main menu GUI with four options of “Create Account”, “Reserve Seat”, “Cancel Reservation”, and “Manage System”.

![Main Menu](homework6/FlightApp_Images/Main_menu.png?raw=true "Main Menu")

(2)   Create a correct account
  	- Select "Create Account"
  	- Username: !!Byun7
  	- Password: !!Byun7
  	 The System informs the Customer that the new account has been created successfully.
After that, the System should display the main menu.

![Create Account](homework6/FlightApp_Images/CreateAccount.png?raw=true "Create Account")
![Successful Account Creation](homework6/FlightApp_Images/SuccessfulAccount.png?raw=true "Successful Account Creation")

(3)   Manage the system - Verify the new account
  	- Select "Manage System"
  	- Username: !admiM2
  	- Password: !admiM2
  	 The system should display
      	1. Transaction type: New account
      	2. Customer's username: !!Byun7
      	3. Transaction date: Depending on the test case 2 execution date.
      	4. Transaction time (= hour and minute): Depending on the test case 2 execution time.

![Verfiy New Account](homework6/FlightApp_Images/LogRecords_1.png?raw=true "Verify new Account")

(4)   Create an incorrect account
    (Invalid Username-Password)
  	- Select "Create Account"
  	- Username: byun7
  	- Password: !byun7
  	 The System should display an error message.
       	The Customer confirms the error message and can re-try or go back to main menu.
        
![Invalid Username-Password](homework6/FlightApp_Images/InvalidUsernm:pswrd_1.png?raw=true "Invalid Username-Password")

    (Duplicate Username)
    - Select "Create Account"
  	- Username: $BriAn7
  	- Password: 123aBc##
  	 System should display an error message.
       	The Customer confirms the error message.
        
![Username Already In Use](homework6/FlightApp_Images/UsernameAlreadyInUse_1.png?raw=true "Username Already In Use")

(5)   Reserve seat
  	- Select "Reserve Seat"
  	- Departure: Monterey
  	- Arrival: Seattle
  	- Number of tickets: 2
  	 System should display two possible flights of Otter201 and Otter205
                  	1. The Customer picks Otter201
                   	2. The Customer enters Username: !!Byun7
                                                          	Password: !!Byun7
      	3. The System confirms it and displays
           	- Username: !!Byun7
           	- Flight number: Otter201
           	- Departure: Monterey, 11:00(AM)
           	- Arrival: Seattle
           	- Number of tickets: 2
           	- Reservation number: Depending on the test.
           	- Total amount:  $401.00
      	4. The Customer confirms it

![Search Flights](homework6/FlightApp_Images/FlightSearch.png?raw=true "Search Flights")
![Flight List](homework6/FlightApp_Images/FlightList_1.png?raw=true "Flight List")
![Confirm Reservation](homework6/FlightApp_Images/ConfirmReservation_1.png?raw=true "Confirm Reservation")

(6)   Reserve seat - One more trial
  	- Select "Reserve Seat"
  	- Departure: Los Angeles
  	- Arrival: Monterey
  	- Number of tickets: 6
  	 System should display one possible flight of Otter102
                  	1. The Customer picks Otter102
                   	2. The Customer enters Username: !!Byun7
                                                          	Password: !!Byun7
      	3. The System confirms it and displays
           	- Username: !!Byun7
           	- Flight number: Otter102
       	    - Departure: Los Angeles, 1:00(PM)
           	- Arrival: Monterey
           	- Number of tickets: 6
           	- Reservation number: Depending on the test.
           	- Total amount:  $900.00
      	4. The Customer confirms it

![Flight List 2](homework6/FlightApp_Images/FlightList_2.png?raw=true "Flight List 2")
![Confirm Reservation 2](homework6/FlightApp_Images/ConfirmReservation_2.png?raw=true "Confirm Reservation 2")

(7)   Reserve seat - Handle No Flight Available
  	- Select "Reserve Seat"
  	- Departure: Seattle
  	- Arrival: Los Angeles
  	- Number of tickets: 1
  	 The System informs the Customer that there is no flight available for the departure/arrival.
      	The Customer can click “Back to search” and the app takes them back to the search page.
        
![No Flight Available](homework6/FlightApp_Images/NoMatchingFlights.png?raw=true "No Flight Available")

(8)   Cancel reservation
  	- Select "Cancel Reservation"
  	- Username: !!Byun7
  	- Password: !!Byun7
  	 System should display
      	1. All three reservations of “!!Byun7” with the detailed information.
        
  	- Select "Otter102" to cancel it.
     	1. The System requests confirmation and the Customer confirms it.
  	   2. The System should display the main menu.

![Cancel Reservations List ](homework6/FlightApp_Images/CancelFlights_1.png?raw=true "Cancel Reservtions List")
![Confirm Cancellation](homework6/FlightApp_Images/ConfirmCancellation_1.png?raw=true "Confirm Cancellation")

(9)   Manage the system - Verify the cancellation
  	- Select "Manage System"
  	- Username: !admiM2
  	- Password: !admiM2
  	 The System should display all transactions so far. But our focus in this test case is the last transaction which should include
      	1. Transaction type: Cancellation
      	2. Customer's username: !!Byun7
   	   3. Flight number: Otter102
      	4. Departure: Los Angeles, 1:00(PM)
      	5. Arrival: Monterey
      	6. Number of tickets: 6
      	7. Reservation number: Depending on the test.
      	8. Transaction date: Depending on the test case 11 execution date.
      	9. Transaction time (= hour and minute): Depending on the test case 11 execution time.

![Verfiy Cancellation](homework6/FlightApp_Images/LogRecords_2.png?raw=true "Verify Cancellation")

(10)   Manage the system - Add a new flight
  	- Select "Manage System"
  	- Username: !admiM2
  	- Password: !admiM2
  The System should display logs. But it's not important at this test case. 
  	- The System Operator selects the YES option to add a new flight.
     	1. Flight number: Otter301
     	2. Departure: Los Angeles
     	3. Arrival: Seattle
     	4. Departure time: 12:00(PM)
     	5. Flight capacity: 10
   	  6. Price: $350.50
  	 The System requests confirmation and the System Operator confirms it.
     

![Create Flight](homework6/FlightApp_Images/CreateFlight_1.png?raw=true "Create Flight")
![Confirm Flight Creation](homework6/FlightApp_Images/ConfirmCreateFlight.png?raw=true "Confirm Flight Creation")
