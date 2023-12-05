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
        CountryMap = new HashMap<String, HashMap<String, Integer>>();
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
                        String[] line = data[0].split(" ");
                        for (int i = 0; i < line.length -2; i++)
                        {
                            CountryKey += line[i];
                            CountryKey+=" ";
                        }
                        if (!CountryMap.containsKey(Name))
                        {
                            HashMap<String, Integer> goal = new HashMap<>();
                            goal.put(CountryKey, 0);
                            CountryMap.put(Name, goal);
                        }
                        else
                        {
                           CountryMap.get(Name).put(CountryKey, 0);
                        }
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
                            if(!CountryMap.containsKey(Name))
                            {
                                HashMap<String, Integer> goal = new HashMap<>();
                                goal.put(value, 0);
                                CountryMap.put(Name, goal);
                            }
                            else
                            {
                                CountryMap.get(Name).put(value, 0);
                            }
                        }
                    }
                }
            }
            System.out.println(CountryMap);
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
                //get the ID of the origin country
                String countID = Line[1];
                //get the full name of the origin country
                if (nameIDMapReverse.get(countID) != null)
                {
                    String CountName = nameIDMapReverse.get(countID);
                    //get the hashmap of the bordering countries
                    CountName = edgeCase(CountName);
                    HashMap<String, Integer> borderMap = CountryMap.get(CountName);
                    if (borderMap!= null)
                    {
                        for (String borderNames : borderMap.keySet())
                        {
                            borderNames = edgeCase(borderNames);
                            if (nameIDMap.get(borderNames) != null)
                            {
                                String borderID = nameIDMap.get(edgeCase(borderNames));
                                if (borderID.equals(Line[3]))
                                {
                                    Integer dist = Integer.parseInt(Line[4]);
                                    borderMap.put(borderNames,dist);
                                    CountryMap.put(CountName, borderMap);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(CountryMap);
        }
        catch (FileNotFoundException fe)
        {
            System.out.println(fe.getMessage());
        }
    }
    String edgeCase(String CountryName)
    {
        if(CountryName.equals("United States of America"))
        {
            return "United States";
        }
        else if(CountryName.equals("Burkina Faso(Upper Volta)"))
        {
            return "Burkina Faso";
        }
        else if(CountryName.equals("Cambodia (Kampuchea"))
        {
            return "Cambodia";
        }
        else if (CountryName.equals("Congo, Democratic Republic of (Zaire"))
        {
            return "Congo, Republic of the";
        }
        else if (CountryName.equals("Czech Republic"))
        {
            return "Czechia";
        }
        else if (CountryName.equals("Gambia"))
        {
            return "Gambia, The";
        }
        else if (CountryName.equals("German Federal Republic"))
        {
            return "Germany";
        }
        else if (CountryName.equals("Iran (Persia"))
        {
            return "Iran";
        }
        else if (CountryName.equals("Italy/Sardinia"))
        {
            return "Italy";
        }
        else if (CountryName.equals("Korea, People's Republic Of"))//ask about North and South Korea
        {
            return "Korea, North";
        }
        else if (CountryName.equals("Korea, Republic of"))
        {
            return "Korea, South";
        }
        else if (CountryName.equals("Kyrgyz Republic"))
        {
            return "Kyrgyzstan";
        }
        else if (CountryName.equals("Russia (Soviet Union)"))
        {
            return "Russia";
        }
        else if (CountryName.equals("Surinam"))
        {
            return "Suriname";
        }
        else if (CountryName.equals("Tanzania/Tanganyika"))
        {
            return "Tanzania";
        }
        else if(CountryName.equals("Turkey (Ottoman Empire)"))
        {
            return "Turkey";
        }
        else if (CountryName.equals("Vietnam, Democratic Republic of"))
        {
            return "Vietnam";
        }
        else if (CountryName.equals("Yemen (Arab Republic of Yemen"))
        {
            return "Yemen";
        }
        else if (CountryName.equals("Zimbabwe (Rhodesia)"))
        {
            return "Zimbabwe";
        }
        return CountryName;
    }
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
        Graph g = new Graph();
        g.readBordersFile();
        g.readStates_NamesFile();;
        g.readCapDist();
        a3.acceptUserInput();
    }

}