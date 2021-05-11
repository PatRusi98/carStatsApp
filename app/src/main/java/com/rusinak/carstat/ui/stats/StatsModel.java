package com.rusinak.carstat.ui.stats;

public class StatsModel {
    String statName;
    String statValue;

    public StatsModel(String statName, String statValue) {
        this.statName = statName;
        this.statValue = statValue;
    }

    /**
     *
     * method that returns name of stat
     *
     * @return name of stat
     */
    public String getStatName() {
        return statName;
    }

    /**
     *
     * method that returns value of stat
     *
     * @return returns value of stat
     */
    public String getStatValue() {
        return statValue;
    }
}
