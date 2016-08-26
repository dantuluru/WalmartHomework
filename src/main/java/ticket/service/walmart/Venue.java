package ticket.service.walmart;
/**
 * author Mounika Dantuluru
 *
 */
import java.util.ArrayList;
import java.util.Optional;

public class Venue {
	
	private ArrayList<Level> levels = new ArrayList<Level>();
	
	/*getter for levels 
	 * @returns ArrayList<Level> i.e array list of all 
	 * the levels in the venue*/
	public ArrayList<Level> getLevels() {
		return levels;
	}
	
	/*@param level is a level given to add to the venue*/
	public void addLevel(Level level) {
		this.levels.add(level);
	}
	/*@param levelId is the id of a level in a venue
	 * @returns Level of that level Id*/
	public Level getLevel(int levelId) throws Exception {
		for(Level level : levels) {
			if (level.getLevel() == levelId) {
				return level;
			}
		}
		throw new Exception("Level is not valid");
	}
	
	/*@param levelId is the id of a level in a venue
	 * @returns int returns number of available seats for the given level Id 
	 * or else gives all available seats in the venue*/
	public synchronized int numAvailableSeats(Optional<Integer> levelId) throws Exception{
		if (levelId.isPresent()) {
			Level level = getLevel(levelId.get());
			return level.getAvailableSeats();
		} else {
			int numAvailableSeats = 0;
			for(Level level : levels) {
				numAvailableSeats += level.getAvailableSeats();
			}
			return numAvailableSeats;
		}
	}
	/*@param venueLevelMin is the id of a minimum priced level in a venue 
	 * venueLevelMax is the id of a maximum priced level in a venue 
	 * @returns int returns number of available seats for the given levels and their in between levels */
	public synchronized int availableSeats(Integer venueLevelMin, Integer venueLevelMax) throws Exception {
		int numAvailableSeats = 0;
		for(int i=venueLevelMin; i>=venueLevelMax; i--) {
			Level level = getLevel(i);
			numAvailableSeats += level.getAvailableSeats();
		}
		return numAvailableSeats;
	}
	
	
}
