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
		if (!checkBeerVO(beerView))
			return false;
		return beerRepository.save(beerName, beerView);

	}

	@Override
	public boolean updateBeer(String beerName, BeerVO beerView) {
		if (getBeer(beerName).isPresent()) {
			return createBeer(beerName, beerView);
		}
		return false;
	}

	@Override
	public boolean createType(String beerTypeName, BeerTypeVO beerTypeView) {
		if (!checkBeerTypeVO(beerTypeView))
			return false;
		return beerTypeRepository.save(beerTypeName, beerTypeView);
	}

	@Override
	public boolean updateType(String beerTypeName, BeerTypeVO beerTypeView) {
		if (getType(beerTypeName).isPresent()) {
			return createType(beerTypeName, beerTypeView);
		}
		return false;
	}

	@Override
	public Optional<BeerTypeVO> getType(String beerTypeName) {
		return beerTypeRepository.get(beerTypeName);
	}

	private static boolean checkBeerVO(BeerVO beerView) {
		return beerView.getName() != null && beerView.getBeerType() != null
				&& beerView.getBeerType().getTypeName() != null && beerView.getBeerType().getTemperature() != null
				&& beerView.getBeerType().getTemperature().getMin() != null
				&& beerView.getBeerType().getTemperature().getMax() != null && !beerView.getName().isEmpty()
				&& !beerView.getBeerType().getTypeName().isEmpty();

	}

	private static boolean checkBeerTypeVO(BeerTypeVO beerType) {
		return beerType.getTypeName() != null && beerType.getTemperature() != null
				&& beerType.getTemperature().getMax() != null && beerType.getTemperature().getMin() != null
				&& !beerType.getTypeName().isEmpty();
	}
}
