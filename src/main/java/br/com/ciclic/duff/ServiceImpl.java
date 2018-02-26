package br.com.ciclic.duff;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.inject.Inject;

public class ServiceImpl implements Service {
	private BeerController controller;
	private static final String BEER_PATH_PARAM = "/:beer";

	@Inject
	public ServiceImpl(BeerController controller) {
		this.controller = controller;
		start();
	}

	@Override
	public void start() {
		path("/beers", () -> {
			get("", (req, resp) -> controller.getAllBeers());
			get(BEER_PATH_PARAM, (req, resp) -> controller.getBeer(req.params("beer")));
			get("/types", (req, resp) -> controller.getTypes());
			put(BEER_PATH_PARAM, (req, resp) -> controller.createBeer(req.params("beer"), req.body()));
			post(BEER_PATH_PARAM, (req, resp) -> controller.updateBeer(req.params("beer"), req.body()));

		});

	}

}
