package pl.pwr.app;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
