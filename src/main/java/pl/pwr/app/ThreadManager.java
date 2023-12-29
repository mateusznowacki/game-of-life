package pl.pwr.app;

public class ThreadManager {

    private int numberOfThreads;

    public ThreadManager(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public Thread[] createThreads() {
        Thread[] threads = new Thread[numberOfThreads];
        return threads;
    }

}
