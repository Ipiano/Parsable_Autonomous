
package org.nordicstorm.robotics.commands;

import org.nordicstorm.robotics.parsable_robot.ParsableCommand;
import org.nordicstorm.robotics.main.ExampleRobot;

/**
 *
 * @author bradmiller
 */
public class ExampleCommand extends ParsableCommand {
    private int exampleInt;
    private boolean exampleBool;
    
    //An empty constructor must be available for the autonomous command to use. It will pass in arguments through setArguments();    
    public ExampleCommand() {
    }
    
    public ExampleCommand(int arg1, boolean arg2){
        exampleInt = arg1;
        exampleBool = arg2;
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    //The name of this command to be used in the debug statements
    public String getName() {
        return "ExampleMethod";
    }

    //The the parser checks the input string, the letter returned here will
    //denote that this command is to be added to the list.
    //This must return a single letter.
    public String getIdentifier() {
        return "E";
    }

    //The parser will set the arguments here, each one will be a string and
    //must be parsed accordingly.
    public void setArguments(String[] args) {
        exampleInt  = getInt(args[0]);
        exampleBool = getBool(args[1]);
    }
}
