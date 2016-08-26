package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */

public class Level {
	private int levelNumber;
	private String levelName;
	private double price;
	private int rows;
	private int seatsInRow;
	private Seat[][] seats; 
	
	/*
	 * Constructor
	 */
	public Level(int levelNumber, String levelName, double price, int rows, int seatsInRow){
		this.levelNumber = levelNumber;
		this.levelName = levelName;
		this.price = price;
		this.rows= rows;
		this.seatsInRow = seatsInRow;
		this.seats = new Seat[this.rows][this.seatsInRow];
		
		for(int r=0; r<rows; r++){
			for(int s=0; s<seatsInRow; s++) {
				seats[r][s] = new Seat();
				seats[r][s].setRowNumber(r);
				seats[r][s].setSeatNumber(s);
				seats[r][s].setSeatState(State.AVAILABLE);
			}
		}
	}
	
	/*
	 * @return int which gives total available seats in this level
	 */
	public int getAvailableSeats() {
		int availability = 0;
		for(int r=0; r<this.rows; r++){
			for(int s=0; s<this.seatsInRow; s++) {
				if (seats[r][s].getSeatState() == State.AVAILABLE){
					availability++;
				}
			}
		}
		return availability;
	}
	
	/*getter for Level Number
	 * @return int which is the level number*/
	public Integer getLevel() {
		return levelNumber;
	}
	
	/*getter for Level Name
	 * @return String which is the level name*/
	public String getLevelName() {
		return levelName;
	}
	
	/*getter for Level price
	 * @return double which is the price for each seat in the level*/
	public double getPrice() {
		return price;
	}
	
	/*getter for rows
	 * @return int which indicates number of rows in the level*/
	public Integer getRows() {
		return rows;
	}
	
	/*getter for SeatsInRow
	 * @return int which indicates number of seats in a row in the level*/
	public Integer getSeatsInRow() {
		return seatsInRow;
	}
	
	/*getter for Seats
	 * @return Seat[][] which gives all seats in the level*/
	public Seat[][] getSeats() {
		return seats;
	}
	
	/*setter for Level Number*/
	public void setLevel(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	/*setter for Level Name*/
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	/*setter for Price*/
	public void setPrice(double price) {
		this.price = price;
	}
	
	/*setter for Row*/
	public void setRows(int rows) {
		this.rows= rows;
	}
	
	/*setter for SeatsInRow*/
	public void setSeatsInRow(int seatsInRow) {
		this.seatsInRow = seatsInRow;
	}
	
	
}
