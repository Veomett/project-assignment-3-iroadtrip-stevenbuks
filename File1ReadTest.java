import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
class File1ReadTest
{
    public static void main (String[] args)
    {
        File myFile;
        HashMap<String, List> Countries = new HashMap<>();
        try{

            Scanner scan = new Scanner(myFile = new File("borders.txt"));
            while(scan.hasNextLine())
            {
                String[] data;
                List<String> valueList = new ArrayList<>();
                String entry = scan.nextLine();
               // System.out.println(entry);
                String[] countryInfo = entry.split("=");
                String Name = countryInfo[0];
                Name = Name.trim();
               // System.out.println(countryInfo);
                String Info = countryInfo[1];
                Info = Info.trim();
                if (Info == null)
                {
                    Countries.put(Name, null);
                }
                else
                {
                    if (Name .equals("Papua New Guinea"))
                    {
                        System.out.println("Name");
                    }
                    data = Info.split(";");
                    String CountryKey ="";
                    if (data.length == 1)
                    {
                        String[] line = data[0].split(" ");
                        for (int i = 0; i < line.length -2; i++)
                        {
                            CountryKey += line[i];
                            CountryKey+=" ";
                        }
                        valueList.add(CountryKey.trim());
                        Countries.put(Name, valueList);
                    }
                    else if (data.length > 1)
                    {
                        for (int i =0; i < data.length; i++)
                        {
                            String[] justCountry = data[i].trim().split(" ");
                            String value = "";
                            for (int j = 0; j < justCountry.length -2; j++)
                            {
                                value += justCountry[j];
                                value += " ";
                            }
                            value = value.trim();
                            valueList.add(value);
                            Countries.put(Name, valueList);
                        }
                    }
                }
            }
            System.out.println(Countries);
        }
        catch(FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
}