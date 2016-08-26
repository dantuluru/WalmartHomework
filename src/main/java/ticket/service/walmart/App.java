package ticket.service.walmart;

import java.util.Optional;

/**
 * author Mounika Dantuluru
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Level l1 = new Level(1, "ORCHESTRA", 100.00, 25, 50);
        Level l2 = new Level(2, "MAIN", 75.00, 20, 100);
        Level l3 = new Level(3, "BALCONY1", 50.00, 15, 100);
        Level l4 = new Level(4, "BALCONY2", 40.00, 15, 100);       
        Venue venue = new Venue();
        venue.addLevel(l1);
        venue.addLevel(l2);
        venue.addLevel(l3);
        venue.addLevel(l4);    
        TicketServiceImpl tService = new TicketServiceImpl(venue,60);
        SeatHold hold = tService.findAndHoldSeats(50, Optional.empty(), Optional.empty(), "test@gmail.com");
        try {
        	Thread.sleep(1000);
        } catch (InterruptedException e) {
    		Thread.currentThread().interrupt();
        }
        tService.reserveSeats(hold.getId(), hold.getCustomerEmail());

    }
}
