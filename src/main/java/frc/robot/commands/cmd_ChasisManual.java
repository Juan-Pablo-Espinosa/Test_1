// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.sub_Chasis;

public class cmd_ChasisManual extends Command {
  /** Creates a new cmd_ChasisManual. */
  private Supplier <Double> SpeedF, SpeedB, Turn;
  private double VelL, VelR, Vel, Giro;
  private Supplier <Boolean> Boton;
  private sub_Chasis CHASIS;
  private double pond = 0.5;

  public cmd_ChasisManual(sub_Chasis chasis, Supplier <Double> speedF, Supplier <Double> speedB, Supplier <Double> turn , Supplier <Boolean> boton) {
    this.CHASIS = chasis;
    this.SpeedF = speedF;
    this.SpeedB = speedB;
    this.Turn = turn;
    this.Boton = boton;
    addRequirements(CHASIS);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    VelL = 0.0;
    VelR = 0.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(SpeedF.get()) < 0.2 && Math.abs(SpeedB.get()) < 0.2){
      Vel = 0;
    }else{
      Vel = SpeedF.get() - SpeedB.get();
    }

    if (Math.abs(Turn.get()) < 0.2){
      Giro = 0;
    }else{
      Giro = Turn.get();
    }

    if (Boton.get()){
      Vel = Vel * pond;
      Giro = Giro * pond;
    }

    VelL = pond * (Vel + Giro);
    VelR = pond * (Vel - Giro);
    CHASIS.SetSpeed(VelR, VelL);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
