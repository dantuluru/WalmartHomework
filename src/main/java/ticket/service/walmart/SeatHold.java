package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;

public class SeatHold {
	private int id;
	private int numSeats;
	private String customerEmail;
	private HashMap<Level, ArrayList<Seat>> seatSelection;
	private LocalDateTime timeOfHold;
	
	/*Constructor*/
	public SeatHold(int id, int numSeats, String customerEmail) {
        this.id = id;
        this.numSeats = numSeats;
        this.customerEmail = customerEmail;
        this.timeOfHold = LocalDateTime.now();
	}
	/*getter for the hold request Id
	 * @return int which is unique for each request*/
	public int getId() {
        return id;
    }
	
	/*getter for the number of seats to be held
	 * @return int i.e number of seats held*/
	public int getNumSeats() {
        return numSeats;
    }
	
	/*getter for the customer email
	 * @return String i.e customer emailId*/
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    /*getter for seats selected to put on hold
     * @return HashMap<Level, ArrayList<Seat>> the set of selected seats*/
    public HashMap<Level, ArrayList<Seat>> getSeatSelection() {	
        return seatSelection;
    }
    
    /*getter for time at which the seats were put on hold
     * @return LocalDateTime the sets are set on hold*/
    public LocalDateTime getHoldTime() {
        return timeOfHold;
    }
    
    /*setter for seats selected to put on hold*/
	public void setSeatSelection(HashMap<Level, ArrayList<Seat>> heldSeats) {
		this.seatSelection = heldSeats;
	}

    
    
}
