/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nordicstorm.robotics.parsable;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Andrew
 */
public class ParsedRobot extends IterativeRobot {
    private Preferences _preferences = Preferences.getInstance();
    private final String _commandName = "AutonomousCommand";
    private String _autonomousString = "";
    private String _defaultString = "";
    private CommandGroup _autonomousCommand;
    
    //Creates the AutonomousCommand preference in RobotPreferences if it doesn't exist  
    public void robotInit(){
        if(!_preferences.containsKey(_commandName)){
            _preferences.putString(_commandName, _defaultString);
            _preferences.save();
        }
    }

    //Cancels any autonomous command when you disable the robot
    public void disabledInit(){
        if(null != _autonomousCommand){
            _autonomousCommand.cancel();
        }
    }
    
    //Checks to see if you have changed the autonomous command while the robot is disabled
    public void disabledPeriodic(){
        String currentString = _preferences.getString(_commandName, _defaultString);
        if(!currentString.equals(_autonomousString)){
            System.out.println("New command detected:: Old one was " + _autonomousString + " New one was " + currentString);
            _autonomousString = currentString;
            _autonomousCommand = CommandParser.parseStringToCommandGroup(_autonomousString);
        }
    }
    
    //Starts the autonomous command in Autonomous Mode
    public void autonomousInit(){
        if(null != _autonomousCommand){
            _autonomousCommand.start();
        }
    }
    
    //Ends the autonomous command when Teleop starts
    public void teleopInit(){
        if(null != _autonomousCommand){
            _autonomousCommand.cancel();
        }
    }

    /**
     * Call this in RobotInit, pass in an Array of all classes which implement IParsable.
     * Each of the classes must have a "dummy constructor" for which takes no arguments and does not need to do anything.
     * The arguments you would normally used will be passed to the command via SetArguments() where you can set the class variables
     * as you would in the normal constructor
     * @param list
     */
    protected void enterCommandList(IParsable[] list){
        ParsableCommandsList.setParsableCommands(list);
        _autonomousCommand = CommandParser.parseStringToCommandGroup(_preferences.getString(_commandName, _defaultString));                
    }
    
   //Use the following three methods to parse the strings input to setArguments
    
    public static int parseInt(String str){
        try{
            return Integer.parseInt(str);
        }catch(Exception ex){
            System.out.println("Could not part integer from " + str);
            return 0;
        }
    }
    
    public static double parseDouble(String str){
        try{
            return Double.parseDouble(str);
        }catch(Exception ex){
            System.out.println("Could not part double from " + str);
            return 0;
        }
    }
    
    public static boolean parseBool(String str){
        if(str.toLowerCase().trim().equals("true")){
            return true;
        }else if (str.toLowerCase().trim().equals("false")){
            return false;
        }else{
            System.out.println("Could not parse boolean from " + str);
            return false;
        }
    }  

}