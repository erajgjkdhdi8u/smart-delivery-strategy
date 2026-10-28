package delivery;
import java.util.Comparator;
import java.util.List;
public class FastestRouteStrategy extends BaseRouteStrategy {
    public Route selectRoute(List<Route> routes, DeliveryRequest request) {
        return allowed(routes, request).stream()
            .min(Comparator.comparingInt(Route::totalMinutes).thenComparingDouble(Route::cost)).orElseThrow();
    }
    public String getStrategyName() { return "Fastest"; }
    public String explain(Route r, DeliveryRequest q) {
        return "Time: " + r.estimatedMinutes() + " + traffic " + r.trafficPenalty() + " = " + r.totalMinutes() + " min";
    }
}