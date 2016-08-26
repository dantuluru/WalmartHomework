=================================****************************=========================

Walmart Coding Assignment

TicketService Application

Created By: Mounika Dantuluru 08/25/2016

=================================****************************=========================

Dependencies:

Java 1.8
Maven 3.3.9
JUnit

=================================****************************=========================

Description:

This is a Ticket Service application for a venue with four different levels of seating arrangement. 
The application is built to support a hold time counter so that the user cannot hold the tickets 
for a long duration of time. The tickets are sold in a synchronized pattern. 
The seats are suggested according to the level. The lower the level the higher their preference. 
The tickets are automatically released from hold if they are on hold
for a longer duration than the timer is set for. On reserve the user can only reserve the tickets 
if the are still on hold. To get the available seats also considers the held seats timer and 
gives a total count after release of held seats.

Classes:

1. State 
	It is an enum describing the state of a seat weather it is available, hold or reserved.
2. Seat
	It consists of the initial data of seat such as its row number and seat number in that row and its state.
3. Level
	It contains all the Level details and its seats details. The level initially sets all of its seats to available state.
4. Venue
	It consists of all the levels and give total available seats in the venue.
5. SeatHold
	When a SeatHold request is maid all the request details are stored with that of the seats selection. 
	The minLevel should be the minimum priced level and the maxLevel should be the level id with maximum price

=================================****************************=========================

How to run the application:

1. Unzip WalmartHomework folder
2. Open terminal
3. Change the location to WalmartTicketService directory
4. Build the project using the command $mvn clean
5. To run the test cases using the command $mvn test

=================================************The End**********===========================
