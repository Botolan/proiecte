package Objects;

import Simulation.SimulationManager;
import Simulation.TimeCounter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue implements Runnable{
    private List<Customer> queue;
    private Thread thread;
    private int nrQueue;
    private boolean isRunning;
    private int simulationTime;
    private int deadTime;
    private double simS;


    public Queue(int nrQueue, int simT, double simS){
        this.simS = simS;
        thread = new Thread(this);
        this.nrQueue = nrQueue;
        this.simulationTime = simT;
        queue = Collections.synchronizedList(new LinkedList<Customer>());
    }


    public List<Customer> getQueue(){
        return this.queue;
    }


    public void increaseDeadTime(){
        deadTime++;
    }


    public void addCustomer(Customer customer) {
        queue.add(customer);
    }

    public int getNrQueue(){
        return nrQueue;
    }

    public void removeCustomer(){
        queue.remove(0);
    }

    public int getQueueSize(){
        return queue.size();
    }


    public void start(){
        this.thread.start();
        isRunning = true;
    }

    public int getDeadTime(){
        return deadTime;
    }

    public void stop(){
        this.thread.stop();
        isRunning = false;
    }

    @Override
    public void run() {
        while(isRunning && (simulationTime >= TimeCounter.getTimeElapsed())) {
            try {
                Thread.sleep((long) (50 * simS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!queue.isEmpty()) {
                if (queue.get(0).isZero()) {
                    SimulationManager.removeCustomer(this, nrQueue);
                }
            }
        }
        TimeCounter.stopSimulation();
        this.stop();
    }
}
