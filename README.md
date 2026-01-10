🧠 Concurrency Handling (Optimistic Locking)
Problem

In a real-world e-commerce system, the same product can be updated concurrently by multiple users or services.

Example scenario:

Product stock = 5

Two users attempt to purchase 3 items at the same time

Without proper concurrency control, stock could incorrectly become -1

This leads to data inconsistency and overselling.

Solution: Optimistic Locking

This project uses Optimistic Locking via JPA’s @Version annotation to safely handle concurrent updates.

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    // other fields...
}

How It Works

Each update includes the current version of the entity

On update, Hibernate generates a query like:

UPDATE product
SET inventory = ?, version = version + 1
WHERE id = ? AND version = ?


If another transaction has already updated the record:

The version no longer matches

No rows are affected

Hibernate throws an OptimisticLockException


API Behavior

When a concurrent modification is detected:

The request fails with HTTP 409 – Conflict

The client is informed to retry the operation

Example response:

{
"message": "The product was updated by another transaction. Please retry.",
"status": 409,
"timestamp": "2026-01-09T20:45:12"
}


| Approach            | Reason                              |
| ------------------- | ----------------------------------- |
| Optimistic Locking  | High performance, no DB locks       |
| Pessimistic Locking | Risk of deadlocks, lower throughput |


Optimistic locking is ideal for read-heavy systems like e-commerce platforms.

Benefits

-Prevents overselling

-Ensures data consistency

-No database-level locking

-Scales well under high traffic

-Clean and declarative implementation