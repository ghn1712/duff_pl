package br.com.ciclic.duff.model;

import com.google.common.base.Objects;

public class BeerTypeVO {
	private String typeName;
	private TemperatureVO temperature;

	public String getTypeName() {
		return typeName;
	}

	public TemperatureVO getTemperature() {
		return temperature;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != BeerTypeVO.class)
			return false;
		if (other == this)
			return true;
		BeerTypeVO toCompare = (BeerTypeVO) other;
		return typeName.equals(toCompare.getTypeName());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.typeName);
	}

}
