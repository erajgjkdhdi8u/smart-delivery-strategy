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
            planner.setStrategy(strategy);
            try {
                Route selected = planner.plan(routes, request);
                System.out.println(planner.strategyName() + " -> " + selected.id() +
                    " | " + planner.explain(selected, request));
            } catch (NoSuitableRouteException e) {
                System.out.println(planner.strategyName() + " -> " + e.getMessage());
            }
        }
        System.out.println("\nAUTOMATIC STRATEGY SELECTION");
        StrategySelector selector = new StrategySelector();
        DeliveryRequest[] requests = {
            new DeliveryRequest(DeliveryPriority.URGENT, 5000, 26, false, 5),
            new DeliveryRequest(DeliveryPriority.ECONOMY, 3000, 60, false, 5),
            new DeliveryRequest(DeliveryPriority.ECO, 5000, 60, true, 1.6),
            new DeliveryRequest(DeliveryPriority.STANDARD, 5000, 60, false, 5)
        };
        for (DeliveryRequest req : requests) {
            planner.setStrategy(selector.select(req));
            try {
                Route route = planner.plan(routes, req);
                System.out.println(req.priority() + " -> " + planner.strategyName() + " -> " + route.id());
            } catch (NoSuitableRouteException e) {
                System.out.println(req.priority() + " -> " + e.getMessage());
            }
        }
        System.out.println("\nREJECTED ROUTES for STANDARD:");
        for (Route r : routes) {
            if (!r.available()) System.out.println(r.id() + ": unavailable");
            else if (r.cost() > request.maxBudget()) System.out.println(r.id() + ": budget exceeded");
            else if (r.totalMinutes() > request.maxDeliveryMinutes()) System.out.println(r.id() + ": deadline exceeded");
            else if (r.carbonEmission() > request.maxEmission()) System.out.println(r.id() + ": emissions exceeded");
        }
    }
}