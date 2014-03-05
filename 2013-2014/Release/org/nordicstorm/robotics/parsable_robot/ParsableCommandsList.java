package org.nordicstorm.robotics.parsable;

import edu.wpi.first.wpilibj.command.WaitCommand;
import org.nordicstorm.robotics.Robot;

/**
 *
 * @author Andrew
 */
public class ParsableCommandsList {
    private static class ParsableWaitCommand extends WaitCommand implements IParsable{

        public ParsableWaitCommand(){
            this(0);
        }
        
        public ParsableWaitCommand(double time){
            super(time);   
        }
        
        public String getIdentifier() {
            return("W");
        }

        public void setArguments(String[] args) {
            setTimeout(ParsedRobot.parseDouble(args[0]));
        }
        
    }
    
    
    private static IParsable[] _commandList; 
    
    //Sets the list of ParsableCommands
    public static void setParsableCommands(IParsable[] commands){  
    //Possibly check for commands with duplicate identifiers?
        System.out.println("Command list updated");
        IParsable[] finalCommands = new IParsable[commands.length + 1];
        for(int i =0; i < commands.length; i++){
            finalCommands[i] = commands[i];
        }
        finalCommands[commands.length] = new ParsableWaitCommand();
        _commandList = finalCommands;
    }
    
    //Returns the first command found which returns a matching identification letter
    public static IParsable getCommandFromLetter(String identifier) throws Exception{
        if(_commandList != null){
            for(int i=0; i<_commandList.length; i++){
                if(_commandList[i].getIdentifier().equals(identifier)){
                    return _commandList[i];
                }
            }
            throw new Exception("Command with letter " + identifier + " not found");
        }else{
            throw new Exception("List of parsable commands not found, did you call enterCommandList() in RobotInit?");
        }

    }    
}