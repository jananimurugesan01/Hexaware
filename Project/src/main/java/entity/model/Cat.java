package entity.model;

public class Cat extends Pet {
    private String color;

    public Cat(String name, int age, String breed, String color) {
        super(name, age, breed);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // Optional: override toString if needed
    @Override
    public String toString() {
        return "Cat [name=" + getName() + ", age=" + getAge() + ", breed=" + getBreed() + ", color=" + color + "]";
    }
}
