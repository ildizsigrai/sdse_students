package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCSVProcessor {

    public static void main(String[] args) {
        CityCSVProcessor processor = new CityCSVProcessor();
        File dataFile = new File("data/Cities.csv");
        processor.processCityData(dataFile);
    }

    public void processCityData(File file) {
        // Try with resource statement (as of Java 8)
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Discard header row
            br.readLine();

            String line;

            List<CityRecord> allRecords = new ArrayList<>();
            Map<String, List<CityRecord>> recordsByCity = new HashMap<>();

            while ((line = br.readLine()) != null) {
                // Parse each line
                String[] rawValues = line.split(",");

                int id = convertToInt(rawValues[0]);
                int year = convertToInt(rawValues[1]);
                String city = convertToString(rawValues[2]);
                int population = convertToInt(rawValues[3]);

                // Create a new CityRecord object
                CityRecord cityRecord = new CityRecord(id, year, city, population);

                // Check if the city name is already in the map
                if (!recordsByCity.containsKey(city)) {
                    recordsByCity.put(city, new ArrayList<>());
                }

                recordsByCity.get(city).add(cityRecord);
                allRecords.add(cityRecord);
            }

            // Process and print data for each city
            for (Map.Entry<String, List<CityRecord>> entry : recordsByCity.entrySet()) {
                String city = entry.getKey();
                List<CityRecord> recordsOfCity = entry.getValue();
                int totalEntries = recordsOfCity.size();
                int minYear = Integer.MAX_VALUE;
                int maxYear = Integer.MIN_VALUE;
                int totalPopulation = 0;

                for (CityRecord record : recordsOfCity) {
                    totalPopulation += record.getPopulation();
                    minYear = Math.min(minYear, record.getYear());
                    maxYear = Math.max(maxYear, record.getYear());
                }


                int averagePopulation = totalPopulation / totalEntries;

                // Print processed data for this city
                System.out.println("Average population of " + city + " (" + minYear + "-" + maxYear + "; " +
                        totalEntries + " entries): " + averagePopulation);
            }
        } catch (Exception e) {
            System.err.println("An error occurred:");
            e.printStackTrace();
        }
    }

    private String convertToString(String rawValue) {
        rawValue = cleanRawValue(rawValue);

        if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
            return rawValue.substring(1, rawValue.length() - 1);
        }

        return rawValue;
    }

    private int convertToInt(String rawValue) {
        rawValue = cleanRawValue(rawValue);
        return Integer.parseInt(rawValue);
    }

    private String cleanRawValue(String rawValue) {
        return rawValue.trim();
    }
}
