package com.hashbash.sangarodhak.Modals;

public class GlobalCaseDataModal {
    private String countryCode, countryName, totalCases, totalActiveCases, totalRecovered, totalDeaths;

    private boolean isExpanded = false;

    public GlobalCaseDataModal(String countryCode, String countryName, String totalCases, String totalActiveCases, String totalRecovered, String totalDeaths) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.totalCases = totalCases;
        this.totalActiveCases = totalActiveCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
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
