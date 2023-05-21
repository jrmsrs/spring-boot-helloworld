package io.jrmsrs.echo.controller;

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

import io.jrmsrs.echo.model.ObjectResponse;
import io.jrmsrs.echo.webservice.SwapiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API Echo - Projeto base para a disciplina de Programação Modular.
 * 
 * Os métodos da API Echo são documentados utilizando a especificação OpenAPI
 * (Swagger) e podem ser visualizados
 * acessando a URL do domínio + "/swagger-ui.html".
 * Acesso em servidor local: http://localhost:8080/swagger-ui.html
 */

@RestController
@RequestMapping("/hello")
@Api(value = "Hello World")
public class EchoController {

	@Autowired
	private SwapiClient swapi;

	@GetMapping("")
	@ApiOperation(value = "Returns a hello world message")
	public ObjectResponse getHelloWorld() {
		ObjectResponse response = new ObjectResponse();
		response.setMessage("Hello World!");
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("/{name}")
	@ApiOperation(value = "Returns a hello world message with the name informed in the request")
	public ObjectResponse getHelloName(@PathVariable String name) {
		ObjectResponse response = new ObjectResponse();
		response.setMessage("Hello, " + name + "!");
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("/planet")
	@ApiOperation(value = "Returns a planet name from Star Wars with the id [range 1-60] informed in the request")
	public @ResponseBody ObjectResponse getPlanetName(@RequestParam String id) {
		ObjectResponse response = new ObjectResponse();
		if (id == null || id.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Parameter 'id' is missing");
		} else if (Integer.parseInt(id) < 1 || Integer.parseInt(id) > 60) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Parameter 'id' must be a number between 1 and 60");
		}
		response.setMessage("Star Wars planet: " + swapi.getPlanetName(id));
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@GetMapping("planet/*")
	@ResponseStatus(HttpStatus.OK)
	public ObjectResponse getRoot() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Put id as {id} url parameter e.g. /hello/planet?id=3. See available endpoints at /swagger-ui.html");
	}

}
