package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@ExtendWith(MockitoExtension.class)
class PersonParserTest {
  @Mock private FileReader fileReader;
  @InjectMocks private PersonParser personParser;

  @Test
  void validCsv() throws IOException {
    // given
    given(fileReader.readLines("input.csv"))
        .willReturn(List.of("Zoe, Adams, 1990-12-05", "       ", "Jhon, Stuart, 1995-03-04", ""));

    // when
    List<Person> result = personParser.parsePersons("input.csv");

    // then
    assertEquals(2, result.size());
    assertEquals(new Person("Zoe", "Adams", LocalDate.of(1990, 12, 5)), result.get(0));
    assertEquals(new Person("Jhon", "Stuart", LocalDate.of(1995, 3, 4)), result.get(1));
  }

  @Test
  void dateFormatInvalid() throws IOException {
    // given
    given(fileReader.readLines("badDate.csv")).willReturn(List.of("Adam, Smith, 14-05-1990"));

    // when&then
    assertThrows(DateTimeException.class, () -> personParser.parsePersons("badDate.csv"));
  }

  @Test
  void wrongNumberOfColumns() throws IOException {
    // given
    given(fileReader.readLines("bad.csv")).willReturn(List.of("Adam, Smith"));

    // when & then
    assertThrows(IllegalArgumentException.class, () -> personParser.parsePersons("bad.csv"));
  }

  @Test
  void emptyFile() throws IOException {
    // given
    given(fileReader.readLines("empty.csv")).willReturn(List.of());

    // when
    List<Person> result = personParser.parsePersons("empty.csv");

    // then
    assertTrue(result.isEmpty());
  }
}
