package delivery;
import java.util.List;
public class SampleData {
    public static List<Route> routes() {
        Location from = new Location("Warehouse", 51.17, 71.43);
        Location to = new Location("Customer", 51.12, 71.47);
        return List.of(
            new Route("R1", from, to, 7, 18, 4200, 2.1, 7, true),
            new Route("R2", from, to, 9, 20, 3400, 2.5, 1, true),
            new Route("R3", from, to, 12, 27, 2200, 1.9, 2, true),
            new Route("R4", from, to, 10, 29, 3100, 0.7, 0, true),
            new Route("R5", from, to, 14, 35, 1800, 2.9, 0, true),
            new Route("R6", from, to, 6, 26, 3700, 1.2, 8, true),
            new Route("R7", from, to, 5, 17, 4800, 3.1, 0, false),
            new Route("R8", from, to, 11, 22, 2900, 1.5, 3, true)
        );
    }
}