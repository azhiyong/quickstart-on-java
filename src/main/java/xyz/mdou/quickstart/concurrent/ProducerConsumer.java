package xyz.mdou.quickstart.concurrent;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class Producer implements Runnable {
    private BlockingQueue<Product> blockingQueue;

    public Producer(BlockingQueue<Product> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                blockingQueue.put(producer());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Product producer() {
        return new Product();
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Product> blockingQueue;

    public Consumer(BlockingQueue<Product> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Product take = blockingQueue.take();
                System.out.println(new Date() + ": " + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Product {}

class Setup {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Product> blockingQueue = new LinkedBlockingQueue<>(1);
        IntStream.range(1, 5).forEach(i -> new Thread(new Producer(blockingQueue)).start());
        IntStream.range(1, 5).forEach(i -> new Thread(new Consumer(blockingQueue)).start());
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
