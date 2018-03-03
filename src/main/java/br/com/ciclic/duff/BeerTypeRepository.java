package br.com.ciclic.duff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.google.gson.Gson;
import com.google.inject.Inject;

import br.com.ciclic.duff.model.BeerTypeVO;

public class BeerTypeRepository implements Repository<BeerTypeVO> {
	private static final String KEYSPACE;
	private static final String TABLE;
	private static final String TABLEKEY;
	private static final String TABLEVIEW;
	private static final Gson serializer;
	final Session session;
	CassandraConnectionHandler cassandraConnectionHandler;

	static {
		KEYSPACE = "beerKeyspace";
		TABLE = "beerType";
		TABLEKEY = "typeName";
		TABLEVIEW = "view";
		serializer = new Gson();
	}

	@Inject
	public BeerTypeRepository(CassandraConnectionHandler cassandraConnectionHandler) {
		this.cassandraConnectionHandler = cassandraConnectionHandler;
		session = cassandraConnectionHandler.connect("127.0.0.1");
	}

	@Override
	public List<BeerTypeVO> getAll() {
		List<BeerTypeVO> values = new ArrayList<>();
		session.execute(getSelectAllPreparedStatement().bind())
				.forEach(row -> values.add(serializer.fromJson(row.getString(TABLEVIEW), BeerTypeVO.class)));
		;
		return values;
	}

	@Override
	public Optional<BeerTypeVO> get(String key) {
		Row row = session.execute(getSelectPreparedStatement().bind(key)).one();
		if (row != null) {
			return Optional.of(serializer.fromJson(row.getString(TABLEVIEW), BeerTypeVO.class));
		}
		return Optional.empty();
	}

	@Override
	public boolean save(String key, BeerTypeVO view) {
		try {
			session.execute(getInsertPreparedStatement().bind(key, serializer.toJson(view)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void delete(String key) {
		session.execute(getDeletePreparedStatement().bind(key));

	}

	private PreparedStatement getDeletePreparedStatement() {
		return session
				.prepare(new SimpleStatement("DELETE FROM" + KEYSPACE + "." + TABLE + " WHERE " + TABLEKEY + " = ?"));
	}

	private PreparedStatement getInsertPreparedStatement() {
		return session.prepare(new SimpleStatement(
				"INSERT INTO " + KEYSPACE + "." + TABLE + "(" + TABLEKEY + "," + TABLEVIEW + ")" + "VALUES ?, ?"));
	}

	private PreparedStatement getSelectPreparedStatement() {
		return session.prepare(new SimpleStatement("SELECT " + TABLEKEY + "," + TABLEVIEW + " FROM " + KEYSPACE + "."
				+ TABLE + " WHERE " + TABLEKEY + " = ?"));
	}

	private PreparedStatement getSelectAllPreparedStatement() {
		return session.prepare(
				new SimpleStatement("SELECT " + TABLEKEY + "," + TABLEVIEW + " FROM " + KEYSPACE + "." + TABLE));
	}

}
