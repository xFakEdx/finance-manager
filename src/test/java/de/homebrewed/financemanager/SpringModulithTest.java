package de.homebrewed.financemanager;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class SpringModulithTest {
  ApplicationModules modules = ApplicationModules.of(FinancemanagerApplication.class);

  @Test
  void testSpringModulithModules() {
    modules.verify();
  }

  @Test
  void testWriteDoc() {
    Documenter.DiagramOptions diagramOptions =
        Documenter.DiagramOptions.defaults().withStyle(Documenter.DiagramOptions.DiagramStyle.C4);
    new Documenter(modules)
        .writeModulesAsPlantUml(diagramOptions)
        .writeIndividualModulesAsPlantUml()
        .writeModuleCanvases()
        .writeAggregatingDocument();
  }
}
