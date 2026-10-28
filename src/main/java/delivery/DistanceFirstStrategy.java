package delivery;
import java.util.Comparator;
import java.util.List;
// Added after the first four algorithms. RoutePlanner did not need to change.
public class DistanceFirstStrategy extends BaseRouteStrategy {
    public Route selectRoute(List<Route> routes, DeliveryRequest request) {
        return allowed(routes, request).stream().min(Comparator.comparingDouble(Route::distanceKm)).orElseThrow();
    }
    public String getStrategyName() { return "Shortest distance"; }
    public String explain(Route r, DeliveryRequest q) { return "Shortest available distance = " + r.distanceKm() + " km"; }
}