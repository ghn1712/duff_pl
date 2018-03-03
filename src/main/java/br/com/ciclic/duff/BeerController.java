package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;

public interface BeerController {

	List<BeerVO> getAllBeers();

	Optional<BeerVO> getBeer(String beerName);

	List<BeerTypeVO> getAllTypes();

	boolean createBeer(String beerName, BeerVO beerInfo);

	boolean updateBeer(String beerName, BeerVO beerInfo);

	boolean createType(String beerTypeName, BeerTypeVO beerTypeView);

	boolean updateType(String beerTypeName, BeerTypeVO beerTypeView);

	Optional<BeerTypeVO> getType(String beerTypeName);

	void deleteBeer(String beerName);

	void deleteBeerType(String beerTypeName);

}
