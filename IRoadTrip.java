//Author: Steven Buks Class: CS245
//this code generates a map of countires and creates a shortest route from one country to another by generating a route made of a list of countries
//import statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//IRoadTrip is the main class where we make the route from one country to another
//Graph is the subclass where our map is made by reading the three files (cap_dist.csv, borders.txt, state_name.tsv)
public class IRoadTrip
{
    //here we instantiate a graph object so we can call its methods and variables outside the graph sub class
    Graph g = new Graph();
    String borders;
    String capDist;
    String stateName;
    class Graph
    {
        //this map converts from full name of country to its acronym
        HashMap<String,String> nameIDMap;
        //this map converts from acronym to full name of country
        HashMap<String, String> nameIDMapReverse;
        //This is the main map that stores countries and what countries they border as well as the distances between a country and all the countries that border it
        HashMap<String, HashMap<String, Integer>> CountryMap;
        Graph()
        {
            CountryMap = new HashMap<String, HashMap<String, Integer>>();
        }
        //this method reads "state_names.tsv"
        public void readStates_NamesFile(String fileName)
        {
            try
            {
                nameIDMap = new HashMap<>();
                nameIDMapReverse = new HashMap<>();
                //scanner is used to read file
                Scanner scan = new Scanner(new File(fileName));
                //skips over first line
                String line1 = scan.nextLine();
                //loop while there are lines in the file
                while(scan.hasNextLine())
                {
                    //split each lines based on tabs
                    String[] nameLine = scan.nextLine().split("\t");
                    //if the country is up to date
                    if (nameLine[4].equals("2020-12-31"))
                    {
                        //add the names and acronyms into the appropriate part of the Hashmap
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
        //reads borders.txt
        public void readBordersFile(String fileName)
        {
            //Countries = new HashMap<>();
            try
            {
                //scanner used to read file
                Scanner scan = new Scanner(new File(fileName));
               //while a line exists in the file
                while(scan.hasNextLine())
                {
                    String[] data;
                    //gets current line
                    String entry = scan.nextLine();
                    //split the line based on"="
                    String[] countryInfo = entry.split("=");
                    //get the name of the origin country and trim it
                    String Name = countryInfo[0];
                    Name = Name.trim();
                    //info is everything after the equals sign
                    String Info = countryInfo[1];
                    Info = Info.trim();
                    //if there is nothing after the equals sign then the country is an island so we add it to the countryMap with the value being an empty hashMap
                    if (Info == null)
                    {
                        CountryMap.put(edgeCase(Name), new HashMap<>());
                    }
                    else
                    {
                        //otherwise we split on ;
                        data = Info.split(";");
                        String CountryKey ="";
                        //if only one country borders the main one
                        if (data.length == 1)
                        {
                            //split the string based on spaces
                            String[] line = data[0].split(" ");
                            //iterate over the split strings until we ruch something that isn't a name of a country
                            for (int i = 0; i < line.length -2; i++)
                            {
                                CountryKey += line[i];
                                CountryKey+=" ";
                            }//if we didn't already add an entry for the main country name
                            if (!CountryMap.containsKey(Name))
                            {
                                //add it to the main map hashMap and set the distance val to be MAX VALUE
                                HashMap<String, Integer> goal = new HashMap<>();
                                goal.put(edgeCase(CountryKey), Integer.MAX_VALUE);
                                CountryMap.put(edgeCase(Name), goal);
                            }
                            else
                            {
                                //else do .get.put() into the main map
                                //set distance value to MAX VALUE
                               CountryMap.get(edgeCase(Name)).put(edgeCase(CountryKey), Integer.MAX_VALUE);
                            }
                        }
                        //if there are more than one border country
                        else if (data.length > 1)
                        {
                            for (int i =0; i < data.length; i++)
                            {
                                //split the string based on spaces
                                String[] justCountry = data[i].trim().split(" ");
                                String value = "";
                                //iterate until we see a number
                                for (int j = 0; j < justCountry.length -2; j++)
                                {
                                    value += justCountry[j];
                                    value += " ";
                                }
                                value = value.trim();
                                //if we didn't already add teh main country to map
                                if(!CountryMap.containsKey(Name))
                                {
                                    //add it and make its value the MAX VALUE
                                    HashMap<String, Integer> goal = new HashMap<>();
                                    goal.put(edgeCase(value), Integer.MAX_VALUE);
                                    CountryMap.put(edgeCase(Name), goal);
                                }
                                else
                                {
                                    //else do .get.put() and make the distnace value Integer.MAX_VALUE
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
        //reads capdist.csv
        public void readCapDist(String fileName)
        {
            try
            {
                //scanner used to read file
                Scanner scan = new Scanner(new File(fileName));
                //skip first line
                String Line1 = scan.nextLine();
                //while there is a line
                while(scan.hasNextLine())
                {
                    //split string based on ,
                    String[] Line = scan.nextLine().split(",");
                    //get the ID of the origin country
                    String countID = Line[1];
                    //get the full name of the origin country if it exists
                    if (nameIDMapReverse.get(edgeCase(countID)) != null)
                    {
                        String CountName = nameIDMapReverse.get(edgeCase(countID));
                        //edgeCase() sets all country names to the borders.txt version of those names
                        CountName = edgeCase(CountName);
                        //get the hashmap of the bordering countries
                        HashMap<String, Integer> borderMap = CountryMap.get(edgeCase(CountName));
                        //if the country borders other countries
                        if (borderMap!= null)
                        {
                            //iterate over the HashMap and get the keys of the countries that are being bordered
                            for (String borderNames : borderMap.keySet())
                            {
                                //if the name has an associating acronym
                                if (nameIDMap.get(edgeCase(borderNames)) != null)
                                {
                                    //get the acronym from the name
                                    String borderID = nameIDMap.get(edgeCase(borderNames));
                                    //if the obtained acronym equals the border acronym from the txt file
                                    if (borderID.equals(Line[3]))
                                    {
                                        //we get the distance value from the file
                                        Integer dist = Integer.parseInt(Line[4]);
                                        //we overwrite the main map to have the distance number
                                        borderMap.put(edgeCase(borderNames),dist);
                                        CountryMap.put(edgeCase(CountName), borderMap);
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
        //this method takes in a string and sees if it is an alias name
        //if so it sets it to be the borders.txt version of that name
        //otherwise it just returns the base string just trimmed
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
            else if (CountryName.equals("Great Britain"))
            {
                return "United Kingdom";
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
        //the constructor makes the graph object read the files

        try
        {
            //this reads in the command line arguments
            borders = args[0];
            capDist = args[1];
            stateName = args[2];
            //this passes in the files as arguments
            g.readBordersFile(borders);
            g.readStates_NamesFile(stateName);;
            g.readCapDist(capDist);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
//getDistance returns the distnace between two adjacent countries
    public int getDistance (String country1, String country2)
    {
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
    //this is the method where we make a path between two countries
    public List<String> findPath (String country1, String country2)
    {
        // Make new heap that contains a string that represents a path of countries where which the countries are separated by a "/"
        //e.g. path from US to Canada would be United States/Canada
        HashMap <String,Integer> heapMap = new HashMap<String, Integer>();
        PriorityQueue<HashMap <String, Integer>> heap = new PriorityQueue();
        heapMap.put(country1, 0);
        //add the input country and its distance ie 0 to heap
        heap.offer(heapMap);
        //initialize a destination Heap
        //this heap only contains paths from the origin to the destination
        //this is so we can get the fastest path
        HashMap<String, Integer> DestMap = new HashMap<>();
        PriorityQueue<HashMap<String, Integer>> DestHeap = new PriorityQueue<>();
        int total;
        //while the main path heap isn't empty
        while (!heap.isEmpty())
        {
            //get the path string and its distance
           HashMap<String, Integer> heapEntry =  heap.poll();
           //given a hashmap entry in heap we then iterate to get its key, since there is only one key per entry this loop should actually be O(1)
           for (String origin : heapEntry.keySet())
           {
               //then we get the distance given that path string
               total = heapEntry.get(origin);
               //then we split the path string by / to isolate each country in that path
               //this is important so we can get the last country so we can see its neighbors
               String[] heapKey = origin.split("/");
               //this gives me the last country in the path
               String key = heapKey[heapKey.length -1];
               //then we get the neighbor
               for (String destination: g.CountryMap.get(key).keySet())
               {
                   if(!origin.contains(destination))
                   {
                       //then we get the distance given that path string and reset it
                       total = heapEntry.get(origin);
                       //then we get the distance between current country and its neighbors
                       int distance = getDistance(key, destination);
                       //we then add the distance to the total value
                       total += distance;
                       //then we add the name of the neighbors to the path string
                       String path = origin + "/" + destination;
                       //add entry to main heap if and only if we don't add the final destination to the heap
                       if (!path.contains(country2))
                       {
                           heapMap.put(path, total);
                           heap.offer(heapMap);
                       }
                       //otherwise we add that country to the heap of destnations
                       else
                       {
                           DestMap.put(origin, total);
                           DestHeap.offer(DestMap);
                       }
                   }
               }
           }
           //this makes a list of strings to get the route
           List<String> PathToDest = new LinkedList<>();
           //if the heap is empty them our path never leads to the destination
            //i.e. our countries are on the other side of the world or one of them is an island
           if (DestHeap.isEmpty())
           {
               return PathToDest;
           }
           else
           {
               //otherwise just get the highest priority item
               //we don't need to check the other ones as they would all have a higher distance value
               HashMap<String, Integer> BestPathMap = DestHeap.poll();
               //we get the key from the heap that we poll
               for (String PathKey: BestPathMap.keySet())
               {
                   //we split the string based on "/" to get each individual country that makes up the route
                   String[] CountriesInPath = PathKey.split("/");
                   for (int i = 0; i < CountriesInPath.length; i++)
                   {
                       //this adds each individual country from the path onto the list
                       PathToDest.add(CountriesInPath[i]);
                   }
                   //returns list
                   return PathToDest;
               }
           }
        }
        return null;
    }
    //this accepts the user input and calls edge case
    public void acceptUserInput()
    {
        //this receives the input for the input countires
        System.out.println("Enter the name of the first country (type EXIT to quit):");
        //we read the keyboard to get the input
        Scanner scan = new Scanner(System.in);
        String Country1 = scan.nextLine();
        //gets the proper name for 1st country
        Country1 = g.edgeCase(Country1);
        //if its not in the map then we assume invalid name
        if (!g.CountryMap.containsKey(Country1))
        {
            System.out.println("Invalid country name. Please enter a valid country name.");
        }
        System.out.println("Enter the name of the first country (type EXIT to quit):");
        String Country2 =  scan.nextLine();
        //the same is for Country2
        Country2 = g.edgeCase(Country2);
        if (!g.CountryMap.containsKey(Country2))
        {
            System.out.println("Invalid country name. Please enter a valid country name.");
        }
        //if we get EXIT in input we stop the program
        if (Country2.equals("EXIT") || Country1.equals("EXIT"))
        {
            return;
        }
        List<String> Pathway = new LinkedList<>();
        Pathway = findPath(Country1, Country2);
        System.out.println(Pathway);
    }
    public static void main(String[] args)
    {
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();

    }
}