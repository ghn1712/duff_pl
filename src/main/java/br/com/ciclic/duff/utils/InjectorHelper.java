package br.com.ciclic.duff.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.com.ciclic.duff.DuffModule;

public class InjectorHelper {
	static Injector injector = Guice.createInjector(new DuffModule());

	private InjectorHelper() {

	}

	public static Injector getInjector() {
		return injector;
	}

}
