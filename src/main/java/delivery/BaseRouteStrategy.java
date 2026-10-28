package delivery;
import java.util.List;
public abstract class BaseRouteStrategy implements RouteStrategy {
    protected List<Route> allowed(List<Route> routes, DeliveryRequest request) {
        if (routes == null || routes.isEmpty()) throw new NoSuitableRouteException("No routes were provided");
        if (request == null) throw new IllegalArgumentException("Request is required");
        List<Route> valid = routes.stream().filter(r -> r.available()
            && r.cost() <= request.maxBudget()
            && r.totalMinutes() <= request.maxDeliveryMinutes()
            && r.carbonEmission() <= request.maxEmission()).toList();
        if (valid.isEmpty()) throw new NoSuitableRouteException("No route meets budget, deadline, availability and emission limits");
        return valid;
    }
}