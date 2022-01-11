package com.feriadoRest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.feriadoRest.model.Feriado;
import com.feriadoRest.repository.FeriadoRepository;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/feriadoRest")
public class FeriadoController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired(required = true)
	private FeriadoRepository repository;

	@GetMapping(path = "/get", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> get() {
		List<Feriado> feriados = new ArrayList<Feriado>();
		String url = "https://feriados-cl-api.herokuapp.com/feriados";
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("user-agent", "Application");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			Feriado[] feriados2 = restTemplate.exchange(url, HttpMethod.GET, entity, Feriado[].class).getBody();
			feriados = Arrays.asList(feriados2);
			repository.saveAll(feriados);
			return new ResponseEntity<>(feriados, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(new String("Error"), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	
}