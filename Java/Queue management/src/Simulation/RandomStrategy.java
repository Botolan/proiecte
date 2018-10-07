package Simulation;

import Objects.Queue;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements PlacementStrategy{

    @Override
    public int getPlace(List<Queue> queues) {
        Random random = new Random();
        return random.nextInt(queues.size());
    }
}