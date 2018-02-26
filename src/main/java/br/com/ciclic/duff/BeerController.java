package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

import br.com.ciclic.duff.model.Beer;
import br.com.ciclic.duff.model.BeerType;

public interface BeerController {

	List<Beer> getAllBeers();

	Optional<Beer> getBeer(String beerName);

	List<BeerType> getTypes();

	boolean createBeer(String beerName, String beerInfo);

	boolean updateBeer(String beerName, String beerInfo);

}
