/*
 *Copyright Mark Mansi and Zackary Jordan 2011 - AND BEYOND -
 *
 *I lost the game
 *
 *Please do not change or remove this copyright, unless you change the majority of the code.
 *Please do not try to come up with really cheap excuses for changing/removing the copyright.
 *
 *Here is a story with a moral:
 *
 *Once upon a time, Jimmy changed/removed Mark's copyright statement! Mark was very upset because this was
 *not a very nice thing to do.
 *
 *THE END
 *
 *Moral: Please abide by the terms above.
 *
 */
package getinput;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class GetInput {

    public static int getInt() {

        Scanner myInp = new Scanner(System.in);
        int myInt = 0;

        try {

            myInt = myInp.nextInt();

        } catch (NoSuchElementException ex) {
            System.out.print("Error! Enter a valid integer: ");
            myInt = getInt();
        } catch (ClassCastException ex) {
            System.out.print("Error! Enter a valid integer: ");
            myInt = getInt();
        } catch (IllegalStateException ex) {
            System.out.print("Error! Enter a valid integer: ");
            myInt = getInt();
        }

        return myInt;

    }

    public static int getInt(String errorText) {

        Scanner myInp = new Scanner(System.in);
        int myInt = 0;

        try {

            myInt = myInp.nextInt();

        } catch (NoSuchElementException ex) {
            System.out.print(errorText);
            myInt = getInt(errorText);
        } catch (ClassCastException ex) {
            System.out.print(errorText);
            myInt = getInt(errorText);
        } catch (IllegalStateException ex) {
            System.out.print(errorText);
            myInt = getInt(errorText);
        }

        return myInt;

    }

    public static String getString() {

        Scanner myInp = new Scanner(System.in);
        String myStr = "";

        try {

            myStr = myInp.nextLine();

        } catch (NoSuchElementException ex) {
            System.out.print("Error! Enter a valid string: ");
            myStr = getString();
        } catch (IllegalStateException ex) {
            System.out.print("Error! Enter a valid string: ");
            myStr = getString();
        }

        return myStr;

    }

    public static String getString(String errorText) {

        Scanner myInp = new Scanner(System.in);
        String myStr = "";

        try {

            myStr = myInp.nextLine();

        } catch (NoSuchElementException ex) {
            System.out.print(errorText);
            myStr = getString(errorText);
        } catch (IllegalStateException ex) {
            System.out.print(errorText);
            myStr = getString(errorText);
        }

        return myStr;

    }

    public static double getDouble() {

        Scanner myInp = new Scanner(System.in);
        double myDouble = 0.0;

        try {

            myDouble = myInp.nextDouble();

        } catch (NoSuchElementException ex) {
            System.out.print("Error! Enter a valid double: ");
            myDouble = getDouble();
        } catch (ClassCastException ex) {
            System.out.print("Error! Enter a valid double: ");
            myDouble = getDouble();
        } catch (IllegalStateException ex) {
            System.out.print("Error! Enter a valid double: ");
            myDouble = getDouble();
        }

        return myDouble;

    }

    public static double getDouble(String errorText) {

        Scanner myInp = new Scanner(System.in);
        double myDouble = 0.0;

        try {

            myDouble = myInp.nextDouble();

        } catch (NoSuchElementException ex) {
            System.out.print(errorText);
            myDouble = getDouble(errorText);
        } catch (ClassCastException ex) {
            System.out.print(errorText);
            myDouble = getDouble(errorText);
        } catch (IllegalStateException ex) {
            System.out.print(errorText);
            myDouble = getDouble(errorText);
        }

        return myDouble;

    }

    public static boolean getBool() {

        Scanner myInp = new Scanner(System.in);
        boolean myBool = false;

        try {

            myBool = myInp.nextBoolean();

        } catch (NoSuchElementException ex) {
            System.out.print("Error! Enter a valid boolean: ");
            myBool = getBool();
        } catch (ClassCastException ex) {
            System.out.print("Error! Enter a valid boolean: ");
            myBool = getBool();
        } catch (IllegalStateException ex) {
            System.out.print("Error! Enter a valid boolean: ");
            myBool = getBool();
        }

        return myBool;

    }

    public static boolean getBool(String errorText) {

        Scanner myInp = new Scanner(System.in);
        boolean myBool = false;

        try {

            myBool = myInp.nextBoolean();

        } catch (NoSuchElementException ex) {
            System.out.print(errorText);
            myBool = getBool(errorText);
        } catch (ClassCastException ex) {
            System.out.print(errorText);
            myBool = getBool(errorText);
        } catch (IllegalStateException ex) {
            System.out.print(errorText);
            myBool = getBool(errorText);
        }

        return myBool;

    }

    public static char getChar() {

        char myChar = 'a';
        String inp = getString();

        if (inp.length() != 1) {

            System.out.print("Error! Enter a valid character: ");
            myChar = getChar();

        } else {
            myChar = inp.charAt(0);
        }

        return myChar;

    }

    public static char getChar(String errorText) {

        char myChar = 'a';
        String inp = getString(errorText);

        if (inp.length() != 1) {

            System.out.print(errorText);
            myChar = getChar(errorText);

        } else {
            myChar = inp.charAt(0);
        }

        return myChar;

    }
}
