package dao;

import entity.model.CashDonation;
import util.DBConnUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonationDAOImpl implements DonationDAO {

    @Override
    public void addDonation(CashDonation donation) {
        String query = "INSERT INTO donations(donor_name, amount, donation_type, donation_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, donation.getDonorName());
            ps.setDouble(2, donation.getAmount());
            ps.setString(3, "cash");
            ps.setDate(4, Date.valueOf(donation.getDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting donation: " + e.getMessage());
        }
    }
}
