package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.util.Optional;

import junit.framework.TestCase;

public class TestNumSeatsAvailable extends TestCase {
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

	public void testLevelIsNull(){	
		TicketServiceImpl tService = new TicketServiceImpl(venue,70);
		int seatsAvailabile = tService.numSeatsAvailable(Optional.empty());
		int countSeatsAvailable = 0;
		for(Level level : venue.getLevels()){
			countSeatsAvailable += level.getAvailableSeats();
		}
		assertTrue(seatsAvailabile == countSeatsAvailable);
	}
	
	public void testLevelIsInvalid(){
		TicketServiceImpl tService = new TicketServiceImpl(venue,70);
		int seatsAvailabile = tService.numSeatsAvailable(Optional.of(5));
		assertTrue(seatsAvailabile == -1);		
	}
	
	public void testLevel() throws Exception{
		TicketServiceImpl tService = new TicketServiceImpl(venue,70);
		int seatsAvailabile = tService.numSeatsAvailable(Optional.of(2));
		int countSeatsAvailable = 0;
		Level level = venue.getLevel(2);
		countSeatsAvailable = level.getAvailableSeats();	
		assertTrue(seatsAvailabile == countSeatsAvailable);
		
	}
}
