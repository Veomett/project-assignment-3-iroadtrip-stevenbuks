import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


class Graph<T>
{
    HashMap<String,String> nameIDMap;
    HashMap<String, String> nameIDMapReverse;
    HashMap<String, List> Countries;
    HashMap<String, HashMap<String, Integer>> CountryMap;

    Graph()
    {
        CountryMap = new HashMap<String,  HashMap<String, Integer>>();
    }
    public void readStates_NamesFile()
    {
        try
        {
            nameIDMap = new HashMap<>();
            nameIDMapReverse = new HashMap<>();
            Scanner scan = new Scanner(new File("state_name.tsv"));
            String line1 = scan.nextLine();
            while(scan.hasNextLine())
            {
                String[] nameLine = scan.nextLine().split("\t");
                if (nameLine[4].equals("2020-12-31"))
                {
                    nameIDMap.put(nameLine[2].trim(), nameLine[1]);
                    nameIDMapReverse.put(nameLine[1].trim(), nameLine[2]);
                }
            }
            System.out.println(nameIDMap);
        }
        catch (FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
    public void readBordersFile()
    {
        Countries = new HashMap<>();
        try{

            Scanner scan = new Scanner(new File("borders.txt"));
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
                    CountryMap.put(Name, new HashMap<>());
                }
                else
                {
                    data = Info.split(";");
                    String CountryKey ="";
                    if (data.length == 1)
                    {
                        for (int i = 0; i < data.length -2; i++)
                        {
                            CountryKey += data[i];
                            CountryKey+=" ";
                        }
                        valueList.add(CountryKey);
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
    public void readCapDist()
    {
        try
        {
            Scanner scan = new Scanner(new File("capdist.csv"));
            String Line1 = scan.nextLine();
            while(scan.hasNextLine())
            {
                String[] Line = scan.nextLine().split(",");
                String countID = Line[1];
                String CountName = nameIDMap.get(countID);
                List<String> countList = Countries.get(CountName);
                for (int i = 0; i < countList.size(); i++)
                {
                    //get the ID from the Country name
                    String countryID = nameIDMapReverse.get(countList.get(i));
                }
            }
        }
        catch (FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
    void addEdge(String country1, String country2, int distance)
    {}

}
public class IRoadTrip {


    public IRoadTrip (String [] args) {
        // Replace with your code
    }

    public int getDistance (String country1, String country2) {
        // Replace with your code
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
    }


    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);

        a3.acceptUserInput();
    }

}

