@ApplicationModule(
    displayName = "Accounting",
    allowedDependencies = {
      "external :: persistence-service",
      "external :: acl",
      "domain",
      "shared :: commands",
      "events"
    })
package de.homebrewed.financemanager.accounting;

import org.springframework.modulith.ApplicationModule;
