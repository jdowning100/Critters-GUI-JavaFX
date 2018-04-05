package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Downing
 * jjd2547
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Spring 2018
 */

import java.util.Iterator;
import java.lang.reflect.Constructor;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */



public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		if(max <= 0)
			return 0;
		else
			return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	private static boolean Encounter;
	private int x_coord;
	private int y_coord;
	private boolean isAlive;
	private boolean moveDone;
	private boolean isBaby;
	
	/**
	 * Walk method, only moves 1 space in 8 different directions.
	 * @param direction
	 */
	protected final void walk(int direction) {
		if(energy <= 0)
			return;
		if(moveDone) {
			energy = energy - Params.walk_energy_cost;
			if(energy <= 0)
				isAlive = false;
			return;
		}
		int prevX = x_coord;
		int prevY = y_coord;
		
		switch(direction) {
		case 0: x_coord = moveX(1);		//East
				break;
		case 1: y_coord = moveY(-1);	//NE
				x_coord = moveX(1);
				break;
		case 2: y_coord = moveY(-1);	//North
				break;
		case 3: y_coord = moveY(-1);	//NW
				x_coord = moveX(-1);
				break;
		case 4: x_coord = moveX(-1);	//West
				break;
		case 5: x_coord = moveX(-1);	//SW
				y_coord = moveY(1);
				break;		
		case 6: y_coord = moveY(1);		//South
				break;
		case 7: y_coord = moveY(1);		//SE
				x_coord = moveX(1);
				break;
		default: break;
		}
		
		if(isBaby)
			return;
		
		boolean canMove = true;
		if(Critter.Encounter) {
				for(Critter y : population) {
					if(this.x_coord == y.x_coord && this.y_coord == y.y_coord)
						canMove = false;
				}
			}
		
		if(!canMove) {
			x_coord = prevX;
			y_coord = prevY;
			return;
		}
		
		moveDone = true;
		energy = energy - Params.walk_energy_cost;
		if(energy <= 0)
			isAlive = false;
		}
	
	/**
	 * Run method. Moves 2 spaces in 8 different directions.
	 * @param direction
	 */
	protected final void run(int direction) {
		if(energy <= 0 && !isBaby)
			return;
		if(moveDone) {
			energy = energy - Params.run_energy_cost;
			if(energy <= 0)
				isAlive = false;
			return;
		}
		
		int prevX = x_coord;
		int prevY = y_coord;
		
		switch(direction) {
		case 0: x_coord = moveX(2);
				break;
		case 1: y_coord = moveY(-2);
				x_coord = moveX(2);
				break;
		case 2: y_coord = moveY(-2);
				break;
		case 3: y_coord = moveY(-2);
				x_coord = moveX(-2);
				break;
		case 4: x_coord = moveX(-2);
				break;
		case 5: x_coord = moveX(-2);
				y_coord = moveY(2);
				break;
		case 6: y_coord = moveY(2);
				break;
		case 7: y_coord = moveY(2);
				x_coord = moveX(2);
				break;
		default: break;
		}
		
		
		boolean canMove = true;
		if(Critter.Encounter) {
				for(Critter y : population) {
					if(this.x_coord == y.x_coord && this.y_coord == y.y_coord)
						canMove = false;
				}
			}
		
		if(!canMove) {
			x_coord = prevX;
			y_coord = prevY;
			return;
		}
					
		moveDone = true;
		energy = energy - Params.run_energy_cost;
		if(energy <= 0)
			isAlive = false;
	}
	/**
	 * Wraps the x coordinate to ensure that a Critter doesn't move off the map (as the map wraps around).
	 * Needs some debugging.
	 * @param distance
	 * @return int
	 */
	private int moveX(int distance) {
		int moved = 0;
		if((distance + x_coord) < 0) {
			moved = Params.world_width - distance;
		}
		else if((Params.world_width - 1) < (x_coord + distance)) {
			moved = distance - 1;
		}
		else
			moved = x_coord + distance;
		return moved;
	}
	
	/**
	 * Wraps the y coordinate to ensure that a Critter doesn't move off the map (as the map wraps around).
	 * Needs some debugging.
	 * @param distance
	 * @return int
	 */
	private int moveY(int distance) {
		int moved = 0;
		if((distance + y_coord) < 0) {
			moved = Params.world_height - distance;
		}
		else if((Params.world_height - 1) < (y_coord + distance)) {
			moved = distance - 1;
		}
		else
			moved = y_coord + distance;
		
		return moved;
	}
	
	/**
	 * Uses a newly created offspring Critter object to give it energy and coordinates
	 * Adds it to babies List
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy || this.isAlive == false)
			return;
		offspring.energy = this.energy / 2;
		this.energy = offspring.energy;
		offspring.isBaby = true;
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.walk(direction);
		
		babies.add(offspring);
		
		
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		try {
			 Class<?> critter = Class.forName("assignment4."+ critter_class_name);
			 Constructor constructor = critter.getConstructor();
			 Critter newCritter = (Critter) constructor.newInstance();
			 
			 newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			 newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			 newCritter.energy = Params.start_energy;
			 newCritter.isAlive = true;
			 population.add(newCritter);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}
		
		
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class<?> c = Class.forName("assignment4." + critter_class_name);
			
			for(Critter x : population) {
				if(c.isInstance(x))
					result.add(x);
			}
		} catch (Exception e) {
			
			throw new InvalidCritterException(critter_class_name);
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
			
			if(new_energy_value <= 0)
				super.isAlive = false;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	/**
	 * Performs the timestep, in the order given below:
	 *  1. increment timestep; timestep++;
 		2. doTimeSteps();
		3. Do the fights. doEncounters();
		4. updateRestEnergy();
		5. Generate Algae genAlgae();
		6. Move babies to general population. population.addAll(babies); babies.clear();
		
		Note that these methods do not exist, they are simply a description of what the below method does.
	 */
	public static void worldTimeStep() {
		// Complete this method.
		Critter.Encounter = false;
		for(Critter x : population) {
			x.moveDone = false;
			x.doTimeStep();
			if(x.energy > 0)
				x.isAlive = true;
			else
				x.isAlive = false;
		}
		
		for(Critter x: population) {
			for(Critter y: population) {
				if(x.x_coord == y.x_coord && x.y_coord == y.y_coord && x.isAlive && y.isAlive && !x.equals(y)) {
					Critter.Encounter = true;
					boolean xfight = x.fight(y.toString());
					boolean yfight = y.fight(x.toString());
					if(x.x_coord == y.x_coord && x.y_coord == y.y_coord && y.isAlive && x.isAlive) {
						//they're gonna fight
						int rollx, rolly;
						if(xfight) {
							rollx = Critter.getRandomInt(x.energy);
						}
						else
							 rollx = 0;
						if(yfight) {
							rolly = Critter.getRandomInt(y.energy);
						}
						else
							rolly = 0;
						if(rollx > rolly) {
							y.isAlive = false;
							x.energy += y.energy/2;
							
						}
						if(rolly > rollx) {
							x.isAlive = false;
							y.energy += x.energy/2;
						}
						if(rollx == rolly) {
							if(Critter.getRandomInt(2) == 1) {
								y.isAlive = false;
								x.energy += y.energy/2;
							}
							else {
								x.isAlive = false;
								y.energy += x.energy/2;
							}
						}
						
					}
				}
			}
		}
		
		for(Critter c : population) {
			c.energy -= Params.rest_energy_cost;
			if(c.energy <= 0)
				c.isAlive = false;
		}
		
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				Critter.makeCritter("Algae");
				
			} catch (InvalidCritterException e) {
				// TODO Auto-generated catch block
				e.toString();
			}
		}
		
		for(Critter b : babies) {
			population.add(b);
			b.isBaby = false;
			b.isAlive = true;
		}
		babies.clear();
		
		for(Iterator<Critter> iterator = population.iterator(); iterator.hasNext(); ) {
			Critter c = iterator.next();
			if(c.isAlive = false || c.energy <= 0)
				iterator.remove();
		}
		
	}
	
	public static void displayWorld() {
		// Complete this method.
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++)
			System.out.print("-");
		System.out.print("+");
		System.out.println();
		char[][] array = new char[Params.world_height][Params.world_width+2];
		
		for(int i = 0; i < Params.world_height; i++) {
			for(int j = 0; j < Params.world_width; j++) {
					array[i][j] = ' ';
			}
			
		}
		
		for(Critter c : population) {
			try {
				array[c.x_coord][c.y_coord] = c.toString().charAt(0);
			}
			catch(Exception e) {
				continue;
			}
		}
		
		for(int i = 0; i < Params.world_height; i++) {
			for(int j = 0; j < Params.world_width + 2; j++) {
				if(j == 0 || j == Params.world_width + 1)
					System.out.print('|');
				else
					System.out.print(array[i][j]);
			}
			System.out.println();
			
		}
		
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++)
			System.out.print("-");
		System.out.print("+");
		System.out.println();
	}
}
