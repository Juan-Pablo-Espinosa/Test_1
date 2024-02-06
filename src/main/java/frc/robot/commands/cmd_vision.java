// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.sub_Chasis;
import java.util.function.Supplier;

public class cmd_vision extends Command {
  /** Creates a new cmd_vision. */
  private final sub_Chasis chasis;
  private double TX, TA, TID, turn, LSpeed, RSpeed, speed, quickOP, pond = 0.15;
  private Supplier<Double> ID;
  public cmd_vision(sub_Chasis CHASIS, Supplier<Double> id) {
    chasis = CHASIS;
    this.ID = id;
    addRequirements(chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    chasis.setOpenLoopS(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    quickOP = TID - ID;
    if (quickOP == 0){
      updateValues();
      if (Math.abs(TX) < 1.5){
        turn = 0;
     }else{
        turn = (TX / 25) * pond;
     }

     if (Math.abs(TA) < 3 && Math.abs(TA) != 0){
       speed = 0.2;
     }else{
        speed = 0;
      }

      if (turn > 0.8){
        turn = 0.8;
      }

      if (speed > 0.8){
        speed = 0.8;
      }

     RSpeed = -speed - turn;
      LSpeed = -speed + turn;
      chasis.SetSpeed(RSpeed, LSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void updateValues(){
    TX = chasis.getTx();
    TA = chasis.getTa();
    TID = chasis.getTid();
  }
}
