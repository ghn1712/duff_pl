package br.com.ciclic.duff;

import br.com.ciclic.duff.utils.InjectorHelper;

public class ServiceWrapper {

	public static void main(String[] args) {
		InjectorHelper.getInjector().getInstance(Service.class).start();
	}
}
