package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map.Entry;

import junit.framework.TestCase;

public class TestFindAndHoldSeats extends TestCase {
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

	public void testHoldAllSeats(){		
		TicketServiceImpl tTicketService = new TicketServiceImpl(venue, 2);
        Optional<Integer> minLevel = Optional.empty();
        Optional<Integer> maxLevel = Optional.empty();
        
        SeatHold seatHold1 = tTicketService.findAndHoldSeats(50, minLevel, maxLevel, "test@gmail.com");
        SeatHold seatHold2 = tTicketService.findAndHoldSeats(6200, minLevel, maxLevel, "test@gmail.com");
        
        HashMap<Level, ArrayList<Seat>> hold1 = seatHold1.getSeatSelection();
        HashMap<Level, ArrayList<Seat>> hold2 = seatHold2.getSeatSelection();
        
        int seatsReservedHold1 = 0;
        int seatsReservedHold2 = 0;
        
        for(Entry<Level, ArrayList<Seat>> entry : hold1.entrySet()){
        	seatsReservedHold1 += entry.getValue().size();
        }
        for(Entry<Level, ArrayList<Seat>> entry : hold2.entrySet()){
        	seatsReservedHold2 += entry.getValue().size();
        }
        
        int countSeatsAvailable = 0;
		for(Level level : venue.getLevels()){
			countSeatsAvailable += level.getAvailableSeats();
		}
		
		assertTrue(countSeatsAvailable == 0);
		assertTrue(seatsReservedHold1 == 50);
        assertTrue(seatsReservedHold2 == 6200);
    }
	
	public void testInvalidEmailId(){		
		TicketServiceImpl tTicketService = new TicketServiceImpl(venue, 10);
        Optional<Integer> minLevel = Optional.empty();
        Optional<Integer> maxLevel = Optional.empty();
        
        SeatHold seatHold1 = tTicketService.findAndHoldSeats(10, minLevel, maxLevel, "hello.test.com");
        assertTrue(seatHold1 == null);
	}
	
	public void testInvalidNumSeats(){		
		TicketServiceImpl tTicketService = new TicketServiceImpl(venue, 10);
        Optional<Integer> minLevel = Optional.empty();
        Optional<Integer> maxLevel = Optional.empty();
        
        SeatHold seatHold1 = tTicketService.findAndHoldSeats(70000, minLevel, maxLevel, "test@gmail.com");
        assertTrue(seatHold1 == null);
	}
	
	public void testInvalidLevels(){		
		TicketServiceImpl tTicketService = new TicketServiceImpl(venue, 10);
        Optional<Integer> minLevel = Optional.of(5);
        Optional<Integer> maxLevel = Optional.empty();
        
        SeatHold seatHold = tTicketService.findAndHoldSeats(10, minLevel, maxLevel, "test@gmail.com");
        assertTrue(seatHold == null);
        
        minLevel = Optional.of(0);
        seatHold = tTicketService.findAndHoldSeats(10, minLevel, maxLevel, "test@gmail.com");
        assertTrue(seatHold == null);
        
        minLevel = Optional.of(0);
        maxLevel = Optional.of(0);
        seatHold = tTicketService.findAndHoldSeats(10, minLevel, maxLevel, "test@gmail.com");
        assertTrue(seatHold == null);
	}
	
	public void testHoldAfterTimerReleased(){	
		TicketServiceImpl tTicketService = new TicketServiceImpl(venue, 2);
        Optional<Integer> minLevel = Optional.empty();
        Optional<Integer> maxLevel = Optional.empty();
        
        tTicketService.findAndHoldSeats(6250, minLevel, maxLevel, "test@gmail.com");
        int availableSeats = tTicketService.numSeatsAvailable(minLevel);
        assertTrue(availableSeats == 0);
        try{
	          Thread.sleep(4000);
	      }catch (InterruptedException e){
	          Thread.currentThread().interrupt();
	      }
        availableSeats = tTicketService.numSeatsAvailable(minLevel);
        assertTrue(availableSeats == 6250);
	}

}
