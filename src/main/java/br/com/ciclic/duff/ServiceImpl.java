package br.com.ciclic.duff;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.inject.Inject;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;
import br.com.ciclic.duff.model.TemperatureVO;

public class ServiceImpl implements Service {

	private final BeerController controller;
	private static final String BEER_PATH_URI;
	private static final String INVALID_JSON_BODY;
	private static final Gson serializer;
	private static final String BEER_TYPE_PATH_URI;
	private static final String BEER_TYPE_PATH_PARAM;
	private static final String BEER_PATH_PARAM;

	static {
		serializer = new Gson();
		BEER_PATH_URI = "/:beer";
		INVALID_JSON_BODY = "{\"message\": \"invalid json\"}";
		BEER_TYPE_PATH_URI = "/types/:beerType";
		BEER_PATH_PARAM = "beer";
		BEER_TYPE_PATH_PARAM = "beerType";
	}

	@Inject
	public ServiceImpl(BeerController controller) {
		this.controller = controller;
	}

	private static Optional<TemperatureVO> getTemperatureVO(String view) {

		try {
			JSONObject json = new JSONObject(view);
			TemperatureVO temperatureVO = serializer.fromJson(json.toString(), TemperatureVO.class);
			return Optional.of(temperatureVO);
		} catch (JsonParseException | JSONException e) {
			return Optional.empty();
		}
	}

	private static Optional<BeerTypeVO> getBeerTypeVO(String view) {
		try {
			JSONObject json = new JSONObject(view);
			BeerTypeVO beerTypeVO = serializer.fromJson(json.toString(), BeerTypeVO.class);
			return Optional.of(beerTypeVO);
		} catch (JSONException | JsonParseException e) {
			return Optional.empty();
		}
	}

	public void start() {
		path("/beers", () -> {

			get("", (req, resp) -> {
				List<BeerVO> types = controller.getAllBeers();
				if (types.isEmpty()) {
					resp.status(HttpStatus.NO_CONTENT_204);
					resp.body("");
				} else {
					List<String> response = new ArrayList<>();
					types.forEach(type -> response.add(serializer.toJson(type)));
					resp.status(HttpStatus.OK_200);
					resp.body(new JSONArray(response).toString());
				}
				return resp;
			});

			get(BEER_PATH_URI, (req, resp) -> {
				Optional<BeerVO> response = controller.getBeer(req.params(BEER_PATH_PARAM));
				if (response.isPresent()) {
					resp.status(HttpStatus.OK_200);
					resp.body(serializer.toJson(response.get()));
				} else {
					resp.status(HttpStatus.NOT_FOUND_404);
					resp.body("");
				}
				return resp;
			});

			get("/types", (req, resp) -> {
				List<BeerTypeVO> types = controller.getAllTypes();
				if (types.isEmpty()) {
					resp.status(HttpStatus.NO_CONTENT_204);
					resp.body("");
				} else {
					List<String> response = new ArrayList<>();
					types.forEach(type -> response.add(serializer.toJson(type)));
					resp.status(HttpStatus.OK_200);
					resp.body(new JSONArray(response).toString());
				}
				return resp;
			});

			get(BEER_TYPE_PATH_URI, (req, resp) -> {
				Optional<BeerTypeVO> response = controller.getType(req.params(BEER_TYPE_PATH_PARAM));
				if (response.isPresent()) {
					resp.status(HttpStatus.OK_200);
					resp.body(serializer.toJson(response.get()));
				} else {
					resp.status(HttpStatus.NOT_FOUND_404);
					resp.body("");
				}
				return resp;
			});

			put(BEER_PATH_URI, (req, resp) -> {

				String beerName = req.params(BEER_PATH_PARAM);
				Optional<BeerTypeVO> beerTypeVO = getBeerTypeVO(req.body());
				if (beerTypeVO.isPresent() && controller.createBeer(beerName, beerTypeVO.get())) {
					resp.status(HttpStatus.NO_CONTENT_204);
					resp.body("");
					return resp;
				}
				resp.status(HttpStatus.BAD_REQUEST_400);
				resp.body(INVALID_JSON_BODY);
				return resp;

			});

			post(BEER_PATH_URI, (req, resp) -> {

				String beerName = req.params(BEER_PATH_PARAM);
				Optional<BeerTypeVO> beerTypeVO = getBeerTypeVO(req.body());
				if (beerTypeVO.isPresent()) {
					if (controller.updateBeer(beerName, beerTypeVO.get())) {
						resp.status(HttpStatus.NO_CONTENT_204);
					} else {
						resp.status(HttpStatus.FORBIDDEN_403);
					}
					resp.body("");
					return resp;
				}
				resp.status(HttpStatus.BAD_REQUEST_400);
				resp.body(INVALID_JSON_BODY);
				return resp;
			});

			delete(BEER_PATH_URI, (req, resp) -> {
				controller.deleteBeer(req.params(BEER_PATH_PARAM));
				resp.status(HttpStatus.NO_CONTENT_204);
				return resp;
			});

			put(BEER_TYPE_PATH_URI, (req, resp) -> {

				String beerTypeName = req.params(BEER_TYPE_PATH_PARAM);
				Optional<TemperatureVO> temperatureVO = getTemperatureVO(req.body());
				if (temperatureVO.isPresent() && controller.createType(beerTypeName, temperatureVO.get())) {
					resp.status(HttpStatus.NO_CONTENT_204);
					resp.body("");
					return resp;
				}
				resp.status(HttpStatus.BAD_REQUEST_400);
				resp.body(INVALID_JSON_BODY);
				return resp;

			});

			post(BEER_TYPE_PATH_URI, (req, resp) -> {

				String beerTypeName = req.params(BEER_TYPE_PATH_PARAM);
				Optional<TemperatureVO> temperatureVO = getTemperatureVO(req.body());
				if (temperatureVO.isPresent()) {
					if (controller.updateType(beerTypeName, temperatureVO.get())) {
						resp.status(HttpStatus.NO_CONTENT_204);
					} else {
						resp.status(HttpStatus.FORBIDDEN_403);
					}
					resp.body("");
					return resp;
				}
				resp.status(HttpStatus.BAD_REQUEST_400);
				resp.body(INVALID_JSON_BODY);
				return resp;
			});

			delete(BEER_TYPE_PATH_URI, (req, resp) -> {
				controller.deleteBeerType(req.params(BEER_TYPE_PATH_PARAM));
				resp.status(HttpStatus.NO_CONTENT_204);
				return resp;
			});

		});
	}

}
