package com.rusinak.carstat.ui.statistiky;

public class StatistikyModel {
    String menoStat;
    String hodnStat;

    public StatistikyModel(String menoStat, String hodnStat) {
        this.menoStat = menoStat;
        this.hodnStat = hodnStat;
    }

    public String getMenoStat() {
        return menoStat;
    }

    public String getHodnStat() {
        return hodnStat;
    }
}
