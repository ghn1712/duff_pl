package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import br.com.ciclic.duff.model.Beer;
import br.com.ciclic.duff.model.BeerType;

public class BeerControlerImpl implements BeerController {
	Repository<Beer, String> beerRepository;
	Repository<BeerType, String> beerTypeRepository;
	Gson gson;

	public BeerControlerImpl(Repository<Beer, String> beerRepository, Repository<BeerType, String> beerTypeRepository) {
		this.beerRepository = beerRepository;
		this.beerTypeRepository = beerTypeRepository;
		gson = new Gson();
	}

	@Override
	public List<Beer> getAllBeers() {
		return beerRepository.getAll();
	}

	@Override
	public Optional<Beer> getBeer(String beerName) {
		return beerRepository.get(beerName);
	}

	@Override
	public List<BeerType> getTypes() {
		return beerTypeRepository.getAll();
	}

	@Override
	public boolean createBeer(String beerName, String beerInfo) {
		return beerRepository.save(gson.fromJson(beerInfo, Beer.class));
	}

	@Override
	public boolean updateBeer(String beerName, String beerInfo) {
		if (getBeer(beerName).isPresent()) {
			return beerRepository.save(gson.fromJson(beerInfo, Beer.class));
		}
		return false;
	}

}
