package br.com.ciclic.duff;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public interface CassandraConnectionHandler {
	default Session connect(String contactPoint) {
		return Cluster.builder().addContactPoint(contactPoint).build().connect();
	}

	void close();
}
