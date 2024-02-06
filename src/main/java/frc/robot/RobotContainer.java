// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.cmd_ChasisManual;
import frc.robot.commands.cmd_MoveForward;
import frc.robot.commands.cmd_vision;
import frc.robot.subsystems.sub_Chasis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  public final sub_Chasis Chasis = new sub_Chasis();
  public final CommandXboxController Joy1 = new CommandXboxController(0);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    Chasis.setDefaultCommand(new cmd_ChasisManual(Chasis, () -> Joy1.getRightTriggerAxis() , () -> Joy1.getLeftTriggerAxis(), () -> Joy1.getLeftX(), () -> Joy1.b().getAsBoolean()));
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    Joy1.a().whileTrue(new cmd_MoveForward(45, Chasis));
    Joy1.b().whileTrue(new cmd_MoveForward(45, Chasis));
    Joy1.x().whileTrue(new cmd_MoveForward(100, Chasis));
    Joy1.y().whileTrue(new cmd_MoveForward(45, Chasis));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
   return new cmd_vision(Chasis);
  }
}
