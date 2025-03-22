@ApplicationModule(
    displayName = "TransactionProcessing",
    allowedDependencies = {
      "external",
      "accounting :: accounting-service",
      "external :: persistence-repository",
      "events"
    })
package de.homebrewed.financemanager.transactionprocessing;

import org.springframework.modulith.ApplicationModule;
