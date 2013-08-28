/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nordicstorm.robotics.parsable_robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
     * Call this in RobotInit, pass in an Array of all classes which extend ParsableCommand.
     * Each of the classes must have a "dummy constructor" for which takes no arguments and does not need to do anything.
     * The arguments you would normally used will be passed to the command via SetArguments() where you can set the class variables
     * as you would in the normal constructor
     */
    protected void enterCommandList(ParsableCommand[] list){
        ParsableCommandsList.setParsableCommands(list);
        _autonomousCommand = CommandParser.parseStringToCommandGroup(_preferences.getString(_commandName, _defaultString));                
    }

}
