package delivery;
import java.util.EnumMap;
import java.util.Map;
public class StrategySelector {
    private final Map<DeliveryPriority, RouteStrategy> strategies = new EnumMap<>(DeliveryPriority.class);
    public StrategySelector() {
        strategies.put(DeliveryPriority.URGENT, new FastestRouteStrategy());
        strategies.put(DeliveryPriority.ECONOMY, new CheapestRouteStrategy());
        strategies.put(DeliveryPriority.ECO, new EcoFriendlyRouteStrategy());
        strategies.put(DeliveryPriority.STANDARD, new BalancedRouteStrategy(0.4, 0.3, 0.1, 0.2));
    }
    public void register(DeliveryPriority priority, RouteStrategy strategy) {
        strategies.put(priority, strategy);
    }
    public RouteStrategy select(DeliveryRequest request) {
        if (request == null) throw new IllegalArgumentException("Request is required");
        if (request.ecoPreferred()) return strategies.get(DeliveryPriority.ECO);
        return strategies.get(request.priority());
    }
}