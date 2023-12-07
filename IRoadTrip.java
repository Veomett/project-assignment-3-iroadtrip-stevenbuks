import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class IRoadTrip {
    Graph g = new Graph();
 private class Graph
{
    HashMap<String,String> nameIDMap;
    HashMap<String, String> nameIDMapReverse;

    //HashMap<String, List> Countries;
    //whenever adding something to map call edgeCase() on it
    HashMap<String, HashMap<String, Integer>> CountryMap;//<United States, <Canada, 0>>

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
                    nameIDMap.put(edgeCase(nameLine[2].trim()), edgeCase(nameLine[1]));
                    nameIDMapReverse.put(edgeCase(nameLine[1].trim()), edgeCase(nameLine[2]));
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
        //Countries = new HashMap<>();
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
                    CountryMap.put(edgeCase(Name), new HashMap<>());
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
                            goal.put(edgeCase(CountryKey), Integer.MAX_VALUE);
                            CountryMap.put(edgeCase(Name), goal);
                        }
                        else
                        {
                           CountryMap.get(edgeCase(Name)).put(edgeCase(CountryKey), Integer.MAX_VALUE);
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
                                goal.put(edgeCase(value), Integer.MAX_VALUE);
                                CountryMap.put(edgeCase(Name), goal);
                            }
                            else
                            {
                                CountryMap.get(edgeCase(Name)).put(edgeCase(value), Integer.MAX_VALUE);
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
                if (nameIDMapReverse.get(edgeCase(countID)) != null)
                {
                    String CountName = nameIDMapReverse.get(edgeCase(countID));
                    //get the hashmap of the bordering countries
                    System.out.println(CountName);
                    CountName = edgeCase(CountName);
                    HashMap<String, Integer> borderMap = CountryMap.get(edgeCase(CountName));
                    if (borderMap!= null)
                    {
                        //<Strings origin, <String dest, Int distnace>
                        for (String borderNames : borderMap.keySet())
                        {
                            System.out.println(borderNames);
                            if (nameIDMap.get(edgeCase(borderNames)) != null)
                            {
                                String borderID = nameIDMap.get(edgeCase(borderNames));
                                System.out.println(borderID);
                                if (borderID.equals(Line[3]))
                                {
                                    System.out.println("here");
                                    System.out.println(CountName);
                                    Integer dist = Integer.parseInt(Line[4]);
                                    borderMap.put(edgeCase(borderNames),dist);
                                    CountryMap.put(edgeCase(CountName), borderMap);
                                    System.out.println(CountryMap.get(edgeCase(CountName)));
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
        //only keep the ones that convert state_names to borders.txt
        CountryName = CountryName.trim();
        if(CountryName.equals("United States of America"))
        {
            return "United States";
        }
        else if (CountryName.equals("Morocco (Ceuta)"))
        {
            return "Morocco";
        }
        else if (CountryName.equals("Spain (Ceuta"))
        {
            return "Spain";
        }
        else if (CountryName.equals("Vietnam, Democratic Republic of"))
        {
            return "Vietnam";
        }
        else if (CountryName.equals("Burkina Faso (Upper Volta)"))
        {
            return "Burkina Faso";
        }
        else if(CountryName.equals("Cambodia (Kampuchea)"))
        {
            return "Cambodia";
        }
        else if (CountryName.equals("Cote D’Ivoire"))
        {
            return "Cote d'Ivoire";
        }
        else if (CountryName.equals("Myanmar (Burma)"))
        {
            return "Burma";
        }
        else if (CountryName.equals("Congo, Democratic Republic of (Zaire)"))
        {
            return "Congo, Democratic Republic of the";
        }
        else if(CountryName.equals("Democratic Republic of the Congo"))
        {
            return "Congo, Democratic Republic of the";
        }
        else if (CountryName.equals("Republic of the Congo"))
        {
            return "Congo, Republic of the";
        }
        else if (CountryName.equals("UKG"))
        {
            return "UK";
       }
        else if (CountryName.equals("UK"))
        {
            return "United Kingdom";
        }
        else if (CountryName.equals("Czech Republic"))
        {
            return "Czechia";
        }
        else if (CountryName.equals("The Gambia"))
        {
            return "Gambia, The";
        }
        else if (CountryName.equals("Gambia"))
        {
            return "Gambia, The";
        }
        else if (CountryName.equals("German Federal Republic"))
        {
            return "Germany";
        }
        else if (CountryName.equals("Iran (Persia)"))
        {
            return "Iran";
        }
        else if (CountryName.equals("Italy/Sardinia"))
        {
            return "Italy";
        }
        else if (CountryName.equals("Belarus (Byelorussia)"))
        {
            return "Belarus";
        }
        else if (CountryName.equals("North Korea"))
        {
            return "Korea, North";
        }
        else if (CountryName.equals("Korea, People's Republic Of"))//ask about North and South Korea
        {
            return "Korea, North";
        }
        else if (CountryName.equals("South Korea"))
        {
            return "Korea, South";
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
        else if (CountryName.equals("Turkey"))
        {
            return "Turkey (Turkiye)";
        }
        else if(CountryName.equals("Turkey (Ottoman Empire)"))
        {
            return "Turkey (Turkiye)";
        }
        else if (CountryName.equals("Vietnam, Democratic Republic of"))
        {
            return "Vietnam";
        }
        else if (CountryName.equals("Yemen (Arab Republic of Yemen)"))
        {
            return "Yemen";
        }
        else if (CountryName.equals("Zimbabwe (Rhodesia)"))
        {
            return "Zimbabwe";
        }
        else if (CountryName.equals("Macedonia (Former Yugoslav Republic of)"))
        {
            return "North Macedonia";
        }
        else if (CountryName.equals("Russia (Kaliningrad Oblast"))
        {
            return "Russia";
        }
        else if (CountryName.equals("Poland (Kaliningrad Oblast)"))
        {
            return "Poland";
        }
        else if (CountryName.equals("Lithuania (Kaliningrad Oblast)"))
        {
            return "Lithuania";
        }
        return CountryName;
    }
}


    public IRoadTrip (String [] args)
    {
        g.readBordersFile();
        g.readStates_NamesFile();;
        g.readCapDist();
    }

    public int getDistance (String country1, String country2) {
        HashMap<String, Integer> borderMap = new HashMap<>();
        try
        {
            return g.CountryMap.get(country1).get(country2);
        }
        catch (Exception e)
        {
            return -1;
        }



    }


    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        HashMap <String,Integer> heapMap = new HashMap<>();
        PriorityQueue<HashMap <String, Integer>>heap = new PriorityQueue();
        for (String destination : g.CountryMap.keySet())
        {
            if (destination.equals(country1))
            {
                heapMap.put(country1, 0);
            }
            else
            {
                heapMap.put(destination, Integer.MAX_VALUE);
            }
        }
        while (!heap.isEmpty())
        {
           HashMap<String, Integer> heapEntry =  heap.poll();
           for (String origin : heapEntry.keySet())
           {
               for (String destination: g.CountryMap.get(origin).keySet())
               {
                   int distance = getDistance(origin, destination);
                   heapMap.put(destination, distance);
                   heap.offer(heapMap);
               }
           }
        }
        return null;
    }
    String InputEdgeCase(String Country)
    {
        if(g.CountryMap.containsKey(Country))
        {
            return Country;
        }
        return "";
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