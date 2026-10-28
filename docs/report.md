# Assignment 5 — Short Report

**Student:** Kuralbaev Erasyl  
**Subject:** Software Design Patterns  
**Project:** Smart Delivery & Route Optimization Engine  
**Pattern:** Strategy | **Java:** 17

## Objective
Create a small route selector that supports interchangeable algorithms for the fastest, cheapest, most eco-friendly, balanced and shortest-distance routes.

## Implementation
The model consists of `Location`, `Route` and `DeliveryRequest`. `RoutePlanner` is Context and delegates choosing to the `RouteStrategy` interface. Five strategy classes implement distinct algorithms. `StrategySelector` maps request priorities to strategies, and `Main` demonstrates repeated runtime switching with the same planner instance.

## Scoring methods
- Fastest: base time + traffic delay.
- Cheapest: base delivery cost + 12 per km + 250 for urgent priority.
- Eco: 0.95 × carbon emissions + 0.05 × distance.
- Balanced: 0.4 normalized time + 0.3 normalized cost + 0.1 normalized distance + 0.2 normalized emissions. Weights must sum to one.
- Fifth, DistanceFirst: minimum km.

Filtering is completed before ranking: unavailable routes, cost > maxBudget, time > maxDeliveryMinutes and emission > maxEmission are rejected.

## Failure handling
Empty candidates or no valid route cause `NoSuitableRouteException`. Negative route values and invalid weights cause `IllegalArgumentException`.

## Example output
```text
Fastest -> R2 | Time: 20 + traffic 1 = 21 min
Cheapest -> R5 | Total cost = base 1800.0 + distance 168.0 + urgent fee 0 = 1968.0
Eco friendly -> R4 | Eco score = CO2 0.7 * 0.95 + distance 10.0 * 0.05 = 1.165
```

## UML
See [class diagram](class-diagram.md) and [sequence diagram](sequence-diagram.md). They match the code.

## Testing
There are 20 meaningful JUnit 5 tests in `RoutePlannerTest`. To capture actual results for submission, run `mvn test` and attach the console screenshot if required. Tests have not been claimed to have passed without execution.

## SOLID
SRP keeps planning, selecting and algorithms separate. OCP allows extra strategies without planner changes. DIP makes `RoutePlanner` depend on `RouteStrategy`.

## Limitation
Demo routes and traffic penalties are manually defined and not connected to live maps.
