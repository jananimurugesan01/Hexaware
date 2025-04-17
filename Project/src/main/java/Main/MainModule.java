
package Main;

import dao.DonationDAO;
import dao.DonationDAOImpl;
import dao.PetDAO;
import dao.PetDAOImpl;
import entity.model.*;
import exception.AdoptionException;
import exception.InsufficientFundsException;
import util.DBConnUtil;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) throws InsufficientFundsException {
        Scanner sc = new Scanner(System.in);
        PetShelter shelter = new PetShelter();
        PetDAO petDAO = new PetDAOImpl();
        DonationDAO donationDAO = new DonationDAOImpl();

        while (true) {
            System.out.println("\n=== PetPals Platform ===");
            System.out.println("1. Add Pet");
            System.out.println("2. Show Available Pets");
            System.out.println("3. Make Cash Donation");
            System.out.println("4. Host Adoption Event");
            System.out.println("5. Read Pets from File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Enter pet type (dog/cat):");
                        String type = sc.next().toLowerCase();

                        System.out.print("Enter name: ");
                        String name = sc.next();
                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter breed: ");
                        String breed = sc.next();

                        Pet pet;
                        if (type.equals("dog")) {
                            System.out.print("Enter specific dog breed: ");
                            String dogBreed = sc.next();
                            pet = new Dog(name, age, breed, dogBreed);
                        } else if (type.equals("cat")) {
                            System.out.print("Enter cat color: ");
                            String color = sc.next();
                            pet = new Cat(name, age, breed, color);
                        } else {
                            System.out.println("Invalid pet type.");
                            break;
                        }

                        petDAO.addPet(pet);
                        System.out.println("Pet added successfully.");
                    } 
                    catch (Exception e) {
                        System.out.println(" Invalid input. Age must be a number.");
                    
                    }
                 break;
             
                  

                case 2:
                    try {
                        List<Pet> pets = petDAO.getAllPets();
                        for (Pet p : pets) {
                            System.out.println(p);
                        }
                    } catch (Exception e) {
                        System.out.println("Error retrieving pets: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter donor name: ");
                        String donor_name = sc.next();
                        System.out.print("Enter donation amount: ");
                        double amount = sc.nextInt();
                        System.out.print("Enter donation type: ");
                        String donation_type = sc.next();
                        System.out.print("Enter Item type: ");
                        String item_type = sc.next();
        
                        if (amount < 10) throw new IllegalArgumentException("Minimum donation is 10.");

                        CashDonation donation = new CashDonation(donor_name, amount,donation_type,item_type, LocalDate.now());
                        donationDAO.addDonation(donation);

                        System.out.println("Donation recorded successfully.");
                    } catch (Exception e) {
                        System.out.println("Unexpected error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                    	
                        if (shelter.getAvailablePets().isEmpty()) {
                            throw new AdoptionException("No pets available for adoption.");
                        }

                        sc.nextLine(); 
                        System.out.print("Enter event name: ");
                        String eventName = sc.nextLine();

                        System.out.print("Enter number of participants: ");
                        int participantCount = sc.nextInt();
                        sc.nextLine(); // consume newline

                        System.out.println("Enter names of participants:");
                        String[] participants = new String[participantCount];
                        for (int i = 0; i < participantCount; i++) {
                            System.out.print("Participant " + (i + 1) + ": ");
                            participants[i] = sc.nextLine();
                        }

                        Connection conn = DBConnUtil.getConnection(); // without db.properties
                        PreparedStatement eventStmt = conn.prepareStatement(
                            "INSERT INTO adoption_events (event_name, event_date) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                        );
                        eventStmt.setString(1, eventName);
                        eventStmt.setDate(2, Date.valueOf(LocalDate.now()));
                        eventStmt.executeUpdate();

                        ResultSet generatedKeys = eventStmt.getGeneratedKeys();
                        int eventId = -1;
                        if (generatedKeys.next()) {
                            eventId = generatedKeys.getInt(1);
                        }

                        PreparedStatement participantStmt = conn.prepareStatement(
                            "INSERT INTO participants (event_id, participant_name) VALUES (?, ?)"
                        );
                        for (String participant : participants) {
                            participantStmt.setInt(1, eventId);
                            participantStmt.setString(2, participant);
                            participantStmt.executeUpdate();
                        }

                        System.out.println("Adoption event and participants saved successfully.");

                        participantStmt.close();
                        eventStmt.close();
                        conn.close();

                    } catch (AdoptionException e) {
                        System.out.println("Adoption error: " + e.getMessage());
                    } catch (SQLException e) {
                        System.out.println("SQL Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    try (BufferedReader reader = new BufferedReader(new FileReader("pets.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] data = line.split(",");
                            if (data.length < 4) continue;

                            String type = data[0];
                            String name = data[1];
                            int age = Integer.parseInt(data[2]);
                            String attr = data[3];

                            Pet pet;
                            switch (type.toLowerCase()) {
                                case "dog":
                                    pet = new Dog(name, age, "Mixed", attr);
                                    break;
                                case "cat":
                                    pet = new Cat(name, age, "Mixed", attr);
                                    break;
                                default:
                                    pet = null;
                                    break;
                            }

                            if (pet != null) {
                                petDAO.addPet(pet);
                            }
                        }
                        System.out.println("Pets loaded from file.");
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
