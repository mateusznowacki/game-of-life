package pl.pwr.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadManager {

    private final List<Thread> threads;
    private final CyclicBarrier entryBarrier;
    private final CyclicBarrier exitBarrier;

    public ThreadManager(int numberOfThreads) {
        this.threads = new ArrayList<>();
        this.entryBarrier = new CyclicBarrier(numberOfThreads + 1);
        this.exitBarrier = new CyclicBarrier(numberOfThreads + 1);

        initializeThreads(numberOfThreads);
    }

    private void initializeThreads(int numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            GameOfLife gameOfLife = new GameOfLife(entryBarrier, exitBarrier, i);
            Thread thread = new Thread(gameOfLife::run);
            threads.add(thread);
        }
    }

    public void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void waitForIterationStart() throws BrokenBarrierException, InterruptedException {
        entryBarrier.await();
    }

    public void waitForIterationEnd() throws BrokenBarrierException, InterruptedException {
        exitBarrier.await();
    }

    public void waitForAllThreads() throws BrokenBarrierException, InterruptedException {
        entryBarrier.await();
        entryBarrier.reset();
        exitBarrier.await();
        exitBarrier.reset();
    }
}
