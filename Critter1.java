package assignment4;

import java.util.*;
/**
 * 
 * @author Jonathan
 * jjd2547
 * 
 * Critter always walks, fights, and reproduces all the time - he's crazy!
 * Only walks in 4 directions
 */
public class Critter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		int walkdir = Critter.getRandomInt(4);
		walk(walkdir);
		reproduce(new Critter1(), Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String opponent) {
		return true;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
	/**
	 * Gives a list of the Critter1s, each respective x,y coordinates and energy.
	 * Note that in order to use the Critter.TestCritter functions this classes extends it.
	 * @param critters
	 */
	public static void runStats(java.util.List<Critter> critters) {
	
		System.out.print("" + critters.size() + " total Critter1s    ");
		for (Critter obj : critters) {
			System.out.println();
			Critter1 c = (Critter1) obj;
			System.out.println("x coord: "+c.getX_coord() + "   y coord: " + c.getY_coord() + "   energy: " + c.getEnergy());

		}
		
	}
}

