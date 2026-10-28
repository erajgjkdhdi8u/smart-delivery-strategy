package delivery;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<Route> routes = SampleData.routes();
        DeliveryRequest request = new DeliveryRequest(DeliveryPriority.STANDARD, 5000, 60, false, 5);
        RoutePlanner planner = new RoutePlanner(new FastestRouteStrategy());
        RouteStrategy[] strategies = {
            new FastestRouteStrategy(), new CheapestRouteStrategy(),
            new EcoFriendlyRouteStrategy(), new BalancedRouteStrategy(0.4, 0.3, 0.1, 0.2)
        };
        System.out.println("STRATEGY COMPARISON - same request");
        for (RouteStrategy strategy : strategies) {
            planner.setStrategy(strategy); // 3 runtime switches after the first strategy
            try {
                Route selected = planner.plan(routes, request);
                System.out.println(planner.strategyName() + " -> " + selected.id() +
                    " | " + planner.explain(selected, request));
            } catch (NoSuitableRouteException e) {
                System.out.println(planner.strategyName() + " -> " + e.getMessage());
            }
        }
    }
}