package br.com.ciclic.duff.model;

import java.util.Objects;

public class BeerVO {
	private String name;
	private BeerTypeVO beerType;

	public BeerVO(String name, BeerTypeVO beerType) {
		this.name = name;
		this.beerType = new BeerTypeVO(beerType.getTypeName(), beerType.getTemperature());
	}

	public String getName() {
		return name;
	}

	public BeerTypeVO getBeerType() {
		return beerType;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == this)
			return true;
		if (!(arg0 instanceof BeerVO))
			return false;
		BeerVO toCompare = (BeerVO) arg0;
		return this.name.equals(toCompare.getName())
				&& this.beerType.getTypeName().equals(toCompare.getBeerType().getTypeName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, beerType);
	}

}
