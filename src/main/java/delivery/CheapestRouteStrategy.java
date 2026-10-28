package delivery;
import java.util.Comparator;
import java.util.List;
public class CheapestRouteStrategy extends BaseRouteStrategy {
    public double totalCost(Route r, DeliveryRequest q) {
        return r.cost() + 12 * r.distanceKm() + (q.priority() == DeliveryPriority.URGENT ? 250 : 0);
    }
    public Route selectRoute(List<Route> routes, DeliveryRequest request) {
        return allowed(routes, request).stream()
            .min(Comparator.comparingDouble(r -> totalCost(r, request))).orElseThrow();
    }
    public String getStrategyName() { return "Cheapest"; }
    public String explain(Route r, DeliveryRequest q) {
        return "Total cost = base " + r.cost() + " + distance " + (12 * r.distanceKm()) +
            " + urgent fee " + (q.priority() == DeliveryPriority.URGENT ? 250 : 0) +
            " = " + totalCost(r, q);
    }
}