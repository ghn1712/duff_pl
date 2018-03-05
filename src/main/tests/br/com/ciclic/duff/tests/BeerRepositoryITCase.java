package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.com.ciclic.duff.BeerRepository;
import br.com.ciclic.duff.CassandraConnectionHandlerImpl;
import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;

public class BeerRepositoryITCase {
	Repository<BeerVO> beerRepository;

	@Before
	public void setup() {
		beerRepository = new BeerRepository(new CassandraConnectionHandlerImpl());
	}

	@Test
	public void should_save_beer_vo() {
		BeerVO beer = new BeerVO("skol", new BeerTypeVO("ipa", new TemperatureVO(0, -1)));
		beerRepository.save(beer);
		assertEquals(1, beerRepository.getAll().size());
		assertEquals(beer, beerRepository.get("skol").get());
	}

	@Test
	public void should_delete_beer_vo() {
		BeerVO beer = new BeerVO("skol", new BeerTypeVO("ipa", new TemperatureVO(0, -1)));
		beerRepository.save(beer);
		assertEquals(1, beerRepository.getAll().size());
		assertEquals(beer, beerRepository.get("skol").get());
		beerRepository.delete("skol");
		assertEquals(0, beerRepository.getAll().size());
		assertFalse(beerRepository.get("skol").isPresent());

	}
}
