package br.com.ciclic.duff;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import br.com.ciclic.duff.model.BeerTypeVO;
import br.com.ciclic.duff.model.BeerVO;

public class DuffModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BeerController.class).to(BeerControllerImpl.class);
		bind(new TypeLiteral<Repository<BeerVO>>() {
		}).to(BeerRepository.class);
		bind(new TypeLiteral<Repository<BeerTypeVO>>() {
		}).to(BeerTypeRepository.class);
		bind(CassandraConnectionHandler.class).to(CassandraConnectionHandlerImpl.class);

	}

}
