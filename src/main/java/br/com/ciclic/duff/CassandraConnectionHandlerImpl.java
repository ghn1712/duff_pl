package br.com.ciclic.duff;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnectionHandlerImpl implements CassandraConnectionHandler {
	Session session;

	@Override
	public Session connect(String contactPoint) {
		if(this.session == null) {
			this.session = Cluster.builder().addContactPoint(contactPoint).build().connect();
		}
		return this.session;
	}

	@Override
	public void close() {
		if(this.session != null) 
			this.session.close();
	}

}
