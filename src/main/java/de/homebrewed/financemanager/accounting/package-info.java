@ApplicationModule(
    displayName = "Accounting",
    allowedDependencies = {"domain", "acl :: out-persistence", "shared :: commands", "events"})
package de.homebrewed.financemanager.accounting;

import org.springframework.modulith.ApplicationModule;
