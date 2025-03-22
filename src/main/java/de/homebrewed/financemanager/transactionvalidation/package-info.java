@ApplicationModule(
    displayName = "TransactionValidation",
    allowedDependencies = {
      "events",
      "external :: persistence-repository",
      "external :: persistence-entity"
    })
package de.homebrewed.financemanager.transactionvalidation;

import org.springframework.modulith.ApplicationModule;
