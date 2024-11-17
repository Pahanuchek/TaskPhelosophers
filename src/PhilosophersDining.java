import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosophersDining {


    private static final int NUM_PHILOSOPHERS = 5;

    private static final Lock[] forks = new Lock[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int philosopherIndex = i;
            new Thread(() -> philosopher(philosopherIndex)).start();
        }
    }

    private static void philosopher(int philosopherIndex) {
        int meals = 0;
        while (meals < 3) {
            think();
            takeForks(philosopherIndex);
            eat();
            meals++;
            putForks(philosopherIndex);
        }
    }

    private static void think() {
        try {
            System.out.println(Thread.currentThread().getName() + " размышляет.");
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void takeForks(int philosopherIndex) {
        int leftFork = philosopherIndex;
        int rightFork = (philosopherIndex + 1) % NUM_PHILOSOPHERS;

        if (leftFork < rightFork) {
            forks[leftFork].lock();
            forks[rightFork].lock();
        } else {
            forks[rightFork].lock();
            forks[leftFork].lock();
        }

        System.out.println(Thread.currentThread().getName() + " взял вилки.");
    }

    private static void eat() {
        try {
            System.out.println(Thread.currentThread().getName() + " ест.");
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void putForks(int philosopherIndex) {
        int leftFork = philosopherIndex;
        int rightFork = (philosopherIndex + 1) % NUM_PHILOSOPHERS;

        forks[leftFork].unlock();
        forks[rightFork].unlock();

        System.out.println(Thread.currentThread().getName() + " положил вилки.");
    }
}