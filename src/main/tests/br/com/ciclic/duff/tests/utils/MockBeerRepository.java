package br.com.ciclic.duff.tests.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerVO;

public class MockBeerRepository implements Repository<BeerVO> {
	private List<BeerVO> beerList;

	public MockBeerRepository() {
		this.beerList = new ArrayList<>();
	}

	@Override
	public List<BeerVO> getAll() {
		return beerList;
	}

	@Override
	public Optional<BeerVO> get(String key) {
		return beerList.stream().filter(beer -> beer.getName().equals(key)).findFirst();
	}

	@Override
	public boolean save(String key, BeerVO view) {
		return beerList.add(view);
	}

}
