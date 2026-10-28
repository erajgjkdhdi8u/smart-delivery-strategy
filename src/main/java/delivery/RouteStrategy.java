package delivery;
import java.util.List;
public interface RouteStrategy {
    Route selectRoute(List<Route> routes, DeliveryRequest request);
    String getStrategyName();
    String explain(Route route, DeliveryRequest request);
}