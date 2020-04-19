package team.hashbash.sangarodhak.Modals;

public class StateCaseDataModal {
    private String districtName, totalCases;

    public StateCaseDataModal(String districtName, String totalCases) {
        this.districtName = districtName;
        this.totalCases = totalCases;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getTotalCases() {
        return totalCases;
    }
}
