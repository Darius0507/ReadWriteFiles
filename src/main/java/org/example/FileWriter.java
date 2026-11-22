package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

  public void writeLines(String fileName, List<String> lines) throws IOException {
    Files.write(Paths.get(fileName), lines);
  }
}
