package delivery;
public record Route(String id, Location origin, Location destination, double distanceKm,
                    int estimatedMinutes, double cost, double carbonEmission,
                    int trafficPenalty, boolean available) {
    public Route {
        if (id == null || id.isBlank() || origin == null || destination == null)
            throw new IllegalArgumentException("Route details are required");
        if (distanceKm < 0 || estimatedMinutes < 0 || cost < 0 || carbonEmission < 0 || trafficPenalty < 0)
            throw new IllegalArgumentException("Route numbers cannot be negative");
    }
    public int totalMinutes() { return estimatedMinutes + trafficPenalty; }
}