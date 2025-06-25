# Gadget Factory Store Simulator

This Java project simulates the daily operations of a gadget factory store over a period of 100 days, modeling inventory production, customer orders, order fulfillment, returns, and profit calculations. The program demonstrates object-oriented design, use of core Java data structures, and simulation logic.

---

## Overview

The Gadget Factory Store operates with the following features:

### Production Simulation

- Produces two batches of gadgets daily (5 gadgets per batch).
- Gadget pricing is dynamically calculated based on fluctuating material and production costs.

### Order Management

- Customer orders request random quantities of gadgets.
- Orders are fulfilled immediately if stock allows; otherwise, they are queued for later fulfillment.
- A maximum of 3 pending orders are allowed in the queue at any time.

### Return System

- Each fulfilled order has a 20% chance of being returned within 3 days of fulfillment.
- Returned gadgets are fully refunded but materials are fully recycled (gadgets are not restocked).

### Profit Reporting

- Monthly profit reports are generated, accounting for sales, returns, taxes (8%), and material costs.
- Correctly adjusts profits for cross-month returns.

---

## Key Concepts Demonstrated

- Object-Oriented Programming (OOP)
- Simulation and state management
- Use of Lists, Stacks, and Queues (Java Collections Framework)
- Date management with `LocalDate`
- Randomized event generation (orders, returns)

---

## Technologies Used

- Java 17+
- Java Collections (`List`, `Deque`, `ArrayDeque`)
- `LocalDate` and `Random` classes

---

## Classes

- `Gadget`: Represents individual gadgets with price and unique ID.
- `Order`: Represents customer orders, fulfillment status, and billing.
- `GadgetFactoryStore`: Main simulation class controlling daily operations, inventory, and reporting.

---

## Getting Started

Clone the repository and run the `GadgetFactoryStore` class to start the 100-day simulation. The program outputs daily activity logs and monthly profit summaries to the console.
