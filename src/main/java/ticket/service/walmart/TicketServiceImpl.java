package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketServiceImpl implements TicketService {
	
	private Venue venue;
	private HashMap<Integer, SeatHold> allHolds;
	private static int idCounter = 0;
	private int holdTimer;
	
	// Constructor
	public TicketServiceImpl(Venue venue, int holdTimer) {
		this.venue = venue;
		this.holdTimer = holdTimer;
		this.allHolds = new HashMap<Integer, SeatHold>();
	}
	
	/*create a unique id for each hold request*/
	public static synchronized int createID() {
	    return idCounter++;
	}
	
	/**
	* The number of seats in the requested level that are neither held nor reserved
	* @param venueLevel a numeric venue level identifier to limit the search
	* @return the number of tickets available on the provided level or -1 if the input data is invalid
	*/
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		int numAvailableSeats = 0;
		if(venueLevel.isPresent() && (venueLevel.get()>4 || venueLevel.get()<1)){
				return -1;
		}
		try {	
			removeHold();
			numAvailableSeats = venue.numAvailableSeats(venueLevel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numAvailableSeats;
	}

	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param minLevel the minimum venue level
	* @param maxLevel the maximum venue level
	* @param customerEmail unique identifier for the customer
	* @return a SeatHold object identifying the specific seats and related
	information
	*/
	@Override
	public synchronized SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
		Integer venueMinLevel = minLevel.orElse(4);
		Integer venueMaxLevel = maxLevel.orElse(1);	
		if(venueMinLevel < venueMaxLevel){
			int temp = venueMinLevel;
			venueMinLevel = venueMaxLevel;
			venueMaxLevel = temp;
		}
		if(numSeats<=0 || venueMinLevel>4 || venueMaxLevel<1 || isNotEmail(customerEmail)) {
			return null;
		}
		SeatHold hold = new SeatHold(createID(), numSeats, customerEmail);	
		
			
		try {
			removeHold();
			HashMap<Level, ArrayList<Seat>> heldSeats = holdSeats(numSeats, venueMinLevel, venueMaxLevel);
			if(heldSeats==null){
				return null;
			}
			hold.setSeatSelection(heldSeats);
			addHold(hold); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hold;
	}

	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param minLevel the minimum venue level
	* @param maxLevel the maximum venue level
	* @param customerEmail unique identifier for the customer
	* @return a HashMap<Level, ArrayList<Seat>> object identifying the specific seats that are put on hold
	*/
	public synchronized HashMap<Level, ArrayList<Seat>> holdSeats(int numSeats, int venueMinLevel, int venueMaxLevel) throws Exception {
		HashMap<Level,ArrayList<Seat>> seatsOnHold = new HashMap<Level,ArrayList<Seat>>();
		boolean flag = numSeats <=  venue.availableSeats(venueMinLevel,venueMaxLevel);
		if (flag) {
			while(numSeats!=0 && venueMinLevel >= venueMaxLevel) {
				Level level = venue.getLevel(venueMinLevel);
				ArrayList<Seat> seatsPerLevel = new ArrayList<Seat>();
				if(level.getAvailableSeats() !=0) {
					Seat[][] seats = level.getSeats();
					for(int r=0; r<level.getRows(); r++) {
						for(int s=0; s<level.getSeatsInRow() && numSeats > 0; s++) {
							if (seats[r][s].getSeatState() == State.AVAILABLE) {
								seats[r][s].setSeatState(State.HOLD);
								seatsPerLevel.add(seats[r][s]);
								numSeats--;
							}
						}
					}
					seatsOnHold.put(level, seatsPerLevel);
					venueMinLevel--;
				}
			}
			return seatsOnHold;
		}		
		return null;		
	}
	
	/*The seats selected to be held is added to the HAshMap with all the hold requests*/
	public void addHold(SeatHold hold) {
		allHolds.put(hold.getId(), hold);
	}
	
	/*To check if the customer Emial given to hold reuest is a valid emial id
	 * @return true if the email is valid*/
	private boolean isNotEmail(String customerEmail) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(customerEmail);
		boolean matchFound = m.matches();
		if (matchFound) {
		    return false;
		}
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	* Commit seats held for a specific customer
	*
	* @param seatHoldId the seat hold identifier
	* @param customerEmail the email address of the customer to which the seat hold
	is assigned
	* @return a reservation confirmation code
	*/
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		removeHold();
		if(!allHolds.containsKey(seatHoldId)) {
			return null;
		}
		SeatHold hold = allHolds.get(seatHoldId);
		HashMap<Level, ArrayList<Seat>> selection = hold.getSeatSelection();
		for(Entry<Level, ArrayList<Seat>> entry : selection.entrySet()){
			ArrayList<Seat> seats = entry.getValue();
			for(Seat seat : seats){
				seat.setSeatState(State.RESERVED);
			}	
		}
		allHolds.remove(seatHoldId);
		
		return UUID.randomUUID().toString();
	}
	
	/** Checks all the seats set on hold and checks if its been held 
	 * for more time then remove from hold and set seat as available
	 * **/
	public synchronized void removeHold() {
		for(Iterator<Entry<Integer, SeatHold>> it = allHolds.entrySet().iterator(); it.hasNext();) {
			Entry<Integer, SeatHold> entry = it.next();
			SeatHold hold = entry.getValue();
			LocalDateTime currentTime = LocalDateTime.now();
			long seconds = ChronoUnit.SECONDS.between(hold.getHoldTime(), currentTime);
			if(seconds > holdTimer) {
				HashMap<Level, ArrayList<Seat>> selection = hold.getSeatSelection();
				for(Entry<Level, ArrayList<Seat>> set : selection.entrySet()) {
					ArrayList<Seat> seats = set.getValue();
					for(Seat seat : seats) {
						if(seat.getSeatState() == State.HOLD){
							seat.setSeatState(State.AVAILABLE);
						}
					}
				}
				it.remove();
			}
		}
	}
	
	
}
