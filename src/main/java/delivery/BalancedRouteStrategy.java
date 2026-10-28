package delivery;
import java.util.List;
public class BalancedRouteStrategy extends BaseRouteStrategy {
    private final double time, cost, distance, emission;
    public BalancedRouteStrategy(double time, double cost, double distance, double emission) {
        if (!Double.isFinite(time) || !Double.isFinite(cost) || !Double.isFinite(distance) ||
            !Double.isFinite(emission) || time < 0 || cost < 0 || distance < 0 || emission < 0 ||
            Math.abs(time + cost + distance + emission - 1.0) > 0.000001)
            throw new IllegalArgumentException("Weights must be nonnegative and sum to 1");
        this.time = time; this.cost = cost; this.distance = distance; this.emission = emission;
    }
    private double score(Route r, List<Route> routes) {
        double maxTime = routes.stream().mapToDouble(Route::totalMinutes).max().orElse(1);
        double maxCost = routes.stream().mapToDouble(Route::cost).max().orElse(1);
        double maxDistance = routes.stream().mapToDouble(Route::distanceKm).max().orElse(1);
        double maxEmission = routes.stream().mapToDouble(Route::carbonEmission).max().orElse(1);
        return time * r.totalMinutes() / Math.max(1, maxTime)
             + cost * r.cost() / Math.max(1, maxCost)
             + distance * r.distanceKm() / Math.max(1, maxDistance)
             + emission * r.carbonEmission() / Math.max(1, maxEmission);
    }
    public Route selectRoute(List<Route> routes, DeliveryRequest request) {
        List<Route> valid = allowed(routes, request);
        return valid.stream().min((a, b) -> Double.compare(score(a, valid), score(b, valid))).orElseThrow();
    }
    public String getStrategyName() { return "Balanced"; }
    public String explain(Route r, DeliveryRequest q) {
        return "Weighted time/cost/distance/emissions with weights " + time + "/" + cost + "/" + distance + "/" + emission;
    }
}