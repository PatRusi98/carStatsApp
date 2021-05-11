package com.rusinak.carstat.ui.stats;

public class StatsModel {
    String statName;
    String statValue;

    public StatsModel(String statName, String statValue) {
        this.statName = statName;
        this.statValue = statValue;
    }

    public String getStatName() {
        return statName;
    }

    public String getStatValue() {
        return statValue;
    }
}
