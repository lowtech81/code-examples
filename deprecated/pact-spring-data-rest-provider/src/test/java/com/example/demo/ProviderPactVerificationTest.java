package com.example.demo;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.example.framework.DatabaseStateHolder;
import com.example.framework.SpringBootStarter;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class)
@Provider("customerServiceProvider")
@PactFolder("../pact-feign-consumer/target/pacts")
public class ProviderPactVerificationTest {

  @ClassRule
  public static SpringBootStarter appStarter = SpringBootStarter.builder()
          .withApplicationClass(DemoApplication.class)
          .withArgument("--spring.config.location=classpath:/application-pact.properties")
          .withDatabaseState("single-address", "/initial-schema.sql", "/single-address.sql")
          .withDatabaseState("address-collection", "/initial-schema.sql", "/address-collection.sql")
          .build();

  @State("a single address")
  public void toSingleAddressState() {
    DatabaseStateHolder.setCurrentDatabaseState("single-address");
  }

  @State("a collection of 2 addresses")
  public void toAddressCollectionState() {
    DatabaseStateHolder.setCurrentDatabaseState("address-collection");
  }

  @TestTarget
  public final Target target = new HttpTarget(8080);

}
