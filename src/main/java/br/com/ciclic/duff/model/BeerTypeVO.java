package br.com.ciclic.duff.model;

import com.google.common.base.Objects;

public class BeerTypeVO {
	private String typeName;
	private TemperatureVO temperature;

	public BeerTypeVO(String typeName, TemperatureVO beerTypeView) {
		this.typeName = typeName;
		this.temperature = new TemperatureVO(beerTypeView.getMax(), beerTypeView.getMin());
	}

	public BeerTypeVO(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public TemperatureVO getTemperature() {
		return temperature;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BeerTypeVO))
			return false;
		if (other == this)
			return true;
		BeerTypeVO toCompare = (BeerTypeVO) other;
		return typeName.equals(toCompare.getTypeName()) && temperature.equals(toCompare.getTemperature());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.typeName, this.temperature);
	}

}
