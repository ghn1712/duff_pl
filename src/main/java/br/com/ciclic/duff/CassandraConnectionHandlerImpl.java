package br.com.ciclic.duff;

import com.datastax.driver.core.Session;

public class CassandraConnectionHandlerImpl implements CassandraConnectionHandler {
	Session session;

	public CassandraConnectionHandlerImpl() {
		this.session = connect("127.0.0.1");
	}

	@Override
	public void close() {
		this.session.close();

	}

}
