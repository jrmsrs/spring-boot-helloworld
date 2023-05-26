package io.jrmsrs.hello.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloControllerTest {
  HelloController hc = new HelloController();

  @Test
  void testFormattedHelloName() {
    assertEquals("Hello, World!", hc.formattedHelloName("World"));
    assertEquals("Hello, Jane!", hc.formattedHelloName("Jane"));
    assertEquals("Hello, John Doe!", hc.formattedHelloName("John Doe"));
  }

  @Test
  void testInvalidPlanetIdFormat() {
    assertTrue(hc.invalidPlanetIdFormat("a"));
    assertTrue(hc.invalidPlanetIdFormat("1a"));
    assertFalse(hc.invalidPlanetIdFormat("1"));
    assertTrue(hc.invalidPlanetIdFormat("-1"));
    assertTrue(hc.invalidPlanetIdFormat("0"));
    assertFalse(hc.invalidPlanetIdFormat("60"));
    assertTrue(hc.invalidPlanetIdFormat("61"));
  }

}
