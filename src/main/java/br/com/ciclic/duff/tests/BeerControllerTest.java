package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import br.com.ciclic.duff.BeerController;
import br.com.ciclic.duff.BeerControllerImpl;
import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;

public class BeerControllerTest {

	private BeerController toTest;
	private Repository<BeerVO> beerRepository;
	private Repository<BeerTypeVO> beerTypeRepository;
	private Gson serializer = new Gson();
	private BeerTypeVO ipaType;
	private BeerTypeVO dunkelType;
	private BeerTypeVO emptyNameType;
	private TemperatureVO positiveTemperature;
	private TemperatureVO negativeTemperature;
	private TemperatureVO nullMaxTemperature;
	private TemperatureVO nullMinTemperature;
	private TemperatureVO impossibleTemperature;

	private static final String ipaTypeName = "ipa";
	private static final String skolBeerName = "skol";
	private static final String dunkelTypeName = "dunkel";
	private static final String heinekenBeerName = "heineken";

	@Before
	public void setup() {
		beerRepository = new MockBeerRepository();
		beerTypeRepository = new MockBeerTypeRepository();
		toTest = new BeerControllerImpl(beerRepository, beerTypeRepository);
		ipaType = serializer.fromJson(new JSONObject("{\"typeName\": \"ipa\"}").toString(), BeerTypeVO.class);
		dunkelType = serializer.fromJson(new JSONObject("{\"typeName\": \"dunkel\"}").toString(), BeerTypeVO.class);
		emptyNameType = serializer.fromJson(new JSONObject("{\"typeName\": \" \"}").toString(), BeerTypeVO.class);
		positiveTemperature = new TemperatureVO(5, 0);
		negativeTemperature = new TemperatureVO(-1, -3);
		nullMaxTemperature = new TemperatureVO(null, 0);
		nullMinTemperature = new TemperatureVO(0, null);
		impossibleTemperature = new TemperatureVO(0, 10);
	}

	@Test
	public void should_insert_a_type() {
		assertTrue(toTest.createType(ipaTypeName, positiveTemperature));
		Optional<BeerTypeVO> response = beerTypeRepository.get(ipaTypeName);
		assertTrue(response.isPresent());
		BeerTypeVO beerType = response.get();
		assertEquals(ipaType.getTypeName(), beerType.getTypeName());
		assertEquals(positiveTemperature, beerType.getTemperature());
	}

	@Test
	public void should_not_update_type_that_doesnt_exists() {
		assertFalse(toTest.updateType(ipaTypeName, positiveTemperature));
		assertTrue(beerTypeRepository.getAll().isEmpty());
	}

	@Test
	public void should_update_type() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertTrue(toTest.updateType(ipaTypeName, negativeTemperature));
		assertEquals(1, beerTypeRepository.getAll().size());
		BeerTypeVO response = beerTypeRepository.get(ipaTypeName).get();
		assertNotEquals(positiveTemperature, response.getTemperature());
		assertEquals(negativeTemperature, response.getTemperature());
		assertEquals(ipaTypeName, response.getTypeName());
	}

	@Test
	public void should_insert_beer_with_inserted_type() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertTrue(toTest.createBeer(skolBeerName, ipaType));
		Optional<BeerVO> response = toTest.getBeer(skolBeerName);
		assertTrue(response.isPresent());
		BeerVO beer = response.get();
		assertEquals(skolBeerName, beer.getName());
		assertEquals(ipaType.getTypeName(), beer.getBeerType().getTypeName());
		assertEquals(positiveTemperature, beer.getBeerType().getTemperature());
	}

	@Test
	public void should_not_insert_beer_without_type_that_doesnt_exists() {
		assertFalse(toTest.createBeer(skolBeerName, ipaType));
		assertTrue(beerRepository.getAll().isEmpty());
	}

	@Test
	public void should_update_beer() {
		toTest.createType(ipaTypeName, positiveTemperature);
		toTest.createType(dunkelTypeName, negativeTemperature);
		assertTrue(toTest.createBeer(skolBeerName, ipaType));
		assertTrue(toTest.updateBeer(skolBeerName, dunkelType));
		Optional<BeerVO> response = toTest.getBeer(skolBeerName);
		assertTrue(response.isPresent());
		BeerVO beer = response.get();
		assertEquals(skolBeerName, beer.getName());
		BeerTypeVO beerType = beer.getBeerType();
		assertEquals(dunkelType.getTypeName(), beerType.getTypeName());
		assertEquals(negativeTemperature, beerType.getTemperature());
		assertNotEquals(positiveTemperature, beerType.getTemperature());
		assertNotEquals(ipaType.getTypeName(), beerType.getTypeName());
	}

	@Test
	public void should_not_update_beer_that_doesnt_exists() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertFalse(toTest.updateBeer(skolBeerName, ipaType));
		assertTrue(toTest.getAllBeers().isEmpty());
	}

	@Test
	public void should_get_beer() {
		toTest.createType(ipaTypeName, positiveTemperature);
		toTest.createBeer(skolBeerName, ipaType);
		Optional<BeerVO> response = toTest.getBeer(skolBeerName);
		assertTrue(response.isPresent());
		BeerVO beer = response.get();
		assertEquals(skolBeerName, beer.getName());
		assertEquals(positiveTemperature, beer.getBeerType().getTemperature());
		assertEquals(ipaType.getTypeName(), beer.getBeerType().getTypeName());

	}

	@Test
	public void should_not_get_beer_that_does_not_exists() {
		assertFalse(toTest.getBeer(skolBeerName).isPresent());
	}

	@Test
	public void should_get_all_beers() {
		toTest.createType(ipaTypeName, positiveTemperature);
		toTest.createBeer(skolBeerName, ipaType);
		toTest.createBeer(heinekenBeerName, ipaType);
		assertEquals(2, beerRepository.getAll().size());
		assertEquals(1, beerTypeRepository.getAll().size());
	}

	@Test
	public void should_get_all_types_and_be_empty() {
		assertTrue(toTest.getAllTypes().isEmpty());
	}

	@Test
	public void should_not_insert_beer_with_empty_name() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertFalse(toTest.createBeer(" ", ipaType));
		assertTrue(toTest.getAllBeers().isEmpty());

	}

	@Test
	public void should_not_insert_beer_with_null_name() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertFalse(toTest.createBeer(null, ipaType));
		assertTrue(toTest.getAllBeers().isEmpty());

	}

	@Test
	public void should_not_insert_beer_with_null_beer_type() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertFalse(toTest.createBeer(skolBeerName, null));
		assertTrue(toTest.getAllBeers().isEmpty());

	}

	@Test
	public void should_not_insert_beer_with_beer_type_name_null() {
		toTest.createType(ipaTypeName, positiveTemperature);
		assertFalse(toTest.createBeer(skolBeerName, emptyNameType));
		assertTrue(toTest.getAllBeers().isEmpty());

	}

	@Test
	public void should_not_insert_beer_type_with_type_name_empty() {
		assertFalse(toTest.createType(" ", positiveTemperature));
		assertTrue(toTest.getAllTypes().isEmpty());
	}

	@Test
	public void should_not_insert_beer_type_with_type_name_null() {
		assertFalse(toTest.createType(null, positiveTemperature));
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_not_insert_beer_type_with_temperature_null() {
		assertFalse(toTest.createType(ipaTypeName, null));
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_not_insert_beer_type_with_max_temperature_null() {
		assertFalse(toTest.createType(ipaTypeName, nullMaxTemperature));
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_not_insert_beer_type_with_min_temperature_null() {
		assertFalse(toTest.createType(ipaTypeName, nullMinTemperature));
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_not_insert_a_beer_type_with_min_temperature_smaller_than_max_temperature() {
		assertFalse(toTest.createType(ipaTypeName, impossibleTemperature));
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_delete_beer() {
		toTest.createType(dunkelTypeName, positiveTemperature);
		toTest.createBeer(skolBeerName, dunkelType);
		assertEquals(1, toTest.getAllBeers().size());
		toTest.deleteBeer(skolBeerName);
		assertTrue(toTest.getAllBeers().isEmpty());
	}

	@Test
	public void should_delete_beer_type() {
		toTest.createType(dunkelTypeName, positiveTemperature);
		assertEquals(1, toTest.getAllTypes().size());
		toTest.deleteBeerType(dunkelTypeName);
		assertTrue(toTest.getAllTypes().isEmpty());

	}

	@Test
	public void should_not_delete_beer_type_that_does_not_exists() {
		toTest.createType(dunkelTypeName, positiveTemperature);
		assertEquals(1, toTest.getAllTypes().size());
		toTest.deleteBeerType(ipaTypeName);
		assertEquals(1, toTest.getAllTypes().size());

	}

	@Test
	public void should_not_delete_beer_that_does_not_exists() {
		toTest.createType(dunkelTypeName, positiveTemperature);
		toTest.createBeer(skolBeerName, dunkelType);
		assertEquals(1, toTest.getAllBeers().size());
		toTest.deleteBeer(heinekenBeerName);
		assertEquals(1, toTest.getAllBeers().size());

	}
}