package xyz.mdou.quickstart.concurrence;

import java.util.ArrayList;
import java.util.List;

public class PV {

    public static void main(String[] args) {
        new PV().doTask();
    }

    private void doTask() {
        try {
            Repository repository = new Repository();
            Thread threadProduce = new Thread(new Producer(repository));
            Thread threadConsumer = new Thread(new Consumer(repository));
            threadProduce.start();
            threadConsumer.start();
            threadProduce.join();
            threadConsumer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Repository {

        private List<Product> productList = new ArrayList<>(1);

        private synchronized void inProduct(Product product) {
            if (productList.size() > 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("in product to repository.");
            productList.add(product);
            notify();
        }

        private synchronized Product outProduct() {
            if (productList.size() <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("out product from repository.");
            Product product = productList.get(0);
            productList.clear();
            notify();
            return product;
        }
    }

    class Product {
    }

    class Producer implements Runnable {

        private Repository repository;

        public Producer(Repository repository) {
            this.repository = repository;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                repository.inProduct(new Product());
            }
        }
    }

    class Consumer implements Runnable {

        private Repository repository;

        public Consumer(Repository repository) {
            this.repository = repository;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                repository.outProduct();
            }
        }
    }
}
