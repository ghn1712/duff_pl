package br.com.ciclic.duff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.inject.Inject;

import br.com.ciclic.duff.model.BeerVO;

public class BeerRepository implements Repository<BeerVO> {
	Session session;
	CassandraConnectionHandler cassandraConnectionHandler;
	private static final String KEYSPACE;
	private static final String TABLE;
	private static final String TABLEKEY;
	private static final String TABLEVALUE;
	private static final Gson serializer;

	static {
		KEYSPACE = "beerKeyspace";
		TABLE = "beers";
		TABLEKEY = "beerName";
		TABLEVALUE = "beerView";
		serializer = new Gson();
	}

	@Inject
	public BeerRepository(CassandraConnectionHandler cassandraConnectionHandler) {
		this.cassandraConnectionHandler = cassandraConnectionHandler;
		session = cassandraConnectionHandler.connect("127.0.0.1");
	}

	@Override
	public List<BeerVO> getAll() {
		List<BeerVO> beerViewList = new ArrayList<>();
		session.execute(getSelectAllPreparedStatement().bind())
				.forEach(row -> beerViewList.add(serializer.fromJson(row.getString(TABLEVALUE), BeerVO.class)));
		return beerViewList;
	}

	@Override
	public Optional<BeerVO> get(String key) {
		Row row = session.execute(getSelectPreparedStatement().bind(key)).one();
		if (row != null) {
			return Optional.of(serializer.fromJson(row.getString(TABLEVALUE), BeerVO.class));
		}
		return Optional.empty();
	}

	@Override
	public boolean save(BeerVO view) {
		try {
			session.execute(getInsertPreparedStatement().bind(view.getName(), serializer.toJson(view)));
		} catch (JsonParseException e) {
			return false;
		}
		return true;
	}

	@Override
	public void delete(String key) {
		session.execute(getDeletePreparedStatement().bind(key));
	}
	
	@Override
	public void truncate() {
		session.execute(QueryBuilder.truncate(KEYSPACE, TABLE));
	}

	private PreparedStatement getDeletePreparedStatement() {
		return session
				.prepare(new SimpleStatement("DELETE FROM " + KEYSPACE + "." + TABLE + " WHERE " + TABLEKEY + " = ?"));
	}

	private PreparedStatement getSelectPreparedStatement() {
		return session.prepare(new SimpleStatement("SELECT " + TABLEKEY + "," + TABLEVALUE + " FROM " + KEYSPACE + "."
				+ TABLE + " WHERE " + TABLEKEY + " = ?"));
	}

	private PreparedStatement getSelectAllPreparedStatement() {
		return session.prepare(
				new SimpleStatement("SELECT " + TABLEKEY + "," + TABLEVALUE + " FROM " + KEYSPACE + "." + TABLE));
	}

	private PreparedStatement getInsertPreparedStatement() {
		return session.prepare(new SimpleStatement(
				"INSERT INTO " + KEYSPACE + "." + TABLE + "(" + TABLEKEY + "," + TABLEVALUE + ")" + "VALUES (?, ?)"));
	}
}
