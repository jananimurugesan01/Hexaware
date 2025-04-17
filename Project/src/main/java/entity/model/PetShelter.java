
package entity.model;

import java.util.ArrayList;
import java.util.List;

public class PetShelter implements IAdoptable {
    private List<Pet> availablePets = new ArrayList<>();

    public void addPet(Pet pet) {
        availablePets.add(pet);
    }

    public void removePet(Pet pet) {
        availablePets.remove(pet);
    }

    public List<Pet> getAvailablePets() {
        return availablePets;
    }

    public void listAvailablePets() {
        if (availablePets.isEmpty()) {
            System.out.println("No pets currently available.");
        } else {
            for (Pet pet : availablePets) {
                try {
                    System.out.println(pet);
                } catch (NullPointerException e) {
                    System.out.println("Pet details missing.");
                }
            }
        }
    }

    @Override
    public void adopt() {
        System.out.println("Pet Shelter is participating in the adoption event.");
    }
}
