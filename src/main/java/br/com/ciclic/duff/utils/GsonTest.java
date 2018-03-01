package br.com.ciclic.duff.utils;

import java.util.Scanner;

import com.google.gson.Gson;

import br.com.ciclic.duff.model.BeerVO;

public class GsonTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String test = sc.next();
		Gson gson = new Gson();
		BeerVO beer = gson.fromJson(test, BeerVO.class);
		System.out.println(beer.getBeerType().getTypeName());
		System.out.println(beer.getBeerType().getTemperature().getMax());
		System.out.println(beer.getBeerType().getTemperature().getMin());
		System.out.println(beer.getName());

	}
}
