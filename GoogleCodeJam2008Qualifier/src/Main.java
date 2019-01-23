import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File input = new File("/Users/andytaber/A-large-practice.in");
        File output = new File("/Users/andytaber/A-large-practice.out");

        Scanner in = new Scanner(input);

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

            switchesPerTestCase.add(leastSwitches);

        }

        in.close();

        FileWriter fileWriter = new FileWriter(output);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        int caseNumber = 1;
        for (int numSwitches : switchesPerTestCase) {
            printWriter.print("Case #" + caseNumber + ": " + numSwitches + "\n");
            caseNumber++;
        }

        printWriter.close();

    }

    public static int computeLeastSwitches(Set<String> searchEngines, ArrayList<String> queries) {

        int switches = 0;

        Map<String, Integer> tempCheckNumPlacesMap = new HashMap<>();

        for(String searchEngine : searchEngines) {
            tempCheckNumPlacesMap.put(searchEngine, 0);
        }

        //Hold on to current place in list of queries after last switch
        int primaryIndex = 0;

        //Cycle through all queries
        while (primaryIndex < queries.size()) {

            //Check each search engine for a "collision," and record how many queries before a collision
            for(String searchEngine : searchEngines) {

                int i = 0;

                while (!queries.get(i + primaryIndex).equals(searchEngine)) {
                    i++;

                    if (primaryIndex + i == queries.size()) {
                        break;
                    }
                }

                tempCheckNumPlacesMap.put(searchEngine, i);
            }

            int largestRun = 0;

            //Find the largest run out of the search engines used above before a collision (or end of list of queries)
            for (int run : tempCheckNumPlacesMap.values()) {
                if(run > largestRun) {
                    largestRun = run;
                }
            }

            //If the PI + the LR is equal to or greater than number of queries, we didn't need another switch
            if (primaryIndex + largestRun < queries.size()) {
                switches++;
            }

            primaryIndex += largestRun;

        }

        return switches;

    }
}
