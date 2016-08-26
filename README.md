# Walmart Coding Assignmet

## Dependencies

- Java 1.8
- Maven 3.3.9
- JUnit

## Description

This is a Ticket Service application for a Venue with four different levels of seating arrangement. The application is built to support a hold time counter so that the user cannot hold the tickets for a long duration of time. The tickets are sold in a synchronized pattern. The seats are suggested according to the level. The lower the level the higher the preference. Level 4 (Balcony 2) is the lowest level. Level 1 (Orchestra) is the highest level. The tickets are automatically released from hold if they are on hold for a longer duration than the timer. On reserve the user can only reserve the tickets if they are still on hold. To get the available seats, considers the timer for held seats and gives a total count after release of any timed out seats.

## Running the test cases
```bash
git clone https://github.com/dantuluru/WalmartHomework.git
cd WalmartHomework
mvn test
```

## Classes

1. State

	Describes the state of the seat, whether it is available, held or reserved.

2. Seat

	Provides row number, seat number and state.

3. Level

	Contains information about Level. The level initially sets all of its seats to available state.

4. Venue

	It consists of all the levels and gives total available seats in the venue.

5. SeatHold

	When a SeatHold request is made all the request details are stored with that of the seats selection.
