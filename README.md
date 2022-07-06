### Rewards Calculator based on customer transaction
###The rest API to get customer rewards based on customer Id

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

- The package name is structured as com.rewards.rewardsCalculator
- Exception is thrown if customer does not exists.
- H2 in-memory database to store the information.
- Install H2 db locally and run it. Change the db settings in application.properties file.
- Do run the Initialscript.sql on H2 in memory DB to prepare the test data.

```
 http://localhost:8080/customers/{customerId}/rewards
```

Due to time constraint these were not covered -
- Unit tests
- Integration tests
- Exceptions