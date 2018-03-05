package br.com.ciclic.duff;

import com.datastax.driver.core.Session;

public interface CassandraConnectionHandler {
	Session connect(String contactPoint);

	void close();
}
