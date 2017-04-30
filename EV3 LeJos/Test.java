import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Test
{
  public static void main(String[] args)
  {
    try
    {
    	//starting a server in socket 3000
      ServerSocket server = new ServerSocket(3000);
      System.out.println("Started");
      
      //accepts incoming socket connections
      Socket s = server.accept();
      
      
      //Instantiate the motors in the port A and C of ev3
      final RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
      RegulatedMotor m2 = new EV3LargeRegulatedMotor(MotorPort.C);
      
      //reads the incoming data from socket connection
      BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
      
      //variable initialization
      String data = null;
      int time = 0;
      String direction = null;

      
      //keep the program alive while there is a socket connection
      while ((data = br.readLine()) != null)
      {
    	
    	//extract the direction and time from the incoming data
        try
        {
          direction = data.substring(0, 1);// the character that defines the direction of movement
          time = Integer.parseInt(data.substring(1)); //the time of movement
        }
        catch (Exception localException) {}
        if ((direction.equals("F"))) //checks for the password and the direction
        {
          m.forward(); //motor 1 move forward
          m2.forward();// motor 2 move forward
          Delay.msDelay(time); //pause the program for the specidied time
          new Thread()
          {
        	//thread is required to stop both of the motors at the same time
            public void run()
            {
              m.stop(); //motor 1 stop
            }
          }.start();
          m2.stop(); //motor 2 stop
        }
        else if ((direction.equals("B"))) //checks for the password and the direction
        {
          m.backward(); //motor 1 move backward
          m2.backward(); // motor 2 move backward
          Delay.msDelay(time); 
          new Thread()
          {
            public void run()
            {
              m.stop();
            }
          }.start();
          m2.stop();
        }
        else if ((direction.equals("R"))) //checks for the password and the direction
        {
          // these opposite movement will cause the ev3 to make a circular movement to the right	
          m.forward(); //motor 1 move forward
          m2.backward(); //motor 2 move backward 
          
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              m.stop();
            }
          }.start();
          m2.stop();
        }
        else if ((direction.equals("L"))) //checks for the password and the direction
        {
         // circular movement to the left
          m.backward(); //motor 1 move backward
          m2.forward(); //motor 2 move forward
          
          Delay.msDelay(time);
          new Thread()
          {
            public void run()
            {
              m.stop();
            }
          }.start();
          m2.stop();
        }
        else
        {
          System.out.println("Failed!"); //if the required information is not present, this will be printed
        }
        System.out.println(data); //print out the incoming data
      }
      System.out.println("Ended");
      Delay.msDelay(4000); //delay for 4 seconds before stopping the program
    }
    catch (Exception localException1) {}
  }
}
