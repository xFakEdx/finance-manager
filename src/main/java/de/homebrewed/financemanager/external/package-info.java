@ApplicationModule(
    displayName = "External",
    allowedDependencies = {"accounting :: accounting-service", "shared :: commands", "domain"})
package de.homebrewed.financemanager.external;

import org.springframework.modulith.ApplicationModule;
