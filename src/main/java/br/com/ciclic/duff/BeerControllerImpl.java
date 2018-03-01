package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import com.google.inject.Inject;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;

public class BeerControllerImpl implements BeerController {
	Repository<BeerVO> beerRepository;
	Repository<BeerTypeVO> beerTypeRepository;

	@Inject
	public BeerControllerImpl(Repository<BeerVO> beerRepository, Repository<BeerTypeVO> beerTypeRepository) {
		this.beerRepository = beerRepository;
		this.beerTypeRepository = beerTypeRepository;
	}

	@Override
	public List<BeerVO> getAllBeers() {
		return beerRepository.getAll();
	}

	@Override
	public Optional<BeerVO> getBeer(String beerName) {
		return beerRepository.get(beerName);
	}

	@Override
	public List<BeerTypeVO> getTypes() {
		return beerTypeRepository.getAll();
	}

	@Override
	public boolean createBeer(String beerName, BeerVO beerView) {
		return beerRepository.save(beerName, beerView);

	}

	@Override
	public boolean updateBeer(String beerName, BeerVO beerType) {
		if (getBeer(beerName).isPresent()) {
			return createBeer(beerName, beerType);
		}
		return false;
	}

	@Override
	public boolean createType(String beerType, BeerTypeVO beerTemperature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateType(String beerType, BeerTypeVO beerTemperature) {
		// TODO Auto-generated method stub
		return false;
	}

}
