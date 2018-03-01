package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

	List<T> getAll();

	Optional<T> get(String key);

	boolean save(String key, T view);
}
