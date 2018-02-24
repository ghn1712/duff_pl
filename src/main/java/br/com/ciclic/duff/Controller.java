package br.com.ciclic.duff;

import java.util.List;

import br.com.ciclic.duff.model.Beer;
import br.com.ciclic.duff.model.BeerType;

public interface Controller {

	List<Beer> getAllBeers();

	Beer getBeer(String params);

	List<BeerType> getTypes();

	boolean createBeer(String body);

	boolean createBeer(String params, String body);

	boolean updateBeer(String params, String body);

}
