package br.com.ciclic.duff;

import java.util.List;
import java.util.Optional;

public interface Repository<T, U> {
	List<T> getAll();

	Optional<T> get(U key);

	boolean save(T toSave);
}
