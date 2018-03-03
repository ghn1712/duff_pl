package br.com.ciclic.duff.model;

import com.google.common.base.Objects;

public class TemperatureVO {
	private Integer min;
	private Integer max;

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof TemperatureVO))
			return false;
		if (arg0 == this)
			return true;
		TemperatureVO toCompare = (TemperatureVO) arg0;
		return this.min.equals(toCompare.getMin()) && this.max.equals(toCompare.getMax());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.min, this.max);
	}

}
