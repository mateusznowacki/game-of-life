package pl.pwr.outputs;

public class ConsolePrinter {

    public void printThreadInfo(int threadId, int rowStart, int rowEnd, int columnStart, int columnEnd) {
        System.out.println("tid:" + threadId + " rows: " + rowStart + ":" + rowEnd + "(" + (rowEnd - rowStart)+")"
                + " columns: " + columnStart + ":" + columnEnd + "(" + (columnEnd - columnStart)+")");
    }


    public void printConfigurationInfo(int numberOfThreads) {
        System.out.println("Number of available threads: " + numberOfThreads + " column-based partitioning");
    }

    public void printCurrentIterationNumber(int iterationNumber) {
        System.out.println("Current iteration number: " + iterationNumber);
    }


}
