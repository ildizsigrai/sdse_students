package edu.sdse.csvprocessor;

public class CityRecord {
    private int id;
    private int year;
    private String city;
    private int population;

    // Constructor:
    public CityRecord(int id, int year, String city, int population) {
        this.id = id;
        this.year = year;
        this.city = city;
        this.population = population;
    }

    // Getter methods for population and year:
    public int getPopulation() {
        return population;
    }

    public int getYear() {
        return year;
    }

    // City records are formatted appropriately when printed:
    @Override
    public String toString() {
        return "id: " + id + ", year: " + year + ", city: " + city + ", population: " + population;
    }
}
