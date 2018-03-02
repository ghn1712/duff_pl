package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;
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
import br.com.ciclic.duff.tests.utils.MockBeerRepository;
import br.com.ciclic.duff.tests.utils.MockBeerTypeRepository;

public class BeerControllerTest {

	BeerController toTest;
	Repository<BeerVO> beerRepository;
	Repository<BeerTypeVO> beerTypeRepository;
	JSONObject typeToInsert;
	Gson serializer = new Gson();

	@Before
	public void setup() {
		beerRepository = new MockBeerRepository();
		beerTypeRepository = new MockBeerTypeRepository();
		toTest = new BeerControllerImpl(beerRepository, beerTypeRepository);
		typeToInsert = new JSONObject("{\"typeName\": \"ipa\", \"temperature\": {\"max\": 10, \"min\": 1}}");
	}

	@Test
	public void should_insert_a_type() {
		BeerTypeVO type = serializer.fromJson(typeToInsert.toString(), BeerTypeVO.class);
		assertTrue(toTest.createType("ipa", type));
		Optional<BeerTypeVO> response = beerTypeRepository.get("ipa");
		assertTrue(response.isPresent());
		assertEquals(type, response.get());
	}

	@Test
	public void should_not_update_type_that_doesnt_exists() {

	}

	@Test
	public void should_update_type() {

	}

	@Test
	public void should_insert_beer_with_inserted_type() {

	}

	@Test
	public void should_not_insert_beer_without_type_that_doesnt_exists() {

	}

	@Test
	public void should_update_beer() {

	}

	@Test
	public void should_not_update_beer_that_doesnt_exists() {

	}

	@Test
	public void should_get_beer() {

	}

	@Test
	public void should_not_get_beer_that_does_not_exists() {

	}

	@Test
	public void should_get_all_beers() {

	}

	@Test
	public void should_get_all_types() {

	}

	@Test
	public void should_not_insert_beer_with_empty_name() {

	}

	@Test
	public void should_not_insert_beer_with_null_name() {

	}

	@Test
	public void should_not_insert_beer_with_null_beer_type() {

	}

	@Test
	public void should_not_insert_beer_with_beer_type_name_null() {

	}

	@Test
	public void should_not_insert_beer_with_beer_type_temperature_null() {

	}

	@Test
	public void should_not_insert_beer_with_beer_type_max_temperature_null() {

	}

	@Test
	public void should_not_insert_beer_with_beer_type_min_temperature_null() {

	}

	@Test
	public void should_not_insert_beer_with_beer_type_name_empty() {

	}
}