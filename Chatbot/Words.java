import java.util.Scanner;
import java.util.Random;
public class Words
{
    public static void print(String text)
    {
        System.out.println(text);
    }
    public static void printsl(String text)
    {
        System.out.print(text);
    }
    
    private static boolean inArray(String comm, String[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (comm.equals(array[i]))
            {
                return true;
            }
        }
        return false;
    }
    
    private static boolean Check(String comm, String[] comms)
    {
        //create array of commands
        if (inArray(comm,comms))
        {
            //check if command (comm) is in the array of commands
            print("It is a command");
            return true;
        }
        else
        {
            print("Command not recognized");
            return false;
        }
    }
    
    public static void main(String[] args)
    {
        String[] comms = new String[]{"d","a","s","f"};
        printsl("\f");
        Scanner kb = new Scanner(System.in);
        print("My name is Sam and I am a semi-intelligent chatbot.");
        print("Checkout the README.txt file to see which commands you can use.");
        print("Input a command: ");
        /*if (command.contains("good") || command.contains("ok") || command.contains("fine"))
        {
            print("splendid");
        }
        else if (command.contains("bad") || command.contains("terrible") || command.contains("kys"))
        {
            print("Your opinion is wrong");
        }
        else
        {
            print("Sorry I didn't catch that.");
        }*/
        
        while (true)
        {
            String command = kb.nextLine();
            command = command.toLowerCase();
            if (command.contains("bye") || command.contains("later") || command.contains("cya") || command.contains("see you"))
            {
                //later change this to randomly choose the response
                print("Hasta Luego");
                break;
            }
            else
            {
                while (!Check(command,comms))
                {
                    print("Command: ");
                    command = kb.nextLine();
                }
            }
        }
        
    }
}