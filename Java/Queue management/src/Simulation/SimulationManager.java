package Simulation;

import GUI.GUI;
import Objects.Customer;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Objects.Queue;

public class SimulationManager implements Runnable{
    private static List<Queue> queues;
    private static PlacementStrategy strategy;
    private static GUI.Animation animation;
    private Thread thread;
    private static int simulationTime;
    private int nrQ;
    private double simulationSpeed;
    private int minServiceTime;
    private int maxServiceTime;
    private static int totalWaitingTime;
    private static int totalCustomerNumber;
    private static int totalServiceTime;


    private void setStrategy(String st){
        switch (st) {
            case "Random":
                strategy = new RandomStrategy();
                break;
            case "Cel mai scurt timp de asteptare la coada":
                strategy = new MinWaitingStrategy();
                break;
            case "Cea mai libera coada":
                strategy = new FreeQueue();
                break;
        }
    }


    public SimulationManager(int minA, int maxA, int minS, int maxS, int nrQ, int simT, double simulationSpeed, GUI.Animation animation, String st) throws InterruptedException {
        this.minServiceTime = minS;
        this.maxServiceTime = maxS;


        this.simulationSpeed = simulationSpeed;
        thread = new Thread(this);
        simulationTime = simT;
        queues = new LinkedList<Queue>();
        this.nrQ = nrQ;
        for(int i = 0;i < nrQ;i++)  {
            Queue queue = new Queue(i, simT, simulationSpeed);
            queues.add(queue);
        }
        new TimeCounter(simulationSpeed, queues, animation, minA, maxA);
        this.setStrategy(st);
        SimulationManager.animation = animation;
        animation.setNrQ(nrQ);
        animation.setQueueList(queues);
        animation.repaint();
        thread.start();
        TimeCounter.startSimulation();
        for(Queue i:queues) {
            i.start();
        }
    }

    private static void addCustomer(Customer customer){
        Queue queue = queues.get(strategy.getPlace(queues));
        if(!queue.getQueue().isEmpty()) {
            customer.setWaitingTime(queue.getQueue().get(queue.getQueue().size() - 1).getWaitingTime());
            totalWaitingTime += customer.getWaitingTime();
        }
        else {
            customer.setWaitingTime(0);
        }
        totalWaitingTime += customer.getWaitingTime();
        totalServiceTime += customer.getServiceTime();
        totalCustomerNumber++;
        customer.setQueueNr(queue.getNrQueue());
        queue.addCustomer(customer);
        GUI.writeInLog(TimeCounter.getTimeElapsed() + " : Asezat la coada " + (customer.getQueueNr() + 1) + " " + customer.customerInfo());
        animation.repaint();
    }

    public static void removeCustomer(Queue queue, int queueNumber) {
        if(simulationTime > TimeCounter.getTimeElapsed()) {
            GUI.writeInLog(TimeCounter.getTimeElapsed() + " : A iesit de la coada " + (queueNumber + 1) + " clientul cu ID:" + (queue.getQueue().get(0).getCustomerId()));
            queue.removeCustomer();
            animation.repaint();
        }
    }


    private static void printStatistics(){
        GUI.writeInLog("Cei mai multi clienti au fost la secunda:" + TimeCounter.getPeakHour() + " numar de clienti " + TimeCounter.getCustomerNumber());
        GUI.writeInLog("Timpul mediu de asteptare a fost de " + 1.0 * totalWaitingTime/totalCustomerNumber);
        GUI.writeInLog("Timpul mediu de servire a fost de " + 1.0 * totalServiceTime/totalCustomerNumber);
        for(Queue i:queues) {
            GUI.writeInLog("Casa " + (i.getNrQueue() + 1) + " a stat goala " + i.getDeadTime() + " secunde");
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        int randomServiceTime = minServiceTime + random.nextInt((maxServiceTime - minServiceTime) + 1);
        addCustomer(new Customer(randomServiceTime, TimeCounter.getRandomArrivalTime()));
        while(TimeCounter.getTimeElapsed() < simulationTime) {
            try {
                Thread.sleep((long) (100 * simulationSpeed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(TimeCounter.getAddOk()) {
                randomServiceTime = minServiceTime + random.nextInt((maxServiceTime - minServiceTime) + 1);
                addCustomer(new Customer(randomServiceTime, TimeCounter.getRandomArrivalTime()));
            }
        }
        TimeCounter.stopSimulation();
        for(int i = 0;i < nrQ;i++)  {
            queues.get(i).stop();
        }
        GUI.writeInLog("STOP!");
        printStatistics();
        this.thread.stop();
    }
}
