package br.com.ciclic.duff;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;

public class Service {

	private static BeerController controller;
	private static final String BEER_PATH_PARAM;
	private static final String INVALID_JSON_BODY;
	private static final Gson serializer;
	private static final String BEER_TYPE_PATH_PARAM;

	static {
		serializer = new Gson();
		BEER_PATH_PARAM = "/:beer";
		INVALID_JSON_BODY = "{\"message\": \"invalid json\"}";
		BEER_TYPE_PATH_PARAM = "/:beerType";
	}

	private static Optional<BeerVO> getBeerVO(String name, String view) {

		try {
			JSONObject json = new JSONObject(view);
			json.putOnce("name", name);
			BeerVO beerVO = serializer.fromJson(json.toString(), BeerVO.class);
			return Optional.of(beerVO);
		} catch (JsonParseException | JSONException e) {
			return Optional.empty();
		}
	}

	private static Optional<BeerTypeVO> getBeerTypeVO(String typeName, String view) {
		try {
			JSONObject json = new JSONObject(view);
			json.putOnce("typeName", typeName);
			BeerTypeVO beerTypeVO = serializer.fromJson(json.toString(), BeerTypeVO.class);
			return Optional.of(beerTypeVO);
		} catch (JSONException | JsonParseException e) {
			return Optional.empty();
		}
	}

	public static void main(String[] args) {

		path("/beers", () -> {
			get("", (req, resp) -> controller.getAllBeers());
			get(BEER_PATH_PARAM, (req, resp) -> controller.getBeer(req.params("beer")));
			get("/types", (req, resp) -> controller.getAllTypes());
			put(BEER_PATH_PARAM, (req, resp) -> {

				String beerName = req.params("beer");
				Optional<BeerVO> beerVO = getBeerVO(beerName, req.body());
				if (beerVO.isPresent()) {
					return controller.createBeer(beerName, beerVO.get());
				}
				resp.status(400);
				resp.body(INVALID_JSON_BODY);
				return resp;

			});
			post(BEER_PATH_PARAM, (req, resp) -> {

				String beerName = req.params("beer");
				Optional<BeerVO> beerVO = getBeerVO(beerName, req.body());
				if (beerVO.isPresent()) {
					return controller.updateBeer(beerName, beerVO.get());
				}
				resp.status(400);
				resp.body(INVALID_JSON_BODY);
				return resp;
			});
			put(BEER_TYPE_PATH_PARAM, (req, resp) -> {

				String beerTypeName = req.params("beerType");
				Optional<BeerTypeVO> beerTypeVO = getBeerTypeVO(beerTypeName, req.body());
				if (beerTypeVO.isPresent()) {
					return controller.createType(beerTypeName, beerTypeVO.get());
				}
				resp.status(400);
				resp.body(INVALID_JSON_BODY);
				return resp;

			});
			post(BEER_TYPE_PATH_PARAM, (req, resp) -> {

				String beerTypeName = req.params("beerType");
				Optional<BeerTypeVO> beerTypeVO = getBeerTypeVO(beerTypeName, req.body());
				if (beerTypeVO.isPresent()) {
					return controller.updateType(beerTypeName, beerTypeVO.get());
				}
				resp.status(400);
				resp.body(INVALID_JSON_BODY);
				return resp;
			});

		});

		after((req, resp) -> resp.type("application/json"));
	}

}
