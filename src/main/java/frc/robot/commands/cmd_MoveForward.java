// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.sub_Chasis;
import edu.wpi.first.wpilibj.Timer;

public class cmd_MoveForward extends Command {
  /** Creates a new cmd_MoveForward. */
  private double Setpoint;
  private final sub_Chasis Chasis;
  private double ErrorP, KP, Speed;
  private double DT, ErrorI, KI;
  private double IZone, lastDT, ErrorD, lastError, KD;

  public cmd_MoveForward(double setpoint, sub_Chasis chasis) {
    this.Setpoint = setpoint;
    this.Chasis = chasis;
    addRequirements(Chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DT = 0;
    ErrorI = 0;
    KI = 0.6;
    lastDT = 0;
    IZone = 0.25 * Setpoint;
    lastError = 0;
    ErrorD = 0;
    KD = 0.009;

    Chasis.setOpenLoopS(0);
    Chasis.resetEncoder();
    Chasis.SetSpeed(0.0, 0.0);
    ErrorP = 0;
    KP = 0.09;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    DT = Timer.getFPGATimestamp() - lastDT;
    //P
    ErrorP = Setpoint - Chasis.getEncoder();
    //I
    
    if (IZone > ErrorP){
      ErrorI += ErrorP * DT;
    }

    //D
    ErrorD = (ErrorP - lastError) / DT;
    lastError = ErrorP;
    Speed = (ErrorP * KP) + (ErrorI * KI) + (ErrorD * KD);

    if (Speed > 0.9){
      Speed = 0.9;
    }

    if (Speed < -0.9){
      Speed = -0.9;
    }

    Speed = Speed * -1;
    Chasis.SetSpeed(Speed, Speed);
    lastDT = DT;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Chasis.resetEncoder();
    Chasis.SetSpeed(0.0, 0.0);
    Setpoint = 0.0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (ErrorP < 2.0){
      return true;
    }else{
      return false;
    }
  }
}