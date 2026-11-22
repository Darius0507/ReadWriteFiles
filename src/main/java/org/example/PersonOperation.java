package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PersonOperation {
  private final PersonParser personParser;
  private final FileWriter fileWriter;

  public PersonOperation() {
    this.personParser = new PersonParser(new FileReader());
    fileWriter = new FileWriter();
  }

  public PersonOperation(PersonParser personParser, FileWriter fileWriter) {
    this.personParser = personParser;
    this.fileWriter = fileWriter;
  }

  public void birthDayValidation(String inputFilName, int targetMonth, String outputFileName)
      throws IOException {
    int validMonth =
        Optional.of(targetMonth)
            .filter(month -> month >= 1 && month <= 12)
            .orElseThrow(
                () ->
                    new IllegalArgumentException("Invalid month! Month must be between 1 and 12"));

    List<String> names =
        personParser.parsePersons(inputFilName).stream()
            .filter(person -> person.getBirthDate().getMonthValue() == validMonth)
            .sorted(
                Comparator.comparing(Person::getFirstName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Person::getLastName, String.CASE_INSENSITIVE_ORDER))
            .map(person -> person.getFirstName() + " " + person.getLastName())
            .toList();

    fileWriter.writeLines(outputFileName, names);
  }
}
