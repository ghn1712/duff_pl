package br.com.ciclic.duff.tests.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerTypeVO;

public class MockBeerTypeRepository implements Repository<BeerTypeVO> {
	private List<BeerTypeVO> beerTypeList = new ArrayList<>();

	public MockBeerTypeRepository() {
		this.beerTypeList = new ArrayList<>();
	}

	@Override
	public List<BeerTypeVO> getAll() {
		return beerTypeList;
	}

	@Override
	public Optional<BeerTypeVO> get(String key) {
		return beerTypeList.stream().filter(beerType -> beerType.getTypeName().equals(key)).findFirst();
	}

	@Override
	public boolean save(String key, BeerTypeVO view) {
		beerTypeList.add(view);
		return true;
	}

}
