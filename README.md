Parsable_Autonomous
===================

What is this?
  This is a way for FRC teams to easily create, organize, and switch between multiple autonomous sequences.

Can I use this? 
  This will only work for FRC teams who use the FRC Java CommandBase Robot
  and set up and use the FRC SmartDashboard

Why does this help?
  Under usual circumstances, FRC teams who are developing autonomous programs must go through the long process of
  uploading the program to the robot before they could test the program. Then, if something needed to be changed, even
  a single variable, the entire program had to be uploaded again, a process which could take up to a minute each time.
  
  These classes, when utilized, would allow single variables like that, and even the entire autonomous sequence
  to be changed without even having to open the netbeans IDE. The robot wouldn't have to be restarted, and multiple tests
  could take place in the time it would have taken to do only one. In addition to testing advantages, this method can also
  be used to alter autonomous mode while the robot is on the field before a match.

Why is it called "Parsable_Autonomous"?
  These classes make use of the FRC SmartDashboard and the RobotPreferences to recieve real-time user input. This input
  is then decoded and used to make a sequence of commands to be run. When the user input is changed, the autonomous sequence
  changes.
  For example, if the user inputs the following:
  D, 5,1,1; S;
  It could change autonomous to drive for 5 seconds at full speed, and then stop.
  If that doesn't work, it could be changed, and next time autonous was run, it would represent the new input,
  and the robot would not have to be restarted.
  
How to implement this in your robot.
  Setting up SmartDashboard
    Step 1: Install the SmartDashboard program from here(http://firstforge.wpi.edu/sf/frs/do/listReleases/projects.smartdashboard/frs.installer)
    Step 2: On the connect the SmartDashboard to your robot, and add the RobotPreferences widget.
    
  Adding the program to your code
    Step 1: Find the necessary files; the files needed are in the package org.nordicstorm.robotics.parsable_robot
    Step 2: Add these classes to your project, perhaps in a new package, and fix any imports and package names.
  
  Extend the main robot class
    Step 1: Extend your main Robot class from ParsableRobot instead of IterativeRobot. Don't worry, ParsableRobot also extends IterativeRobot.
    Step 2: Make sure that you implement and call the super method of each of the following methods:
      robotInit(), autonomousInit(), teleopInit, and disabledPeriodic()
    Step 3: After the super method in robotInit, call enterCommandList() and pass in an Array of all the command you will use
      which extend ParsableCommand.
      
      Example:
        public void robotInit(){
          super.robotInit();
          ParsableCommand[] commands = {new ExampleCommand()};
          enterCommandList(commands);
          --other code
        }
      
  Setting up commands
    Step 1: Create a command class and set it up however you want the command to behave, extend ParsableCommand instead of Command
    Step 2: If you dont already have one, add a constructor which takes no arguments
    Step 3: Return a single letter String in getIdentifier()
    Step 4: Return a String name for the command in getName()
    Step 5: In setArguments(), assign any class variables necessary for the command to run. Use the parse the variables from
      the String Array argument. Remember the order that you assume they will be in.
    
  Putting it all together to make a command
    Now that all the setup is done, making an autonomous sequence is easy.
      Step 1. Open the SmartDashboard and download the code to your robot.
      Step 2. In look at the RobotPreferences widget in the SmartDashboard. There should be a preference called "AutonomousCommand"
        This is where you will input your autonomous sequence.
      Make a sequence of commands following the following pattern. Use the letters you returned in your getIdentifier() methods to reference certain commands.
       
                    First Command         Second command        ...
        Pattern: Letter arg,arg,arg; Letter arg, arg, arg; Letter args...
        Example: D 5,1,1; R 1; S true, 5; K 4.2, 4;
        
        For each command, ensure that the command will handle the arguments correctly in setArguments()
        
  Customize the parser(Optional)
    If you want, you can change the class variables in CommandParser _argDelim and _commandDelim to change the string you input.
  
  If, after this readme, you are confused about how this works, look at the rest of the parsable_robot project, there are some examples.
  If, after that, you are confused, e-mail me at andrew@stelterfamily.net
    
  
  
