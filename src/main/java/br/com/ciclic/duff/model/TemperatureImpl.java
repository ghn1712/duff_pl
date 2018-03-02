package br.com.ciclic.duff.model;

public class TemperatureImpl implements Temperature {
	Integer min;
	Integer max;

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public void setTemperature(int min, int max) {
		this.min = min;
		this.max = max;
	}

}
