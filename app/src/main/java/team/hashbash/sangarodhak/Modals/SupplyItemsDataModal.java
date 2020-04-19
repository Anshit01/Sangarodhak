package team.hashbash.sangarodhak.Modals;

public class SupplyItemsDataModal {

    private String itemName, quantityType;
    private int quantityAvailable, itemRate, quantityBought;

    public SupplyItemsDataModal(String itemName, String quantityType, int quantityAvailable, int itemRate) {
        this.itemName = itemName;
        this.itemRate = itemRate;
        this.quantityType = quantityType;
        this.quantityAvailable = quantityAvailable;
    }

    public SupplyItemsDataModal(String itemName, String quantityType, int quantityAvailable, int itemRate, int quantityBought) {
        this.itemName = itemName;
        this.quantityType = quantityType;
        this.quantityAvailable = quantityAvailable;
        this.itemRate = itemRate;
        this.quantityBought = quantityBought;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemRate() {
        return itemRate;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    public void setQuantityBought(int quantityBought) {
        this.quantityBought = quantityBought;
    }
}
