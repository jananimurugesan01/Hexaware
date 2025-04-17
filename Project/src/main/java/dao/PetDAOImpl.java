package dao;

import entity.model.*;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    @Override
    public void addPet(Pet pet) {
        String query = "INSERT INTO pets(name, age, breed, type, extra_info) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, pet.getName());
            ps.setInt(2, pet.getAge());
            ps.setString(3, pet.getBreed());

            if (pet instanceof Dog) {
                ps.setString(4, "dog");
                ps.setString(5, ((Dog) pet).getDogBreed());
            } else if (pet instanceof Cat) {
                ps.setString(4, "cat");
                ps.setString(5, ((Cat) pet).getColor());
            } else {
                ps.setString(4, "unknown");
                ps.setString(5, null);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting pet: " + e.getMessage());
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM pets";

        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String breed = rs.getString("breed");
                String extra = rs.getString("extra_info");

                Pet pet = null;
                if ("dog".equalsIgnoreCase(type)) {
                    pet = new Dog(name, age, breed, extra);
                } else if ("cat".equalsIgnoreCase(type)) {
                    pet = new Cat(name, age, breed, extra);
                }

                if (pet != null) {
                    pets.add(pet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving pets: " + e.getMessage());
        }

        return pets;
    }
}
