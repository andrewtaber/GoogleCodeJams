import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int testCases = in.nextInt();

        for(int i = 0; i < testCases; i++) {
            // Identify number of search engines
            int numberSearchEngines = in.nextInt();

            // Store those search engines in a Set
            Set<String> searchEngines = new HashSet<>();

            for(int j = 0; j < numberSearchEngines; j++) {
                searchEngines.add(in.next());
            }

            // Identify number of Queries
            int numQueries = in.nextInt();

            // Store those queries in an ArrayList
            ArrayList<String> queries = new ArrayList<>();

            for(int k = 0; k < numQueries; k++) {
                queries.add(in.next());
            }

            // Compute least number of switches needed
            int leastSwitches = computeLeastSwitches(searchEngines, queries);


        }


    }

    public static int computeLeastSwitches(Set<String> searchEngines, ArrayList<String> queries) {

        int switches = 0;

        Map<String, Integer> tempCheckNumPlacesMap = new HashMap<>();

        for(String searchEngine : searchEngines) {
            tempCheckNumPlacesMap.put(searchEngine, 0);
        }


        int primaryIndex = 0;

        while (primaryIndex < queries.size()) {

            for(String searchEngine : searchEngines) {

                ListIterator iterator = queries.listIterator(primaryIndex);
                int i = 0;

                while (!iterator.equals(searchEngine) && iterator.hasNext()) {

                    iterator.next();
                    i++;
                }

                tempCheckNumPlacesMap.put(searchEngine, i);
            }

            int largestRun = 0;

            for (int run : tempCheckNumPlacesMap.values()) {
                if(run > largestRun) {
                    largestRun = run;
                }
            }

            primaryIndex += largestRun;

            if (primaryIndex < queries.size()) {
                switches++;
            }
        }

        return switches;

    }
}
