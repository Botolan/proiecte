package Simulation;

import Objects.Queue;

import java.util.List;

public class FreeQueue implements PlacementStrategy {
    @Override
    public int getPlace(List<Queue> queues) {
        int minCustomers = Integer.MAX_VALUE;
        int index = 0;
        for(Queue i:queues) {
            if(minCustomers > i.getQueueSize()){
                index = i.getNrQueue();
                minCustomers = i.getQueueSize();
            }
        }
        return index;
    }
}
