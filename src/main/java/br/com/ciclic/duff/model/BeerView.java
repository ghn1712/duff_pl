package br.com.ciclic.duff.model;

public class BeerView {
	private final String beerName;
	private final String view;

	public BeerView(String beerName, String view) {
		this.beerName = beerName;
		this.view = view;
	}

	public String getName() {
		return this.beerName;
	}

	public String getBeerView() {
		return this.view;
	}
}
