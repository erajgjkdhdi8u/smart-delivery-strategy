package delivery;
import java.util.Comparator;
import java.util.List;
public class EcoFriendlyRouteStrategy extends BaseRouteStrategy {
    public double ecoScore(Route r) { return r.carbonEmission() * 0.8 + r.distanceKm() * 0.2; }
    public Route selectRoute(List<Route> routes, DeliveryRequest request) {
        return allowed(routes, request).stream().min(Comparator.comparingDouble(this::ecoScore)).orElseThrow();
    }
    public String getStrategyName() { return "Eco friendly"; }
    public String explain(Route r, DeliveryRequest q) {
        return "Eco score = CO2 " + r.carbonEmission() + " * 0.8 + distance " +
            r.distanceKm() + " * 0.2 = " + ecoScore(r);
    }
}