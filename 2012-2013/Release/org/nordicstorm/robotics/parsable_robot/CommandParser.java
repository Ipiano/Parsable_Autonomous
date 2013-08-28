/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nordicstorm.robotics.parsable_robot;

import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 * @author Andrew
 */
public class CommandParser {
    
    //Set the delimiters for parsing separate commands and separate arguments for each command
    private static char _commandDelim = ';';
    private static char _argDelim = ',';
    private static boolean _printDebug;
    private static boolean _output;
    
    /**
     * 
     * @param stringToParse
     * @return 
     */
    public static CommandGroup parseStringToCommandGroup(String stringToParse){
        return parseStringToCommandGroup(stringToParse, true);
    }
    
    /**
     * 
     * @param stringToParse
     * @param printDebug
     * @return  _
     */
    public static CommandGroup parseStringToCommandGroup(String stringToParse, boolean printDebug){
        return parseStringToCommandGroup(stringToParse, printDebug, true);
    }
    
    /**
     * 
     * @param stringToParse
     * @param printDebug
     * @param outputToDriverStation
     * @return 
     */
    public static CommandGroup parseStringToCommandGroup(String stringToParse, boolean printDebug, boolean outputToDriverStation){
                _printDebug = printDebug;
                _output = outputToDriverStation;
                print("Parsing new command");
        
                String returnString = "";
                String successful = "Parse Successful";
		String newSequence = stringToParse;                
                boolean shouldRun = true;
		int currentIndex = 0;
                CommandGroup result = new CommandGroup();
                
                //Checks if the command is not empty or null
                if(newSequence == null || newSequence.trim().equals("")){
                    shouldRun = false;
                }
                
                //Parses through the sequence
		while(currentIndex < newSequence.length() && shouldRun){
                        print("Parsing this command " + newSequence);
                        
                        //Parses out the first command in the sequence
			int nextSemi = newSequence.indexOf(_commandDelim, currentIndex);
			if (nextSemi < 0) {
                            nextSemi = newSequence.length();
			}
			String currentCommand = newSequence.substring(currentIndex, nextSemi).trim();
                        print("CurrentCommand = " + currentCommand);
                        
                        //Checks if the command is to run in parallel, and gets the identifying letter
			String commandStart = currentCommand.substring(0,1);
                        boolean parallel = false;
                        if ("P".equals(commandStart)){
                            commandStart = currentCommand.substring(1,2);
                            parallel = true;
                        }
                        
                        //Removes the identifying letter, leaving the arguments   
                        print("CommandStart = " + commandStart);
                        String args = "";
                        if (currentCommand.length() > 1){
                            int startArgs = 1;
                            if(parallel){
                                startArgs = 2;
                            }
                            args = currentCommand.substring(startArgs).trim();
                        }
                        print("Arguments are " + args);
                        
                        //Checks if there is a command that has the identifiying letter
			ParsableCommand thisCommand = null;
			String commandText = "";
			String creationText = "";
                        try{
                            //Create a new command of this type, and set the arguments
                            thisCommand = (ParsableCommand)(ParsableCommandsList.getCommandFromLetter(commandStart)).getClass().newInstance();
                            thisCommand.setArguments(getArgs(args));
                            commandText = "Ran " + thisCommand.getName() + " with arguments " + args;
                            creationText = "Added " + thisCommand.getName() + " with arguments " + args;
                     
                            
                            //Adds the command to the commandGroup
                            if (parallel){
                                result.addParallel(thisCommand);
                                commandText = commandText + "--Parallel";
                                creationText = creationText + "--Parallel";
                            }else{
                                result.addSequential(thisCommand);
                            }
                            
                            //Add a command to print debug at runtime, and print a line of debug now
                            result.addSequential(new PrintCommand(commandText));	
                            print(creationText);
                            returnString = returnString + " " + commandStart;  
                            
                        }catch(Exception ex){
                            successful = "Parse completed but encountered errors";
                            System.out.println(ex.getMessage());
                        }
			currentIndex = nextSemi + 1;
		}
                if(_output){
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, (returnString + "                             "));
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, (successful + "                             "));
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, (Timer.getFPGATimestamp() + "                          "));
                    DriverStationLCD.getInstance().updateLCD(); 
                }
                
                return result;
        }
        
        //Returns the arguments in a String Array
        private static String[] getArgs(String base){
                String[] result = new String[10];
		int currentIndex = 0;
		int argCount = 0;
		while(currentIndex < base.length()){
                    int nextComma = base.indexOf(_argDelim, currentIndex);
                    if (nextComma < 0) {
                        nextComma = base.length();
                    }
                    if(result.length == argCount){
                        result = resizeStringArray(result, result.length + 10);
                    }
                    result[argCount] =(base.substring(currentIndex, nextComma));
                    argCount ++;
                    currentIndex = nextComma + 1;
		}
                
                return resizeStringArray(result, argCount);
                
        }
        
        private static String[] resizeStringArray(String[] startArray, int size){
            String[] tempArray = new String[size];
            
            //Custom Array copy because Arrays.copyFrom() doesn't exist in this version of Java
            for(int i=0; i < Math.min(tempArray.length, startArray.length); i++){
                tempArray[i] = startArray[i];
            }
            return tempArray;
        }
        
        private static void print(String text){
            if(_printDebug){
                System.out.println(text);
            }
        }

	
}
