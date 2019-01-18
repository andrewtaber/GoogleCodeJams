import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("/Users/andytaber/A-large-practice.in");

        Scanner in = new Scanner(file);

        int testCases = Integer.parseInt(in.nextLine());

        ArrayList<Integer> switchesPerTestCase = new ArrayList<>();

        for(int i = 0; i < testCases; i++) {
            // Identify number of search engines
            int numberSearchEngines = Integer.parseInt(in.nextLine());

            // Store those search engines in a Set
            Set<String> searchEngines = new HashSet<>();

            for(int j = 0; j < numberSearchEngines; j++) {
                searchEngines.add(in.nextLine());
            }

            // Identify number of Queries
            int numQueries = Integer.parseInt(in.nextLine());

            // Store those queries in an ArrayList
            ArrayList<String> queries = new ArrayList<>();

            for(int k = 0; k < numQueries; k++) {
                queries.add(in.nextLine());
            }

            // Compute least number of switches needed
            int leastSwitches = computeLeastSwitches(searchEngines, queries);

            System.out.println(leastSwitches);

            switchesPerTestCase.add(leastSwitches);


        }

        int caseNumber = 1;
        for (int numSwitches : switchesPerTestCase) {
            System.out.println("Case #" + caseNumber + ": " + numSwitches);
            caseNumber++;
        }

    }

    public static int computeLeastSwitches(Set<String> searchEngines, ArrayList<String> queries) {

        int switches = 0;

        Map<String, Integer> tempCheckNumPlacesMap = new HashMap<>();

        for(String searchEngine : searchEngines) {
            tempCheckNumPlacesMap.put(searchEngine, 0);
        }

        int primaryIndex = 0;

        while (primaryIndex < queries.size() - 1) {

            for(String searchEngine : searchEngines) {

                int i = 0;

                while (!queries.get(i + primaryIndex).equals(searchEngine) && (primaryIndex + i + 1 < queries.size())) {
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

            if (primaryIndex < queries.size() - 1) {
                switches++;
            }
        }

        System.out.println(tempCheckNumPlacesMap);
        return switches;

    }
}
