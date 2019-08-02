import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;
public class Chatbot
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
            if (comm.contains(array[i]))
            {
                return true;
            }
        }
        return false;
    }
    
    private static boolean Check(String comm, String[] comms)
    {
        if (comm.equals("commands"))
        {
            print("\nCommand keywords (can be paired with other words to form a sentence): ");
            print("search");
            print("wiki");
            for (String s : comms)
            {
                print(s);
            }
            print("");
            return true;
        }
        else if (comm.contains("search") || comm.contains("wiki"))
        {
            return true;
        }
        else if (inArray(comm,comms))
        {
            //check if command (comm) is in the array of commands
            return true;
        }
        else
        {
            //print("Command not recognized");
            return false;
        }
    }
    
    private static String Wiki(String comm) throws IOException
    {
        //This will use a web crawler API to parse through the HTML of a wiki link and spit out the text of the first (summary) paragraph
        String[] commArr = comm.split(" ");
        String search = "";
        
        for (int i = commArr.length-2; i >= 0; i--)
        {
            if (commArr[i].equals("for") || commArr[i].equals("search") || commArr[i].equals("wiki"))
            {
                for (int j = i+1; j < commArr.length; j++)
                {
                    search = search.concat(commArr[j]+"_");
                }
                break;
            }
        }
        if (search.endsWith("_"))
        {
            search = search.substring(0, search.length()-1);
        }
        Elements paragraph;
        try
        {
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/"+search).timeout(10000).get();
            paragraph = doc.select("div.mw-parser-output p:nth-of-type(3)");
        }
        catch (HttpStatusException h)
        {
            return "Wikipeida page does not exist";
        }
        //Element step;
        String summary = paragraph.text();
        int var = summary.length()/6;
        String sub1 = (summary.substring(0, var+1)+"\n");
        String sub2 = (summary.substring(var+1, (var*2)+1)+"\n");
        String sub3 = (summary.substring((var*2)+1, (var*3)+1)+"\n");
        String sub4 = (summary.substring((var*3)+1, (var*4)+1)+"\n");
        String sub5 = (summary.substring((var*4)+1, (var*5)+1)+"\n");
        String sub6 = (summary.substring((var*5)+1,(var*6)+1)+"\n");
        /*for (int i=0; i<3; i++)
        {
            space+=20;
        }*/
        return (sub1.concat(sub2.concat(sub3.concat(sub4.concat(sub5.concat(sub6))))));
    }
    
    private static void Chat(String comm, String[] comms, String[][] responses) throws IOException
    {
        Random r = new Random();
        for (int i = 0; i < comms.length; i++)
        {
           if (comm.contains("search") || comm.contains("wiki"))
           {
               print(Wiki(comm));
               break;
           }
           else if (comm.contains(comms[i]))
           {
              int num = r.nextInt(responses[i].length);
              print(responses[i][num]);
              break;
           }
        }  
    }
    
    public static void main(String[] args) throws IOException
    {
        String[] comms = {"how are you","your name","joke","hello","hi","hey"};
        String[][] responses = {{"I'm doing good","Pretty bad", "Life sucks", "I'm great today", "lmao kys libtard"},{"My name is Chatou, your friendly neighborhood Chatbot"}, 
        {"Your life","I’m a big fan of whiteboards. I find them quite re-markable.", "I was going to make myself a belt made out of watches, but then I realized it would be a waist of time.","I asked my French friend if she likes to play video games. She said, “Wii.\"",
        "What do you call a pig that does karate? Pork chop"},{"Hey, how's it going"},{"Hello!"}, {"What's up"}};
        String[] byes = {"Bye!", "Later, alligator", "See you in a while, crocodile", "See you soon", "I hope I never see you again", "Ok time to play Fortnite: Battle Royale",
        "Ok time to play Minecraft: Java Edition", "Hasta Luego"};
        
        printsl("\f");
        Scanner kb = new Scanner(System.in);
        print("Greetings, earthling! I am a semi-intelligent chatbot.");
        print("Type \"commands\" to see which commands you can use!");
        print("TRY OUT THE \"search\" COMMAND TO LOOK UP ON WIKIPEDIA!!");
        print("Input a command: ");
        
        bot: while (true)
        {
            String command = kb.nextLine();
            command = command.toLowerCase();
            if (command.contains("bye") || command.contains("later") || command.contains("cya") || command.contains("see you"))
            {
                Random r = new Random();
                print(byes[r.nextInt(byes.length)]);
                break;
            }
            else
            {
                //Checks to see if the command typed is in the array of available commands
                while (!Check(command,comms) || (command.contains("bye") || command.contains("later") || command.contains("cya") || command.contains("see you")))
                {
                    if (command.contains("bye") || command.contains("later") || command.contains("cya") || command.contains("see you")) 
                    {
                        Random r = new Random();
                        print(byes[r.nextInt(byes.length)]);
                        break bot;
                    }
                    //print("Command: ");
                    command = kb.nextLine();
                }
                
                //Gives a response to the command
                Chat(command,comms,responses);
            }
        }
        
    }
}