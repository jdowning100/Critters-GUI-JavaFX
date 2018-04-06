package assignment5;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Main extends Application{
	protected static boolean pauseTimer = true;
	private static int GUIheight = Params.world_height;
	private static int GUIwidth = Params.world_width;
	protected static BorderPane windowPane;
	protected static TextField statstext = new TextField();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Stage stageSettings = new Stage();
			primaryStage.setTitle("Critters Part II");
			stageSettings.setTitle("Change World Settings");
			GridPane options = new GridPane();
			options.setAlignment(Pos.BOTTOM_CENTER);
			stageSettings.setAlwaysOnTop(true);
			
			Label widthLabel = new Label("World Width");
			Label heightLabel = new Label("World Height");
			TextField widthtext = new TextField();
			TextField heighttext = new TextField();
			Label addCritter = new Label("Critter Name");
			TextField critterName = new TextField();
			Label critterNumber = new Label("Number of Critters");
			TextField numCritters = new TextField();
			Label steps = new Label("Steps to Advance");
			TextField numSteps = new TextField();
			Label stats = new Label("Critter Stats");
			
			Label setSeed = new Label("Set Seed");
			TextField seedText = new TextField();
			
		
			options.add(widthLabel, 0, 0);
			options.add(heightLabel, 0, 2);
			options.add(widthtext, 2, 0);
			options.add(heighttext, 2, 2);
			options.add(addCritter, 0, 4);
			options.add(critterName, 2, 4);
			options.add(critterNumber, 0, 6);
			options.add(numCritters, 2, 6);
			options.add(steps, 0, 8);
			options.add(numSteps, 2, 8);
			options.add(stats, 0, 10);
			options.add(statstext, 2, 10);
			options.add(setSeed, 0, 12);
			options.add(seedText, 2, 12);
			
			options.setPadding(new Insets(25, 25, 25, 25));
			
			Button update = new Button("Update Settings");
			
			options.add(update, 2, 14);
			
			
			Scene optionScene = new Scene(options, 500, 250);
			stageSettings.setScene(optionScene);
			stageSettings.show();

			
			update.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					if(!widthtext.getText().isEmpty()) {
						if(!(Integer.parseInt(widthtext.getText()) > GUIwidth))
							Params.world_width = Integer.parseInt(widthtext.getText());
						
					}
					
					if(!heighttext.getText().isEmpty()) {
						if(!(Integer.parseInt(heighttext.getText()) > GUIheight))
							Params.world_height = Integer.parseInt(heighttext.getText());
						
					}
					
					if(!critterName.getText().isEmpty()) {
						if(!numCritters.getText().isEmpty()) {
							for(int i = 0; i < Integer.parseInt(numCritters.getText()); i++) {
								try {
									Critter.makeCritter(critterName.getText());
								} catch (InvalidCritterException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							Critter.displayWorld(windowPane);
							System.out.println("Made " + numCritters.getText() +" "+ critterName.getText());
						} else {
							try {
								Critter.makeCritter(critterName.getText());
								Critter.displayWorld(windowPane);
								System.out.println("Made " + critterName.getText());
							} catch (InvalidCritterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					if(!numSteps.getText().isEmpty()) {
						for(int i = 0; i < Integer.parseInt(numSteps.getText()); i++)
							Critter.worldTimeStep();
						Critter.displayWorld(windowPane);
					}
					
					if(!statstext.getText().isEmpty()) {
						try {
							String s = "";
							List<Critter> critterlist = Critter.getInstances(statstext.getText());
							Class<?> tempclass = Class.forName("assignment5." + statstext.getText());
							Method tempmethod = tempclass.getMethod("runStats", List.class);
							s += (String) tempmethod.invoke(tempclass.newInstance(), critterlist);
							System.out.println(s);
							Stats(s);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							if(e instanceof InvalidCritterException)
								e.toString();
							else
								e.printStackTrace();
							
						}
					}
					if(!seedText.getText().isEmpty())
						Critter.setSeed(Integer.parseInt(seedText.getText()));
					
						
					
				}
			});
				
			windowPane = new BorderPane();
			GridPane buttons = new GridPane();
			windowPane.setBottom(buttons);
			
			
			
			
			buttons.setAlignment(Pos.BOTTOM_CENTER);
			Button play = new Button("Play");
			Button pause = new Button("Pause");
			Button clear = new Button("Clear");
			TextField speed = new TextField();
			Button settings = new Button("Settings");
			Button quit = new Button("Quit");
			buttons.add(play, 0, 2);
			buttons.add(pause, 0, 4);
			buttons.add(clear, 0, 6);
			buttons.add(speed, 2, 2);
			buttons.add(settings, 4, 6);
			buttons.add(quit, 2, 6);
			Scene mainScene = new Scene(windowPane, GUIwidth, GUIheight+100);
			
			
			play.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					pauseTimer = false;
					if(!speed.getText().isEmpty()) {
						Animator timer = new Animator(Integer.parseInt(speed.getText()));
					}
					else {
						Animator timer = new Animator(200);
					}
					
					
					System.out.println("Play Button Pressed");
				}
			}
			);
			
			pause.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					pauseTimer = true;
					
				}
			}
			);
			
			clear.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Critter.clearWorld();
					Critter.displayWorld(windowPane);
				}
			});
			
			settings.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					stageSettings.show();
				}
			});
			
			quit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Platform.exit();				
				}
			});
			
			
			Shape s = new Rectangle(GUIwidth, GUIheight);
			s.setStroke(Color.BLACK);
			s.setFill(Color.WHITE);
			windowPane.getChildren().add(s);
			Critter.displayWorld(windowPane);
			primaryStage.setScene(mainScene);
			primaryStage.show();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	private static boolean hasShown = false;
	private static Stage stageStats = new Stage();
	private static TextArea statsText = new TextArea();
	private static Scene statsScene = new Scene(statsText);
	
	public static void Stats(String stats) {
		
		statsText.setText(stats);
		stageStats.setAlwaysOnTop(true);
		
		if(!hasShown) {
			stageStats.setTitle("Critter Stats");
			stageStats.setScene(statsScene);
			stageStats.show();
			hasShown = false;
		}
		
		stageStats.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				hasShown = false;
			}
		});
		
		

		
		
		
	}

}

class Animator{
	
	public Animator(long period) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if(Main.pauseTimer)
							timer.cancel();
						else
							Critter.worldTimeStep();
						
						Critter.displayWorld(Main.windowPane);
						
						if(!Main.statstext.getText().isEmpty()) {
							try {
								String s = "";
								List<Critter> critterlist = Critter.getInstances(Main.statstext.getText());
								Class<?> tempclass = Class.forName("assignment5." + Main.statstext.getText());
								Method tempmethod = tempclass.getMethod("runStats", List.class);
								s += (String) tempmethod.invoke(tempclass.newInstance(), critterlist);
								System.out.println(s);
								Main.Stats(s);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
							
					}
				});
			}
		}, 0, period);
	}
}
