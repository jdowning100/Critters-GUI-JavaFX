package assignment5;

import java.util.*;

import javafx.scene.paint.Color;
/**
 * 
 * @author Jonathan
 * jjd2547
 * 
 * Critter always walks, fights, and reproduces all the time - he's crazy!
 * Only walks in 4 directions
 */
public class Critter1 extends Critter.TestCritter {
	public String stats;
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
	 * @return 
	 */
	public String runStats(java.util.List<Critter> critters) {
	
		String s = ("" + critters.size() + " total Critter1s    ");
		for (Critter obj : critters) {
			Critter1 c = (Critter1) obj;
			s += ("\nx coord: "+c.getX_coord() + "   y coord: " + c.getY_coord() + "   energy: " + c.getEnergy());

		}
		return s;
		
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.STAR;
	}
	
	public javafx.scene.paint.Color viewOutlineColor(){
		return Color.RED;
	}
}

