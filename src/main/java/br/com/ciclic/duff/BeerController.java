package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;

public interface BeerController {

	List<BeerVO> getAllBeers();

	Optional<BeerVO> getBeer(String beerName);

	List<BeerTypeVO> getTypes();

	boolean createBeer(String beerName, BeerVO beerInfo);

	boolean updateBeer(String beerName, BeerVO beerInfo);

	boolean createType(String beerType, BeerTypeVO beerTemperature);

	boolean updateType(String beerType, BeerTypeVO beerTemperature);

}
