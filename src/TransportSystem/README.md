# Transport System - Low Level Design

## Problem Statement
Design a system where a user can travel from Location A to Location B using multiple transport modes (Bus, Cab, Metro). The system should find:
- **Cheapest path** (minimize cost)
- **Fastest path** (minimize time)

Routes can be direct (A → B) or indirect (A → X → Y → B).

---

## Package Structure

```
TransportSystem/
├── Main.java                 # Entry point
├── enums/
│   └── TransportMode.java    # BUS, CAB, METRO
├── model/
│   ├── Location.java         # Graph node
│   ├── Route.java            # Graph edge
│   └── Path.java             # Result path
├── service/
│   └── TransportService.java # Core logic
└── strategy/
    ├── PathFindingStrategy.java
    ├── CheapestPathStrategy.java
    └── FastestPathStrategy.java
```

---

## Core Concepts

### 1. Graph Representation
The transport network is modeled as a **directed weighted graph**:
- **Nodes** = Locations (A, B, X, Y, Z)
- **Edges** = Routes (with cost, time, transport mode)

```
Example Graph:

    A ----[CAB, ₹500, 20min]----> B
    |                             ^
    |[BUS, ₹50, 30min]           |[BUS, ₹60, 35min]
    v                             |
    X ----[BUS, ₹40, 25min]----> Y
```

### 2. Strategy Pattern
Different optimization criteria use different strategies:

| Strategy | Optimizes | Algorithm |
|----------|-----------|-----------|
| CheapestPathStrategy | Cost | Dijkstra's (weight = cost) |
| FastestPathStrategy | Time | Dijkstra's (weight = time) |

---

## How Dijkstra's Algorithm Works

### Step-by-Step Example: Find Cheapest Path from A to B

**Graph:**
```
A --[BUS, ₹50]--> X --[BUS, ₹40]--> Y --[BUS, ₹60]--> B
A --[CAB, ₹500]---------------------------------> B
```

**Algorithm Execution:**

| Step | Current | Queue | Distances (Cost) | Action |
|------|---------|-------|------------------|--------|
| 1 | A | [A] | A=0 | Start at A |
| 2 | A | [X, B] | A=0, X=50, B=500 | Explore neighbors |
| 3 | X | [B, Y] | X=50, Y=90, B=500 | X is cheapest, explore it |
| 4 | Y | [B] | Y=90, B=150 | Found cheaper path to B via Y |
| 5 | B | [] | B=150 | Done! |

**Result:** A → X → Y → B (Cost: ₹150)

---

## Understanding `previousRoute` Map

The `previousRoute` map is crucial for **reconstructing the path** after Dijkstra finds the shortest distances. It answers: *"How did I reach this location?"*

### What it stores
```java
Map<Location, Route> previousRoute
```
- **Key**: A destination location
- **Value**: The route that was used to reach this location with minimum cost/time

### Example: Finding Cheapest Path from A to B

**Graph:**
```
A --[BUS, ₹50]--> X --[BUS, ₹40]--> Y --[BUS, ₹60]--> B
A --[CAB, ₹500]-------------------------------> B
```

**How `previousRoute` gets populated:**

| Step | Processing | Update | previousRoute State |
|------|------------|--------|---------------------|
| 1 | Start at A | - | `{}` (empty) |
| 2 | From A, reach X via BUS (₹50) | X reachable for ₹50 | `{X: Route(A→X)}` |
| 3 | From A, reach B via CAB (₹500) | B reachable for ₹500 | `{X: Route(A→X), B: Route(A→B)}` |
| 4 | From X, reach Y via BUS (₹90 total) | Y reachable for ₹90 | `{X: Route(A→X), B: Route(A→B), Y: Route(X→Y)}` |
| 5 | From Y, reach B via BUS (₹150 total) | ₹150 < ₹500, update B! | `{X: Route(A→X), B: Route(Y→B), Y: Route(X→Y)}` |

**Final `previousRoute` map:**
```
{
    X: Route(A → X, BUS, ₹50),
    Y: Route(X → Y, BUS, ₹40),
    B: Route(Y → B, BUS, ₹60)   // Updated! Was A→B, now Y→B
}
```

### Path Reconstruction (Backtracking)

Starting from destination B, we backtrack using `previousRoute`:

```
Step 1: current = B
        previousRoute.get(B) = Route(Y → B)
        Add Route(Y → B) to list
        current = Y

Step 2: current = Y
        previousRoute.get(Y) = Route(X → Y)
        Add Route(X → Y) to list
        current = X

Step 3: current = X
        previousRoute.get(X) = Route(A → X)
        Add Route(A → X) to list
        current = A

Step 4: current = A (source)
        previousRoute.get(A) = null
        Stop!
```

**Routes collected (reversed order):** `[Route(Y→B), Route(X→Y), Route(A→X)]`

**After reversing:** `[Route(A→X), Route(X→Y), Route(Y→B)]`

**Final Path:** A → X → Y → B (Cost: ₹150)

### Code Reference

```java
// In CheapestPathStrategy.java

// During Dijkstra - store how we reached each neighbor
if (newCost < minCost.getOrDefault(neighbor, Double.MAX_VALUE)) {
    minCost.put(neighbor, newCost);
    previousRoute.put(neighbor, route);  // Remember: to reach 'neighbor', use 'route'
    pq.add(neighbor);
}

// Path reconstruction - backtrack from destination
private Path buildPath(Location source, Location destination, Map<Location, Route> previousRoute) {
    List<Route> routes = new ArrayList<>();
    Location current = destination;

    // Backtrack until we reach source
    while (previousRoute.containsKey(current)) {
        Route route = previousRoute.get(current);
        routes.add(route);
        current = route.getSource();  // Move to previous location
    }

    Collections.reverse(routes);  // Reverse to get source → destination order
    // ... build Path object
}
```

### Why not store Location instead of Route?

Storing the **Route** (not just previous Location) preserves:
- Transport mode (BUS/CAB/METRO)
- Cost of that segment
- Time of that segment

This allows us to build a complete `Path` with all details.

---

## Code Flow

### 1. Setup Locations and Routes
```java
TransportService service = new TransportService();

Location A = service.addLocation("A");
Location B = service.addLocation("B");
Location X = service.addLocation("X");

service.addRoute(A, X, TransportMode.BUS, 50, 30);  // ₹50, 30 mins
service.addRoute(X, B, TransportMode.BUS, 40, 25);  // ₹40, 25 mins
service.addRoute(A, B, TransportMode.CAB, 500, 20); // ₹500, 20 mins
```

### 2. Find Paths
```java
// Cheapest: A -> X -> B (₹90)
Path cheapest = service.findCheapestPath(A, B);
cheapest.printPath();

// Fastest: A -> B direct (20 mins)
Path fastest = service.findFastestPath(A, B);
fastest.printPath();
```

### 3. Output
```
Path from A to B:
  A -> X [BUS] Cost: 50.0, Time: 30.0 mins
  X -> B [BUS] Cost: 40.0, Time: 25.0 mins
Total Cost: 90.0, Total Time: 55.0 mins

Path from A to B:
  A -> B [CAB] Cost: 500.0, Time: 20.0 mins
Total Cost: 500.0, Total Time: 20.0 mins
```

---

## Class Responsibilities

| Class | Responsibility |
|-------|----------------|
| `Location` | Represents a node; implements equals/hashCode for graph operations |
| `Route` | Edge with source, destination, mode, cost, time |
| `Path` | Holds list of routes; calculates total cost/time |
| `TransportService` | Manages graph; provides pathfinding APIs |
| `PathFindingStrategy` | Interface for different optimization strategies |
| `CheapestPathStrategy` | Dijkstra's using cost as weight |
| `FastestPathStrategy` | Dijkstra's using time as weight |

---

## Design Patterns Used

1. **Strategy Pattern** - Swap pathfinding algorithms without changing client code
2. **Service Layer** - TransportService encapsulates business logic

---

## Time & Space Complexity

| Operation | Time | Space |
|-----------|------|-------|
| Add Location | O(1) | O(1) |
| Add Route | O(1) | O(1) |
| Find Path (Dijkstra) | O((V + E) log V) | O(V) |

Where V = number of locations, E = number of routes

---

## How to Run

```bash
# Compile to out directory
javac -d out src/TransportSystem/**/*.java src/TransportSystem/Main.java

# Run
java -cp out TransportSystem.Main
```
