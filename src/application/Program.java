package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<Sale> list = new ArrayList<>();
			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Map<String, Double> mapSellers = new HashMap<>();

			for (Sale sale : list) {

				mapSellers.put(sale.getSeller(), sale.getTotal());
			}

			for (String seller : mapSellers.keySet()) {
				double totalSales = list.stream().filter(x -> x.getSeller().equals(seller))
						.map(x -> x.getTotal()).reduce(0.0, (x, y) -> x + y);
				
				mapSellers.put(seller, totalSales);
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor: ");
			for (String seller : mapSellers.keySet()) {
				System.out.println(seller + " - R$ " + String.format("%.2f", mapSellers.get(seller)));
			}
		}

		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}
}