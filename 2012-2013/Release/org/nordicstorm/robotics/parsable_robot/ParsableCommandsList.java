package org.nordicstorm.robotics.parsable_robot;

/**
 *
 * @author Andrew
 */
public class ParsableCommandsList {
    private static ParsableCommand[] _commandList; 
    
    //Sets the list of ParsableCommands
    public static void setParsableCommands(ParsableCommand[] commands){
    //Possibly check for commands with duplicate identifiers?
        System.out.println("Command list updated");
        _commandList = commands;
    }
    
    //Returns the first command found which returns a matching identification letter
    public static ParsableCommand getCommandFromLetter(String identifier) throws Exception{
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
