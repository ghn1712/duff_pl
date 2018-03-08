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

	@Test
	public void should_return_404_when_beer_type_does_not_exist() throws UnirestException {
		HttpResponse<String> request = Unirest.get(url + "/types/{beerTypeName}").routeParam("beerTypeName", "test")
				.asString();
		assertEquals(404, request.getStatus());

	}

	@Test
	public void should_return_204_when_no_beer_exists() {

	}

	@Test
	public void should_return_204_when_no_beer_type_exists() {

	}

	@Test
	public void should_return_200_when_beer_is_inserted_correctly() {

	}

	@Test
	public void should_return_200_when_beer_type_is_inserted_correctly() {

	}

	@Test
	public void should_return_400_when_inserting_an_invalid_beer() {

	}

	@Test
	public void should_return_400_when_inserting_an_invalid_beer_type() {

	}

	@Test
	public void should_return_204_when_removing_a_beer() {

	}

	@Test
	public void should_return_204_when_removing_a_beer_type() {

	}

	@Test
	public void should_return_403_when_post_beer_with_path_param_that_doesnt_exist() {

	}

	@Test
	public void should_return_403_when_post_beer_type_with_path_param_that_doesnt_exist() {

	}

	@Test
	public void should_return_200_when_update_with_post_a_resource_that_exists() {

	}
}
