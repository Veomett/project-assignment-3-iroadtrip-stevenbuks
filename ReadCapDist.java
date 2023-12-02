import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
class ReadCapDist
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scan = new Scanner(new File("capdist.csv"));
        }
        catch(FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
}