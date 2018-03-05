package br.com.ciclic.duff;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnectionHandlerImpl implements CassandraConnectionHandler {
	Session session;

	@Override
	public Session connect(String contactPoint) {
		return Cluster.builder().addContactPoint(contactPoint).build().connect();
	}

	@Override
	public void close() {
		this.session.close();

	}

}
