package pl.pwr.inputs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileValidator {
    public boolean validateFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Wczytaj rozmiar planszy oraz liczbę iteracji
            int rows = Integer.parseInt(reader.readLine().trim());
            int columns = Integer.parseInt(reader.readLine().trim());
            int iterations = Integer.parseInt(reader.readLine().trim());

            // Wczytaj liczbę współrzędnych żywych komórek
            int liveCellsCount = Integer.parseInt(reader.readLine().trim());

            // Sprawdź, czy liczba współrzędnych zgadza się z rzeczywistą liczbą współrzędnych w pliku
            for (int i = 0; i < liveCellsCount; i++) {
                String line = reader.readLine();
                if (line == null) {
                    System.out.println("Not enough lines for live cells.");
                    return false;
                }
                String[] coordinates = line.split(" ");
                if (coordinates.length != 2) {
                    System.out.println("Invalid coordinates format.");
                    return false;
                }
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                // Sprawdź, czy współrzędne mieszczą się w granicach planszy
                if (x < 0 || x >= rows || y < 0 || y >= columns) {
                    System.out.println("Invalid cell coordinates: (" + x + ", " + y + ")");
                    return false;
                }
            }

            // Sprawdź, czy nie ma dodatkowych linii w pliku
            if (reader.readLine() != null) {
                System.out.println("Extra lines in the file.");
                return false;
            }

            // Plik jest poprawny
            return true;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }


}
