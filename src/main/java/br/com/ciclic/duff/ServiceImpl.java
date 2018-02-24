package br.com.ciclic.duff;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.inject.Inject;

public class ServiceImpl implements Service {
	private static Controller controller;

	@Inject
	public ServiceImpl(Controller controller) {
		ServiceImpl.controller = controller;
	}

	public static void main(String[] args) {

		path("/beers", () -> {
			get("", (req, resp) -> controller.getAllBeers());
			get("/:beer", (req, resp) -> controller.getBeer(req.params("beer")));
			get("/types", (req, resp) -> controller.getTypes());
			post("", (req, resp) -> controller.createBeer(req.body()));
			put("/:beer", (req, resp) -> controller.createBeer(req.params("beer"), req.body()));
			post("/:beer", (req, resp) -> controller.updateBeer(req.params("beer"), req.body()));

		});
	}
}
