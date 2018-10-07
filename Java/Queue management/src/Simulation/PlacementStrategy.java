package Simulation;

import Objects.Queue;

import java.util.List;

public interface PlacementStrategy {
    int getPlace(List<Queue> queues);
}
