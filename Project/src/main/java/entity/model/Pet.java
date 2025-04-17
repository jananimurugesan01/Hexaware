
//File: entity/model/Pet.java
package entity.model;

public class Pet {
 private String name;
 private int age;
 private String breed;

 public Pet(String name, int age, String breed) {
     if (age <= 0) throw new IllegalArgumentException("Pet age must be positive");
     this.name = name;
     this.age = age;
     this.breed = breed;
 }

 // Getters
 public String getName() {
     return name;
 }

 public int getAge() {
     return age;
 }

 public String getBreed() {
     return breed;
 }


 public void setName(String name) {
     this.name = name;
 }

 public void setAge(int age) {
     if (age <= 0) throw new IllegalArgumentException("Pet age must be positive");
     this.age = age;
 }

 public void setBreed(String breed) {
     this.breed = breed;
 }

 @Override
 public String toString() {
     return "Pet{name='" + name + "', age=" + age + ", breed='" + breed + "'}";
 }

public String getExtraInfo() {
	// TODO Auto-generated method stub
	return null;
}

public String getType() {
	// TODO Auto-generated method stub
	return null;
}
}
