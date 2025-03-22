@ApplicationModule(
    displayName = "TransactionClearing",
    allowedDependencies = {
      "external :: persistence-repository",
      "external :: persistence-entity",
      "events"
    })
package de.homebrewed.financemanager.transactionclearing;

import org.springframework.modulith.ApplicationModule;
