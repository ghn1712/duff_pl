package br.com.ciclic.duff.tests.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerVO;

public class MockBeerRepository implements Repository<BeerVO> {
	private Map<String, BeerVO> beerMap;

	public MockBeerRepository() {
		this.beerMap = new HashMap<>();
	}

	@Override
	public List<BeerVO> getAll() {
		return new ArrayList<>(beerMap.values());
	}

	@Override
	public Optional<BeerVO> get(String key) {
		BeerVO beer = beerMap.get(key);
		if (beer != null)
			return Optional.of(beer);

		return Optional.empty();
	}

	@Override
	public boolean save(String key, BeerVO view) {
		beerMap.put(key, view);
		return true;
	}

	@Override
	public void delete(String key) {
		beerMap.remove(key);
	}

}
