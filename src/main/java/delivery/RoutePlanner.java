package delivery;
import java.util.List;
import java.util.Objects;
public class RoutePlanner {
    private RouteStrategy strategy;
    public RoutePlanner(RouteStrategy strategy) { setStrategy(strategy); }
    public void setStrategy(RouteStrategy strategy) { this.strategy = Objects.requireNonNull(strategy); }
    public Route plan(List<Route> routes, DeliveryRequest request) {
        return strategy.selectRoute(routes, request);
    }
    public String explain(Route route, DeliveryRequest request) { return strategy.explain(route, request); }
    public String strategyName() { return strategy.getStrategyName(); }
}