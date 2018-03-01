package br.com.ciclic.duff.model;

public class TemperatureImpl implements Temperature {
	int min;
	int max;

	@Override
	public double getMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTemperature(int min, int max) {
		this.min = min;
		this.max = max;
	}

}
