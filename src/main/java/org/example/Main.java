package org.example;

import javax.imageio.IIOException;
import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException {
        PersonOperation personOperation =new PersonOperation();
        personOperation.birthDayValidation(args[0],Integer.parseInt(args[1]),args[2]);

    }
}
