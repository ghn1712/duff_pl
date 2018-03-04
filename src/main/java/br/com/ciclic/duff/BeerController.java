package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;

public interface BeerController {

	List<BeerVO> getAllBeers();

	Optional<BeerVO> getBeer(String beerName);

	List<BeerTypeVO> getAllTypes();

	boolean createBeer(String beerName, BeerTypeVO beerView);

	boolean updateBeer(String beerName, BeerTypeVO beerView);

	boolean createType(String beerTypeName, TemperatureVO beerTypeView);

	boolean updateType(String beerTypeName, TemperatureVO beerTypeView);

	Optional<BeerTypeVO> getType(String beerTypeName);

	void deleteBeer(String beerName);

	void deleteBeerType(String beerTypeName);

}
