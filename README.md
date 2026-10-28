# Smart Delivery & Route Optimization Engine

**Assignment 5 — Strategy Pattern**  
**Student:** Kuralbaev Erasyl  
**Java:** 17+ | **Build:** Maven | **Tests:** JUnit 5

This beginner-friendly Java console project picks the best delivery route from 8 candidate routes, based on different priorities.

## How to run
1. Open this repository as a Maven project in IntelliJ IDEA.
2. Select JDK 17 or newer.
3. Run `delivery.Main` to see strategy comparison, switching, automatic selection and rejected routes.
4. Run `mvn test` to execute 20 automated tests.

## Architecture
- **Context:** `RoutePlanner`, which keeps a `RouteStrategy` and calls its `selectRoute` method.
- **Strategy:** `RouteStrategy` interface.
- **Concrete Strategies:** `FastestRouteStrategy`, `CheapestRouteStrategy`, `EcoFriendlyRouteStrategy`, `BalancedRouteStrategy`, `DistanceFirstStrategy`.
- **Auto selection:** `StrategySelector`, using a simple map from delivery priorities to strategies.
- **Models:** `Route`, `Location`, `DeliveryRequest`, `DeliveryPriority`.

## Formulas
| Strategy | Formula / rule |
| --- | --- |
| Fastest | Minimum `estimatedMinutes + trafficPenalty` |
| Cheapest | Minimum `cost + distanceKm × 12 + urgent surcharge (250)` |
| Eco | Minimum `carbonEmission × 0.95 + distanceKm × 0.05` |
| Balanced | `0.4 × normalizedTime + 0.3 × normalizedCost + 0.1 × normalizedDistance + 0.2 × normalizedEmission` |
| Distance First (fifth) | Minimum distanceKm |

Balanced values are normalized by dividing each candidate's metric by the maximum valid candidate value in the same criterion. The four weights are configurable; any negative, infinite or non-summing set is rejected.

All strategies first remove routes that are unavailable, over budget, past the deadline, or above the emissions limit. When no route is eligible, they throw `NoSuitableRouteException` rather than silently returning null.

## Example console result (from sample data)
```text
STRATEGY COMPARISON - same request
Fastest -> R2 | Time: 20 + traffic 1 = 21 min
Cheapest -> R5 | Total cost = base 1800.0 + distance 168.0 + urgent fee 0 = 1968.0
Eco friendly -> R4 | Eco score = CO2 0.7 * 0.95 + distance 10.0 * 0.05 = 1.165
```
Other lines include the Balanced decision and automatic URGENT, ECONOMY, ECO and STANDARD examples.

## 10 questions for individual defense
1. **What problem is solved?** Different route algorithms can be swapped without a huge planner if/else.
2. **Where is Context?** `RoutePlanner`.
3. **Where is Strategy?** `RouteStrategy`.
4. **Concrete Strategies?** Fastest, Cheapest, EcoFriendly, Balanced, DistanceFirst.
5. **Where is switching?** `Main` calls `planner.setStrategy(strategy)` several times on the same planner.
6. **Why Strategy rather than if/else?** Each algorithm is in its own class, while the planner works with their common interface.
7. **How to add one?** Implement `RouteStrategy` (or extend `BaseRouteStrategy`) and supply the new object; `RoutePlanner` remains unchanged.
8. **Strategy vs Bridge?** Strategy changes an algorithm for the same task; Bridge separates two hierarchies that can evolve independently.
9. **SOLID?** SRP separates planning, selection and algorithms; OCP allows new algorithms; DIP keeps planner dependent on an interface.
10. **Current limitation?** Routes, traffic and costs are sample data, not live traffic from maps.

## Test coverage
20 JUnit 5 test methods cover four original strategies, fifth strategy, switching, selector, no route, budget, deadline, emissions, unavailable route, invalid weights, negative values, boundaries, and strategy comparisons. **Run `mvn test` locally for an actual pass/fail result.**

## UML and short report
- [Class diagram](docs/class-diagram.md)
- [Sequence diagram](docs/sequence-diagram.md)
- [Short report](docs/report.md)

The repository development history contains exactly 8 commits. The fifth strategy was added in a separate commit.
