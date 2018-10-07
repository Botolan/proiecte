package Simulation;

import Objects.Customer;
import Objects.Queue;

import java.util.List;

public class MinWaitingStrategy implements PlacementStrategy{
    @Override
    public int getPlace(List<Queue> queues) {
        int minWaitingTime = Integer.MAX_VALUE;
        int index = 0;
        for(Queue i:queues) {
            int waitingSum = 0;
            for(Customer j:i.getQueue()) {
                waitingSum += j.getServiceTime();
            }
            if(minWaitingTime > waitingSum) {
                minWaitingTime = waitingSum;
                index = i.getNrQueue();
            }
        }
        return index;
    }
}
