package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonParser {
  private final FileReader fileLineReader;

  public PersonParser(FileReader fileLineReader) {
    this.fileLineReader = fileLineReader;
  }

  public List<Person> parsePersons(String fileName) throws IOException {
    List<String> lines = fileLineReader.readLines(fileName);
    return lines.stream().filter(line -> !line.isBlank()).map(this::parseLine).toList();
  }

  private Person parseLine(String line) {
    String[] fields = line.split(",");
    if (fields.length != 3) {
      throw new IllegalArgumentException("Invalid line format");
    }
    String firstName = fields[0].trim();
    String lastName = fields[1].trim();
    LocalDate birthDate = LocalDate.parse(fields[2].trim());
    return new Person(firstName, lastName, birthDate);
  }
}
