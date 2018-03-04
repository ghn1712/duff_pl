package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import com.google.inject.Inject;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;

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
	public List<BeerTypeVO> getAllTypes() {
		return beerTypeRepository.getAll();
	}

	@Override
	public boolean createBeer(String beerName, BeerTypeVO beerView) {
		if (!checkBeer(beerName, beerView))
			return false;
		Optional<BeerTypeVO> type = getType(beerView.getTypeName());
		if (type.isPresent()) {
			return beerRepository.save(beerName, new BeerVO(beerName, type.get()));
		}
		return false;

	}

	@Override
	public boolean updateBeer(String beerName, BeerTypeVO beerView) {
		if (getBeer(beerName).isPresent()) {
			return createBeer(beerName, beerView);
		}
		return false;
	}

	@Override
	public boolean createType(String beerTypeName, TemperatureVO beerTypeView) {
		if (!checkBeerType(beerTypeName, beerTypeView))
			return false;
		return beerTypeRepository.save(beerTypeName, new BeerTypeVO(beerTypeName, beerTypeView));
	}

	@Override
	public boolean updateType(String beerTypeName, TemperatureVO beerTypeView) {
		if (getType(beerTypeName).isPresent()) {
			return createType(beerTypeName, beerTypeView);
		}
		return false;
	}

	@Override
	public Optional<BeerTypeVO> getType(String beerTypeName) {
		return beerTypeRepository.get(beerTypeName);
	}

	@Override
	public void deleteBeer(String beerName) {
		beerRepository.delete(beerName);
	}

	@Override
	public void deleteBeerType(String beerTypeName) {
		beerTypeRepository.delete(beerTypeName);
	}

	private static boolean checkBeer(String beerName, BeerTypeVO beerView) {
		return beerName != null && !beerName.trim().isEmpty() && beerView != null && beerView.getTypeName() != null
				&& !beerView.getTypeName().trim().isEmpty();

	}

	private static boolean checkBeerType(String beerTypeName, TemperatureVO beerType) {
		return beerTypeName != null && beerType != null && beerType.getMax() != null && beerType.getMin() != null
				&& !beerTypeName.trim().isEmpty() && beerType.getMin() <= beerType.getMax();
	}

}
