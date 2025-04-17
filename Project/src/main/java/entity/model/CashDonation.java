
package entity.model;

import java.time.LocalDate;

public class CashDonation extends Donation {
    private LocalDate donationDate;

    public CashDonation(String donorName, double amount,String donationType,String itemType, LocalDate donationDate) {
        super(donorName, amount);
        if (amount < 10) throw new IllegalArgumentException("Minimum donation is 10");
        this.donationDate = donationDate;
    }

    public LocalDate getDate() {
        return donationDate;
    }

    @Override
    public void recordDonation() {
        System.out.println("Recorded cash donation: $" + amount + " by " + donorName + " on " + donationDate);
    }

	public String getDonorName() {
		// TODO Auto-generated method stub
		return donorName;
	}

	public double getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}
}
