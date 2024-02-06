// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

public class sub_Chasis extends SubsystemBase {
  /** Creates a new sub_Chasis. */
  private final CANSparkMax Motor1L = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax Motor2L = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax Motor1R = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax Motor2R = new CANSparkMax(4, MotorType.kBrushless);
  private final RelativeEncoder Encod = Motor1L.getEncoder();

  public sub_Chasis() {
    Motor1L.restoreFactoryDefaults();
    Motor2L.restoreFactoryDefaults();
    Motor1R.restoreFactoryDefaults();
    Motor2R.restoreFactoryDefaults();

    Motor1L.setIdleMode(IdleMode.kBrake);
    Motor2L.setIdleMode(IdleMode.kBrake);
    Motor1R.setIdleMode(IdleMode.kBrake);
    Motor2R.setIdleMode(IdleMode.kBrake);
    
    Motor2L.follow(Motor1L);
    Motor2R.follow(Motor1R);
    Motor1R.setInverted(true);

    Motor1L.set(0);
    Motor1R.set(0);

    

    Encod.setPosition(0.0);
    Encod.setPositionConversionFactor(2.02);
  }

  @Override
  public void periodic() {
   SmartDashboard.putNumber("LeftSpeed", Motor1L.get());
   SmartDashboard.putNumber("RightSpeed", Motor1R.get());
   SmartDashboard.putNumber("Encoder", Encod.getPosition());
   SmartDashboard.putNumber("Encoder_TEXT", Encod.getPosition());
   SmartDashboard.putNumber("Vision TX", getTx());
   SmartDashboard.putNumber("Vision Ta", getTa());
   SmartDashboard.putNumber("Vision Ty", getTy());
  }

  public void SetSpeed(double R, double L){
    if (Math.abs(R) > 0.6){
      R = (R / Math.abs(R)) * 0.6;
    }

    if (Math.abs(L) > 0.6){
      L = (L / Math.abs(L)) * 0.6;
    }

    Motor1L.set(L);
    Motor1R.set(R);
  }

  public double getEncoder(){
    return Encod.getPosition();
  }

  public void resetEncoder(){
    Encod.setPosition(0.0);
  }

  public void setOpenLoopS(double S){
    Motor1L.setOpenLoopRampRate(S);
    Motor2L.setOpenLoopRampRate(S);
    Motor1R.setOpenLoopRampRate(S);
    Motor2R.setOpenLoopRampRate(S);
  }

  public double getTx(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("tx").getDouble(0);
  }

  public double getTy(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ty").getDouble(0);
  }

  public double getTa(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ta").getDouble(0);
  }

  public double getTid(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("tid").getDouble(0);
  }
}
