package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jonathan Downing
 * jjd2547
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0> (I wish we had slip days)
 * Spring 2018
 */

import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        
        /**
         * Creates an infinite loop for the command prompt. Exists when user types the 'quit' command.
         * Uses the kb Scanner and String.split() to separate the commands by space.
         * For exceptions or errors, priority is given to the InvalidCritterException toString() method.
         */
        
        while(true) {
        	
        	System.out.print("critters>");
        	String input = kb.nextLine();
        	String[] cmds = input.split(" ");
        	
        	
        	switch(cmds[0]) {
        	case "quit" : 
        		if(cmds.length != 1)
        			System.out.println("error processing: "+ input);
        		else
        			System.exit(0);
        		break;
        		
        	case "show" : 
        		if(cmds.length != 1)
        			System.out.println("error processing: "+ input);
        		else
        			Critter.displayWorld();
        		break;
        		
        	case "step" : 
        		if(cmds.length != 1 && cmds.length != 2)
        			System.out.println("error processing: "+ input);
        		else if(cmds.length == 1)
        			Critter.worldTimeStep();
        		else if(cmds.length == 2) {
        			for(int i = 0; i < Integer.parseInt(cmds[1]); i++){
        				Critter.worldTimeStep();
        			}
        		}
        		break;
        		
        	case "seed" : 
        		if(cmds.length != 2)
        			System.out.println("error processing: "+ input);
        		else if(cmds.length == 2)
        			Critter.setSeed(Integer.parseInt(cmds[1]));
        		break;
        		
        	case "make" : 
        		if(cmds.length != 2 && cmds.length != 3)
        			System.out.println("error processing: "+ input);
        		else if(cmds.length == 2) {
					try {
						Critter.makeCritter(cmds[1]);
					} catch (InvalidCritterException e) {
						e.toString();
						System.out.println("error processing: "+ input);
					}
        		}
        		else if(cmds.length == 3){
        			for(int i = 0; i < Integer.parseInt(cmds[2]); i++) {
        				try {
							Critter.makeCritter(cmds[1]);
						} catch (InvalidCritterException e) {
							// TODO Auto-generated catch block
							e.toString();
							System.out.println("error processing: "+ input);
						}
        			}
        		}
        		break;
        		
        	case "stats" : 
        		if(cmds.length != 2)
        			System.out.println("error processing: "+ input);
        		else if(cmds.length == 2) {
        			try {
						List<Critter> critterlist = Critter.getInstances(cmds[1]);
						Class<?> tempclass = Class.forName("assignment4." + cmds[1]);
						Method tempmethod = tempclass.getMethod("runStats", List.class);
						tempmethod.invoke("null", critterlist);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						if(e instanceof InvalidCritterException)
							e.toString();
						else
							e.printStackTrace();
						System.out.println("error processing: "+ input);
					}
        			
        			
        		}
        		break;
        		
        		default: System.out.println("Invalid command: "+ input);
        		break;
        			
        		
    		
        		
        	}
        	
        	
        	
        }
        
        
        // System.out.println("GLHF");
        
        /* Write your code above */
      //  System.out.flush();

    }
}
