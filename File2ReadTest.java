import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
class File2ReadTest
{
    public static void main (String[] args)
    {
        HashMap<String,String> nameIDMap = new HashMap<>();
        try
        {
            Scanner scan = new Scanner(new File("state_name.tsv"));
            String line1 = scan.nextLine();
            while(scan.hasNextLine())
            {
                String[] nameLine = scan.nextLine().split("\t");
                if (nameLine[4].equals("2020-12-31"))
                {
                    nameIDMap.put(nameLine[1].trim(), nameLine[2]);
                }
            }
            System.out.println(nameIDMap);

        }
        catch (FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
}