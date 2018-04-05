package assignment4;

import java.util.*;


/**
 * 
 * @author Jonathan
 * jjd2547
 * 
 * Critter walks half the time (given enough energy) and only fights his own species and algae (runs away otherwise)
 * Only reproduces if it has enough energy
 */
public class Critter2 extends Critter.TestCritter{

	@Override
	public void doTimeStep() {
		if(Critter.getRandomInt(2) == 1 && this.getEnergy() >= Params.walk_energy_cost)
			walk(Critter.getRandomInt(8));
		if(this.getEnergy() >= Params.min_reproduce_energy)
			reproduce(new Critter2(), 6);
	}

	@Override
	public boolean fight(String opponent) {
		if (opponent.toString().equals("2") || opponent.toString().equals("@"))
			return true;
		else if(this.getEnergy() >= Params.run_energy_cost)
			run(Critter.getRandomInt(5));
			
		return false;
	}
	
	public String toString() {
		return "2";
	}
	
	public void test (List<Critter> l) {
		
	}
	
	/**
	 * Gives a list of the Critter2s, each respective x,y coordinates and energy.
	 * Note that in order to use the Critter.TestCritter functions this classes extends it.
	 * @param critters
	 */
	public static void runStats(java.util.List<Critter> critters) {
		
		System.out.print("" + critters.size() + " total Critter2s    ");
		for (Critter obj : critters) {
			System.out.println();
			Critter2 c = (Critter2) obj;
			System.out.println("x coord: "+c.getX_coord() + "   y coord: " + c.getY_coord() + "   energy: " + c.getEnergy());

		}
		
	}
	
}
