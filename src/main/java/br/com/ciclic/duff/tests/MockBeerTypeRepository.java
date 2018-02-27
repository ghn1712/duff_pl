package br.com.ciclic.duff.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.ciclic.duff.Repository;
import br.com.ciclic.duff.model.BeerTypeVO;

public class MockBeerTypeRepository implements Repository<BeerTypeVO> {
	private Map<String, BeerTypeVO> beerTypeMap;

	public MockBeerTypeRepository() {
		this.beerTypeMap = new HashMap<>();
	}

	@Override
	public List<BeerTypeVO> getAll() {
		return new ArrayList<>(beerTypeMap.values());
	}

	@Override
	public Optional<BeerTypeVO> get(String key) {
		BeerTypeVO beerTypeVO = beerTypeMap.get(key);
		if (beerTypeVO != null)
			return Optional.of(beerTypeMap.get(key));
		return Optional.empty();

	}

	@Override
	public boolean save(BeerTypeVO view) {
		beerTypeMap.put(view.getTypeName(), view);
		return true;
	}

	@Override
	public void delete(String key) {
		beerTypeMap.remove(key);

	}

	@Override
	public void truncate() {
		beerTypeMap.clear();		
	}

}
