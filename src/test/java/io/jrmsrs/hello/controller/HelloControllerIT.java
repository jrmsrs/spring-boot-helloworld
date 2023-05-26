package io.jrmsrs.hello.controller;

// Integration test

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.jrmsrs.hello.model.ObjectResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testGetHelloWorld() {
		// rota /hello
		ResponseEntity<ObjectResponse> response = restTemplate.getForEntity(createURL("/hello"), ObjectResponse.class);
		ObjectResponse body = response.getBody();
		assertNotNull(body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, World!", body.getMessage());
		assertEquals("200 OK", body.getStatus());

		// rota /hello/
		response = restTemplate.getForEntity(createURL("/hello/"), ObjectResponse.class);
		body = response.getBody();
		assertNotNull(body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, World!", body.getMessage());
		assertEquals("200 OK", body.getStatus());
	}

	@Test
	void testGetHelloName() {
		// rota /hello/John
		String name = "John";
		ResponseEntity<ObjectResponse> response = restTemplate.getForEntity(createURL("/hello/" + name),
				ObjectResponse.class);
		ObjectResponse body = response.getBody();
		assertNotNull(body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, " + name + "!", body.getMessage());
		assertEquals("200 OK", body.getStatus());
	}

	@Test
	void testGetPlanetName() {
		// rota /hello/planet?id=1
		String planetId = "1";
		ResponseEntity<ObjectResponse> response = restTemplate.getForEntity(createURL("/hello/planet?id=" + planetId),
				ObjectResponse.class);
		ObjectResponse body = response.getBody();
		assertNotNull(body);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Star Wars planet: Tatooine", body.getMessage());
		assertEquals("200 OK", body.getStatus());

		// rota /hello/planet?id=61
		planetId = "61";
		response = restTemplate.getForEntity(createURL("/hello/planet?id=" + planetId), ObjectResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// rota /hello/planet?id=0
		planetId = "0";
		response = restTemplate.getForEntity(createURL("/hello/planet?id=" + planetId), ObjectResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// rota /hello/planet?id=abc
		planetId = "abc";
		response = restTemplate.getForEntity(createURL("/hello/planet?id=" + planetId), ObjectResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private String createURL(String uri) {
		return "http://localhost:" + port + uri;
	}
}
