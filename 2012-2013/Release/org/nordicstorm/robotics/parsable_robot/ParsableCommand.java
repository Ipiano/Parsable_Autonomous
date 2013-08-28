/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nordicstorm.robotics.parsable_robot;

//Change this import to your own CommandBase

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 * @author Andrew
 */
public abstract class ParsableCommand extends Command {
    
    public ParsableCommand() {
    }
    
    /**
     * Implement this method and return a name for the command. This is only used for debug purposes. 
     */
    public abstract String getName();
    
    /**
     *Return a one-character String which unique for this class.
     *This is the character you will use in a command string to call this class.
     */
    public abstract String getIdentifier();
    
    /**
     * Use this method to set variables which are set in your normal constructor
     * Keep track of the order, as that will be the order you use for the Input String.

     */
    public abstract void setArguments(String[] args);
    
    
    //Use the following three methods to parse the strings input to setArguments
    
    protected int getInt(String str){
        try{
            return Integer.parseInt(str);
        }catch(Exception ex){
            System.out.println("Could not part integer from " + str);
            return 0;
        }
    }
    
    protected double getDouble(String str){
        try{
            return Double.parseDouble(str);
        }catch(Exception ex){
            System.out.println("Could not part double from " + str);
            return 0;
        }
    }
    
    protected boolean getBool(String str){
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
