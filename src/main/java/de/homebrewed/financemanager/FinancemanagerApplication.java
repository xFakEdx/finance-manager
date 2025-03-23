package de.homebrewed.financemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.homebrewed.financemanager.external"})
public class FinancemanagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinancemanagerApplication.class, args);
  }
}
