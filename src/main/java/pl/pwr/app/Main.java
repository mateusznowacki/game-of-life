package pl.pwr.app;

import pl.pwr.inputs.DataParser;
import pl.pwr.inputs.FileValidator;

public class Main {
    public static void main(String[] args) {
        FileValidator fileValidator = new FileValidator();
        String filePath ="src/main/resources/oscillator.txt";
        System.out.println(fileValidator.validateFile(filePath));
        DataParser dataParser = new DataParser();
        dataParser.parseData(filePath);
        CurrentState.getInstance().getMap().printMap();

    }
}