package com.hashbash.sangarodhak.Modals;

public class CountryCaseDataModal {
    private String stateName, totalCases, totalActiveCases, totalRecovered, totalDeaths;
    private boolean isExpanded = false;

    public CountryCaseDataModal(String stateName, String totalCases, String totalActiveCases, String totalRecovered, String totalDeaths) {
        this.stateName = stateName;
        this.totalCases = totalCases;
        this.totalActiveCases = totalActiveCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
    }

    public String getStateName() {
        return stateName;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public String getTotalActiveCases() {
        return totalActiveCases;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
