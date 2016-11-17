package exceptionhandling;

//DivideByZeroException.java
//Definition of class DivideByZeroException.
//Used to throw an exception when a
//divide-by-zero is attempted.
class DivideByZeroException
        extends ArithmeticException {
public DivideByZeroException()
{
 super( "Attempted to divide by zero" );
}

public DivideByZeroException( String message )
{
 super( message );
}
}
