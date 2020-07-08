package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Class the generates a SowingAction if the Farmer is standing next to a Dirt.
 * 
 * @author Kee Pei Jiin
 */
public class SowingBehaviour implements Behaviour{
	/**The probability for Farmer to sow at each turn is 33%*/
	private static final int SOWING_PROBABILITY = 33;
	/**A Random object that is used to model the Farmer's sowing probability at each turn*/
	private Random rand = new Random();
	
	/**
	 * Returns an action that allows the Farmer to sow a crop when he is standing next to a Dirt.
	 * 
	 * @param actor		The actor that enacts this behaviour. In the game design, the only actor that has this behaviour is Farmer.
	 * @param map		The map the actor is on
	 * @return 			A SowingAction, or null if the harvest action is impossible to be carried out that turn
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		// if the integer generated by the Random object is smaller than 33, then the Farmer will sow at that turn
		if (rand.nextDouble()*100 <= SOWING_PROBABILITY) {
			// determine if the Farmer is standing next to a Dirt
			for (Exit exit: map.locationOf(actor).getExits()) {
				Location possibleSowLoc = exit.getDestination();
				// Among all the types of Ground, only Dirt has the capability of SOW
				if (possibleSowLoc.getGround().hasCapability(ZombieCapability.SOW)) {
					return new SowingAction(possibleSowLoc.x(), possibleSowLoc.y(), map);	
				}
			}
		}
		return null;
	}
}
