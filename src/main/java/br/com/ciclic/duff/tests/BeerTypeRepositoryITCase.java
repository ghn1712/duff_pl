package br.com.ciclic.duff.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.com.ciclic.duff.BeerTypeRepository;
import br.com.ciclic.duff.CassandraConnectionHandlerImpl;
import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.TemperatureVO;

public class BeerTypeRepositoryITCase {
	Repository<BeerTypeVO> beerTypeRepository;
	BeerTypeVO beerType;
	String key;

	@Before
	public void setup() {
		beerTypeRepository = new BeerTypeRepository(new CassandraConnectionHandlerImpl());
		key = "ipa";
		beerType = new BeerTypeVO(key, new TemperatureVO(0, -1));
		beerTypeRepository.truncate();
	}

	@Test
	public void should_save_beer_vo() {
		beerTypeRepository.save(beerType);
		assertEquals(1, beerTypeRepository.getAll().size());
		assertEquals(beerType, beerTypeRepository.get(key).get());
		beerTypeRepository.truncate();
	}

	@Test
	public void should_delete_beer_vo() {
		beerTypeRepository.save(beerType);
		assertEquals(1, beerTypeRepository.getAll().size());
		assertEquals(beerType, beerTypeRepository.get(key).get());
		beerTypeRepository.delete(key);
		assertEquals(0, beerTypeRepository.getAll().size());
		assertFalse(beerTypeRepository.get(key).isPresent());
		beerTypeRepository.truncate();
	}
}
