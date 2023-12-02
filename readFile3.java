import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
class readFile3
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scan = new Scanner(new File("capdist.csv"));
            String Line1 = scan.nextLine();
            while(scan.hasNextLine())
            {
                String[] Line = scan.nextLine().split(",");
                String countID = Line[1];
            }
        }
        catch (FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
}