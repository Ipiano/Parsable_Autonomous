/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.nordicstorm.robotics.main;


import edu.wpi.first.wpilibj.command.Scheduler;
import org.nordicstorm.robotics.parsable_robot.ParsableCommand;
import org.nordicstorm.robotics.parsable_robot.ParsedRobot;
import org.nordicstorm.robotics.commands.ExampleCommand;

public class ExampleRobot extends ParsedRobot {

    //Make sure you call the super of robotInit()
    public void robotInit() {
        super.robotInit();
        
        //Call enterCommandList and pass in an Array of all the commands you have which extend ParsableCommand
        ParsableCommand[] commands = {new ExampleCommand()};
        enterCommandList(commands);
    }
    
    //Make sure you call the super of autonomousInit()
    public void autonomousInit() {
        super.autonomousInit();
    }
    
    //Make sure you call the super of disabledPeriodic()   
    public void disabledPeriodic(){
        super.disabledPeriodic();
    }
    
    //Make sure you call the super of teleopInit()
    public void teleopInit() {
        super.teleopInit();
    }

    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

}
