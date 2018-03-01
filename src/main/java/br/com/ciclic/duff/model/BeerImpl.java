package br.com.ciclic.duff.model;

public class BeerImpl implements Beer {
	String name;
	BeerType beerType;

	public BeerImpl() {

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BeerType getBeerType() {
		return this.beerType;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setBeerType(BeerType beerType) {
		this.beerType = beerType;

	}

}
