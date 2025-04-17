
package entity.model;

public class ItemDonation extends Donation {
    private String itemType;

    public ItemDonation(String donorName, double estimatedValue, String itemType) {
        super(donorName, estimatedValue);
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    @Override
    public void recordDonation() {
        System.out.println("Recorded item donation: " + itemType + " worth $" + amount + " by " + donorName);
    }
}
