package io.jrmsrs.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import io.jrmsrs.hello.model.ObjectResponse;
import io.jrmsrs.hello.webservice.SwapiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hello")
@Api(value = "Hello World")
public class HelloController {

	@Autowired
	private SwapiClient swapi;

	@GetMapping("")
	@ApiOperation(value = "Returns a hello world message")
	public ObjectResponse getHelloWorld() {
		ObjectResponse response = new ObjectResponse();
		response.setMessage(formattedHelloName("World"));
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("/{name}")
	@ApiOperation(value = "Returns a hello world message with the name informed in the request")
	public ObjectResponse getHelloName(@PathVariable String name) {
		ObjectResponse response = new ObjectResponse();
		response.setMessage(formattedHelloName(name));
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("/planet")
	@ApiOperation(value = "Returns a planet name from Star Wars with the id [range 1-60] informed in the request")
	public @ResponseBody ObjectResponse getPlanetName(@RequestParam String id) {
		ObjectResponse response = new ObjectResponse();
		if (invalidPlanetIdFormat(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid input format, parameter 'id' must be a number between 1 and 60",
					new IllegalArgumentException("Invalid input format, parameter 'id' must be a number between 1 and 60"));
		}
		response.setMessage("Star Wars planet: " + swapi.getPlanetName(id));
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("planet/*")
	@ApiOperation(value = "400: Warn the user the correct way to use /planet the endpoint")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ObjectResponse getRoot() {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Put id as {id} url parameter e.g. /hello/planet?id=3. See available endpoints at /swagger-ui.html");
	}

	public String formattedHelloName(String name) {
		return "Hello, " + name + "!";
	}

	public boolean invalidPlanetIdFormat(String id) {
		if (id == null || id.isEmpty() || isNotCastableToInteger(id))
			return true;
		return Integer.parseInt(id) < 1 || Integer.parseInt(id) > 60;
	}

	public boolean isNotCastableToInteger(String string) {
		try {
			Integer.parseInt(string);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}
}
