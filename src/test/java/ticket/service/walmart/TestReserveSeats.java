package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.util.Optional;

import junit.framework.TestCase;

public class TestReserveSeats extends TestCase {
	
	private Venue venue;
    
    public void setUp() {
        this.venue = new Venue(  );
        Level l1 = new Level(1, "ORCHESTRA", 100.00, 25, 50);
	    Level l2 = new Level(2, "MAIN", 75.00, 20, 100);
	    Level l3 = new Level(3, "BALCONY1", 50.00, 15, 100);
	    Level l4 = new Level(4, "BALCONY2", 40.00, 15, 100);
	    venue.addLevel(l1);
	    venue.addLevel(l2);
	    venue.addLevel(l3);
	    venue.addLevel(l4);
    }

	public void testReserving(){     
        TicketServiceImpl tService = new TicketServiceImpl(venue,60);
        SeatHold hold = tService.findAndHoldSeats(50, Optional.empty(), Optional.empty(), "mounika@test.com");
        tService.reserveSeats(hold.getId(), hold.getCustomerEmail());
        try {
			assertTrue( venue.numAvailableSeats(Optional.empty()) == 6200);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public void testReservingAfterHoldTime(){		
		TicketServiceImpl tService = new TicketServiceImpl(venue,2);
        SeatHold hold = tService.findAndHoldSeats(50, Optional.empty(), Optional.empty(), "mounika@test.com");
        try{
	          Thread.sleep(4000);
	      }catch (InterruptedException e){
	          Thread.currentThread().interrupt();
	      }
         String out = tService.reserveSeats(hold.getId(), hold.getCustomerEmail());
        try {
			assertTrue(out == null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

}
