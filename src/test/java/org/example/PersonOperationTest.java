package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PersonOperationTest {
  @Mock private PersonParser personParser;
  @Mock private FileWriter fileWriter;

  @InjectMocks private PersonOperation personOperation;

  @Test
  void filterAndSortTest() throws IOException {

    // given
    given(personParser.parsePersons("input.csv"))
        .willReturn(
            List.of(
                new Person("Zoe", "Adams", LocalDate.of(1992, 5, 12)),
                new Person("Jhon", "Smith", LocalDate.of(1996, 4, 12)),
                new Person("helen", "Mustafa", LocalDate.of(2000, 7, 10)),
                new Person("Alan", "Mark", LocalDate.of(1994, 5, 7))));

    // when
    personOperation.birthDayValidation("input.csv", 4, "out.csv");

    // then
    then(fileWriter).should().writeLines("out.csv", List.of("Jhon Smith"));
  }

  @Test
  void noMatchFound() throws IOException {
    // given
    given(personParser.parsePersons("input.csv"))
        .willReturn(List.of(new Person("Ion", "Popescu", LocalDate.of(1971, 4, 1))));
    // when
    personOperation.birthDayValidation("input.csv", 5, "out.csv");

    // then
    then(fileWriter).should().writeLines("out.csv", List.of());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0, 13})
  void invalidMonth(int month) throws IOException {
    // when & then
    assertThrows(
        IllegalArgumentException.class,
        () -> personOperation.birthDayValidation("input.csv", month, "out.csv"));
  }



  @Test
    void sameFirstNameAndLastName() throws IOException {
      //given
      given(personParser.parsePersons("input.csv")).willReturn(List.of(
              new Person("Zoe", "Adams", LocalDate.of(1992, 4, 12)),
              new Person("Zoe", "Smith", LocalDate.of(1996, 4, 12)),
              new Person("zoe", "Mustafa", LocalDate.of(2000, 4, 10))));
      //when
      personOperation.birthDayValidation("input.csv", 4, "out.csv");

      //then
      then(fileWriter).should().writeLines("out.csv", List.of(
              "Zoe Adams","zoe Mustafa","Zoe Smith"));

  }

    @Test
    void parserFail() throws IOException {
        //given
        given(personParser.parsePersons("input.csv")).willThrow(new IOException("File not found"));
        //when & then
        assertThrows(IOException.class, () -> personOperation.birthDayValidation("input.csv", 5, "out.csv"));
    }
    @Test
    void writerFail() throws IOException {
        //given
        given(personParser.parsePersons("input.csv")).willReturn(List.of());
        willThrow(new IOException("Cnnot write file")).given(fileWriter).writeLines("out.csv",List.of());

        //when & then
        assertThrows(IOException.class, () -> personOperation.birthDayValidation("input.csv",5,"out.csv"));
    }
}
