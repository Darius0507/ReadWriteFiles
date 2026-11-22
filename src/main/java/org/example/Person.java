package org.example;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
  private final String firstName;
  private final String lastName;
  private final LocalDate birthDate;

  public Person(String firstName, String lastName, LocalDate birthDate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(firstName, person.firstName)
        && Objects.equals(lastName, person.lastName)
        && Objects.equals(birthDate, person.birthDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, birthDate);
  }
}
