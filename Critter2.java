package assignment5;

import java.util.*;

import javafx.scene.paint.Color;


/**
 * 
 * @author Jonathan
 * jjd2547
 * 
 * Critter walks half the time (given enough energy) and only fights his own species and algae (runs away otherwise)
 * Only reproduces if it has enough energy
 * Always looks before walking. He's cautious.
 */
public class Critter2 extends Critter.TestCritter{

	@Override
	public void doTimeStep() {
		int rand = Critter.getRandomInt(8);
		if(Critter.getRandomInt(2) == 1 && this.getEnergy() >= (Params.walk_energy_cost + Params.look_energy_cost)) {
			if(!look(rand,false).equals("1"))
				walk(Critter.getRandomInt(8));
		}
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
	public String runStats(java.util.List<Critter> critters) {
		
		String s = ("" + critters.size() + " total Critter2s    ");
		for (Critter obj : critters) {
			Critter1 c = (Critter1) obj;
			s += ("\nx coord: "+c.getX_coord() + "   y coord: " + c.getY_coord() + "   energy: " + c.getEnergy());

		}
		
		return s;
		
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.TRIANGLE;
	}
	
	public javafx.scene.paint.Color viewOutlineColor(){
		return Color.AQUA;
	}
}
