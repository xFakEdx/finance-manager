@ApplicationModule(
    displayName = "Workflow",
    allowedDependencies = {
      "domain",
      "events",
      "accounting :: accounting-service",
      "external :: persistence-service",
      "external :: persistence-entity",
      "external :: acl"
    })
package de.homebrewed.financemanager.workflow;

import org.springframework.modulith.ApplicationModule;
