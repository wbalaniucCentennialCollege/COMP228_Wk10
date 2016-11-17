package exceptionhandling;

//DivideByZeroTest.java
//A simple exception handling example.
//Checking for a divide-by-zero-error.
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DivideByZeroTest extends JFrame
                           implements ActionListener {
private JTextField input1, input2, output;
private int number1, number2;
private double result;

// Initialization
public DivideByZeroTest()
{
   super( "Demonstrating Exceptions" );

   Container c = getContentPane();
   c.setLayout( new GridLayout( 3, 2 ) );

   c.add( new JLabel( "Enter numerator ",
                      SwingConstants.RIGHT ) );
   input1 = new JTextField( 10 );
   c.add( input1 );

   c.add(
      new JLabel( "Enter denominator and press Enter ",
                  SwingConstants.RIGHT ) );
   input2 = new JTextField( 10 );
   c.add( input2 );
   input2.addActionListener( this );

   c.add( new JLabel( "RESULT ", SwingConstants.RIGHT ) );
   output = new JTextField();
   c.add( output );

   setSize( 425, 100 );
   setVisible(true);
}

// Process GUI events
public void actionPerformed( ActionEvent e )
{
   DecimalFormat precision3 = new DecimalFormat( "0.000" );

   output.setText( "" ); // empty the output JTextField

   try {         
      number1 = Integer.parseInt( input1.getText() );
      number2 = Integer.parseInt( input2.getText() );

      result = quotient( number1, number2 );
      output.setText( precision3.format( result ) );
   }
   catch ( NumberFormatException nfe ) {
      JOptionPane.showMessageDialog( this,
         "You must enter two integers",
         "Invalid Number Format",
         JOptionPane.ERROR_MESSAGE );
   }
   catch ( DivideByZeroException dbze ) {
      JOptionPane.showMessageDialog( this, dbze.toString(),
         "Attempted to Divide by Zero from ilia",
         JOptionPane.ERROR_MESSAGE );
   }
}

// Definition of method quotient. Used to demonstrate
// throwing an exception when a divide-by-zero error
// is encountered.
public double quotient( int numerator, int denominator )
   throws DivideByZeroException
{
   if ( denominator == 0 )
      throw new DivideByZeroException();

   return ( double ) numerator / denominator;
}

public static void main( String args[] )
{
   DivideByZeroTest app = new DivideByZeroTest();

   app.addWindowListener(
      new WindowAdapter() {
         public void windowClosing( WindowEvent e )
         {
            e.getWindow().dispose();
            System.exit( 0 );
         }
      }
   );
}
}


