package delivery;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class RoutePlannerTest {
    List<Route> routes = SampleData.routes();
    DeliveryRequest normal = new DeliveryRequest(DeliveryPriority.STANDARD, 5000, 60, false, 5);
    DeliveryRequest request(double budget, int time) {
        return new DeliveryRequest(DeliveryPriority.STANDARD, budget, time, false, 5);
    }
    @Test void fastestSelectsRoute2() { assertEquals("R2", new FastestRouteStrategy().selectRoute(routes, normal).id()); }
    @Test void cheapestSelectsRoute5() { assertEquals("R5", new CheapestRouteStrategy().selectRoute(routes, normal).id()); }
    @Test void ecoSelectsRoute4() { assertEquals("R4", new EcoFriendlyRouteStrategy().selectRoute(routes, normal).id()); }
    @Test void balancedReturnsValidRoute() {
        Route r = new BalancedRouteStrategy(.4,.3,.1,.2).selectRoute(routes, normal);
        assertTrue(r.available() && r.cost() <= 5000);
    }
    @Test void runtimeSwitchWorks() {
        RoutePlanner p = new RoutePlanner(new FastestRouteStrategy());
        assertEquals("R2", p.plan(routes, normal).id());
        p.setStrategy(new CheapestRouteStrategy());
        assertEquals("R5", p.plan(routes, normal).id());
    }
    @Test void selectorUrgent() { assertInstanceOf(FastestRouteStrategy.class, new StrategySelector().select(new DeliveryRequest(DeliveryPriority.URGENT,5000,60,false,5))); }
    @Test void selectorEconomy() { assertInstanceOf(CheapestRouteStrategy.class, new StrategySelector().select(new DeliveryRequest(DeliveryPriority.ECONOMY,5000,60,false,5))); }
    @Test void selectorEcoPreference() { assertInstanceOf(EcoFriendlyRouteStrategy.class, new StrategySelector().select(new DeliveryRequest(DeliveryPriority.STANDARD,5000,60,true,5))); }
    @Test void unavailableRouteExcluded() { assertNotEquals("R7", new DistanceFirstStrategy().selectRoute(routes, normal).id()); }
    @Test void budgetRestriction() {
        Route r = new CheapestRouteStrategy().selectRoute(routes, request(2300,60));
        assertTrue(r.cost() <= 2300);
    }
    @Test void deadlineRestriction() {
        Route r = new FastestRouteStrategy().selectRoute(routes, request(5000,22));
        assertTrue(r.totalMinutes() <= 22);
    }
    @Test void noValidRoute() { assertThrows(NoSuitableRouteException.class, () -> new FastestRouteStrategy().selectRoute(routes, request(100,2))); }
    @Test void invalidWeights() { assertThrows(IllegalArgumentException.class, () -> new BalancedRouteStrategy(.4,.4,.4,.4)); }
    @Test void fifthStrategyChoosesR6() { assertEquals("R6", new DistanceFirstStrategy().selectRoute(routes, normal).id()); }
    @Test void strategiesCanDisagree() {
        assertNotEquals(new FastestRouteStrategy().selectRoute(routes, normal),new CheapestRouteStrategy().selectRoute(routes, normal));
    }
    @Test void emptyRoutes() { assertThrows(NoSuitableRouteException.class, () -> new EcoFriendlyRouteStrategy().selectRoute(List.of(), normal)); }
    @Test void negativeCostRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Route("Bad",routes.get(0).origin(),routes.get(0).destination(),2,20,-1,1,0,true));
    }
    @Test void budgetBoundaryInclusive() { assertDoesNotThrow(() -> new CheapestRouteStrategy().selectRoute(routes, request(1800,60))); }
    @Test void emissionCapApplied() {
        DeliveryRequest ecoLimit = new DeliveryRequest(DeliveryPriority.ECO,5000,60,true,1.0);
        assertEquals("R4", new EcoFriendlyRouteStrategy().selectRoute(routes,ecoLimit).id());
    }
    @Test void nullRequestRejected() { assertThrows(IllegalArgumentException.class, () -> new FastestRouteStrategy().selectRoute(routes,null)); }
}