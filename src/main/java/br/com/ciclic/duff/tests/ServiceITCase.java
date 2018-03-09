package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.Service;
import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;
import br.com.ciclic.duff.utils.InjectorHelper;

public class ServiceITCase {
	static Service service;
	static final String beerUrl = "http://localhost:4567/beers";
	static final String beerTypeUrl = "http://localhost:4567/beerTypes";
	static Gson serializer = new Gson();
	static final String beerPathParam = "";
	static Repository<BeerTypeVO> beerTypeRepo;
	static Repository<BeerVO> beerRepo;

	@BeforeClass
	public static void setup() {
		service = InjectorHelper.getInjector().getInstance(Service.class);
		beerTypeRepo = InjectorHelper.getInjector().getInstance(Key.get(new TypeLiteral<Repository<BeerTypeVO>>() {
		}));
		beerRepo = InjectorHelper.getInjector().getInstance(Key.get(new TypeLiteral<Repository<BeerVO>>() {
		}));
		service.start();
	}

	@Test
	public void should_return_404_when_beer_does_not_exist() throws UnirestException {
		HttpResponse<String> request = Unirest.get(beerUrl + "/{beerName}").routeParam("beerName", "test").asString();
		assertEquals(HttpStatus.NOT_FOUND_404, request.getStatus());
	}

	@Test
	public void should_return_404_when_beer_type_does_not_exist() throws UnirestException {
		HttpResponse<String> request = Unirest.get(beerTypeUrl + "/{beerTypeName}").routeParam("beerTypeName", "test")
				.asString();
		assertEquals(HttpStatus.NOT_FOUND_404, request.getStatus());

	}

	@Test
	public void should_return_204_when_no_beer_exists() throws UnirestException {
		HttpResponse<String> request = Unirest.get(beerUrl).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, request.getStatus());

	}

	@Test
	public void should_return_204_when_no_beer_type_exists() throws UnirestException {
		HttpResponse<String> request = Unirest.get(beerTypeUrl).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, request.getStatus());

	}

	@Test
	public void should_return_204_when_beer_is_inserted_correctly() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(10, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> putBeer = Unirest.put(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(serializer.toJson(new BeerTypeVO("ipa"))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeer.getStatus());
	}

	@Test
	public void should_return_204_when_beer_type_is_inserted_correctly() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").body(serializer.toJson(new TemperatureVO(3, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

	}

	@Test
	public void should_return_400_when_inserting_an_invalid_beer() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").body(serializer.toJson(new TemperatureVO(0, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> putBeer = Unirest.put(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(new JSONObject("{\"invalid\": \"invalid\"}")).asString();
		assertEquals(HttpStatus.BAD_REQUEST_400, putBeer.getStatus());

	}

	@Test
	public void should_return_400_when_inserting_an_invalid_beer_type() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "test").asString();
		assertEquals(HttpStatus.BAD_REQUEST_400, putBeerType.getStatus());

	}

	@Test
	public void should_return_204_when_removing_a_beer() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(10, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> putBeer = Unirest.put(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(serializer.toJson(new BeerTypeVO("ipa"))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeer.getStatus());

		HttpResponse<String> deleteBeer = Unirest.delete(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.asString();
		assertEquals(HttpStatus.NO_CONTENT_204, deleteBeer.getStatus());

		HttpResponse<String> deleteAgainBeer = Unirest.delete(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.asString();
		assertEquals(HttpStatus.NO_CONTENT_204, deleteAgainBeer.getStatus());

	}

	@Test
	public void should_return_204_when_removing_a_beer_type() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").body(serializer.toJson(new TemperatureVO(7, -10))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> deleteBeerType = Unirest.delete(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").asString();
		assertEquals(HttpStatus.NO_CONTENT_204, deleteBeerType.getStatus());

		HttpResponse<String> deleteAgainBeerType = Unirest.delete(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").asString();
		assertEquals(HttpStatus.NO_CONTENT_204, deleteAgainBeerType.getStatus());

	}

	@Test
	public void should_return_400_when_update_beer_with_name_that_doesnt_exist() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").body(serializer.toJson(new TemperatureVO(7, -10))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> updateBeer = Unirest.post(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(serializer.toJson(new BeerTypeVO("dunkel"))).asString();
		assertEquals(HttpStatus.BAD_REQUEST_400, updateBeer.getStatus());

	}

	@Test
	public void should_return_400_when_update_beer_type_with_name_that_doesnt_exist() throws UnirestException {
		HttpResponse<String> updateBeerType = Unirest.post(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(-9, -10))).asString();
		assertEquals(HttpStatus.BAD_REQUEST_400, updateBeerType.getStatus());

	}

	@Test
	public void should_return_204_when_update_a_beer_that_exists() throws UnirestException {
		HttpResponse<String> ipaBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(10, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, ipaBeerType.getStatus());

		HttpResponse<String> putBeer = Unirest.put(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(serializer.toJson(new BeerTypeVO("ipa"))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeer.getStatus());

		HttpResponse<String> dunkelBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "dunkel").body(serializer.toJson(new TemperatureVO(0, -5))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, dunkelBeerType.getStatus());

		HttpResponse<String> updateBeer = Unirest.post(beerUrl + "/{beerName}").routeParam("beerName", "skol")
				.body(serializer.toJson(new BeerTypeVO("dunkel"))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, updateBeer.getStatus());
	}

	@Test
	public void should_return_204_when_update_a_beer_type_that_exists() throws UnirestException {
		HttpResponse<String> putBeerType = Unirest.put(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(-9, -10))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, putBeerType.getStatus());

		HttpResponse<String> updateBeerType = Unirest.post(beerTypeUrl + "/{beerTypeName}")
				.routeParam("beerTypeName", "ipa").body(serializer.toJson(new TemperatureVO(10, 0))).asString();
		assertEquals(HttpStatus.NO_CONTENT_204, updateBeerType.getStatus());

	}

	@After
	public void tear_down() {
		beerTypeRepo.truncate();
		beerRepo.truncate();
	}
}
