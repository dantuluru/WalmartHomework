package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
public class Seat {

	private int rowNumber;
	private int seatNumber;
	private State seatState;
	
	/*getter for Row Number of the seat location
	 * @return int i.e the row number*/
	public int getRowNumber(){
		return rowNumber;
	}
	
	/*getter for seat Number in a row of the seat location
	 * @return int i.e the seat number*/
	public int getSeatNumber(){
		return seatNumber;
	}
	
	/*getter for the state of the seat
	 * @return State enum*/
	public State getSeatState(){
		return seatState;
	}
	
	/*setter for Row Number of the seat location*/
	public void setRowNumber(int rowNumber){
		this.rowNumber = rowNumber;
	}
	
	/*setter for seat Number in a row of the seat location*/
	public void setSeatNumber(int seatNumber){
		this.seatNumber = seatNumber;
	}
	
	/*setter for the state of the seat*/
	public void setSeatState(State seatState){
		this.seatState = seatState;
	}
	
}
