package Simulation;

import GUI.GUI;
import Objects.Customer;
import Objects.Queue;

import java.util.List;
import java.util.Random;

public class TimeCounter implements Runnable {
    private static Thread thread;
    private static Random random = new Random();
    private static boolean isRunning = false;
    private static int timeElapsed = 0;
    private double simulationSpeed;
    private List<Queue> queues;
    private GUI.Animation animation;
    private static int randomArrivalTime;
    private static int minArrivalTime;
    private static int maxArrivalTime;
    private static int peakHour;
    private static int customerNumber;


    TimeCounter(double simulationSpeed, List<Queue> queues, GUI.Animation animation, int minA, int maxA) throws InterruptedException {
        thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        this.queues = queues;
        this.simulationSpeed = simulationSpeed;
        this.animation = animation;
        minArrivalTime = minA;
        maxArrivalTime = maxA;
        randomArrivalTime = minArrivalTime + random.nextInt((maxArrivalTime - minArrivalTime) + 1);
    }

    public static void startSimulation(){
        isRunning = true;
        thread.start();
    }

    public static void stopSimulation(){
        isRunning = false;
        thread.stop();
    }

    public static int getPeakHour(){
        return peakHour;
    }

    public static int getCustomerNumber(){
        return customerNumber;
    }

    public static int getTimeElapsed(){
        return timeElapsed;
    }

    public static int getRandomArrivalTime(){
        return randomArrivalTime;
    }

    public static boolean getAddOk(){
        if(randomArrivalTime <= 0) {
            randomArrivalTime = minArrivalTime + random.nextInt((maxArrivalTime - minArrivalTime) + 1);
            return true;
        }
        return false;
    }

    private void customerCounter(){
        int customers = 0;
        for(Queue i:queues) {
            customers += i.getQueue().size();
        }
        if(customers > customerNumber) {
            customerNumber = customers;
            peakHour = timeElapsed;
        }
    }

    private void descreaseQueueWaitingTime(){
        for(Queue i:queues) {
            for(Customer j:i.getQueue()) {
                if(!i.getQueue().get(0).equals(j)) {
                    j.decreaseWaitingTime();
                }
            }
            if(!i.getQueue().isEmpty()) {
                i.getQueue().get(0).decreaseServiceTime();
            }
        }
    }

    private void deadTime(){
        for(Queue i:queues) {
            if (i.getQueue().isEmpty()) {
                i.increaseDeadTime();
            }
        }
    }



    @Override
    public void run() {
        while(isRunning) {
            try {
                Thread.sleep((long)(1000 * simulationSpeed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            customerCounter();
            deadTime();
            timeElapsed++;
            randomArrivalTime--;
            descreaseQueueWaitingTime();
            animation.repaint();
        }
    }
}
