package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.ciclic.duff.Service;
import br.com.ciclic.duff.utils.InjectorHelper;

public class ServiceITCase {
	static Service service;
	static String url = "http://localhost:4567/beer";

	@BeforeClass
	public static void setup() {
		service = InjectorHelper.getInjector().getInstance(Service.class);
		service.start();
	}

	@Test
	public void should_return_404_when_beer_does_not_exist() throws UnirestException {
		HttpResponse<String> request = Unirest.get(url + "/{beerName}").routeParam("beerName", "test").asString();
		assertEquals(404, request.getStatus());
	}
}
