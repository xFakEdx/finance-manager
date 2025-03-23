@ApplicationModule(
    displayName = "External",
    allowedDependencies = {"events", "shared :: commands", "domain"})
package de.homebrewed.financemanager.external;

import org.springframework.modulith.ApplicationModule;
