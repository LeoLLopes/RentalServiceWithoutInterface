package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalServices {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;
	
	public RentalServices(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();//retorna o get start em milisegundos
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1)/ 1000 / 60 / 60;//transforma os milisegundos em horas
		
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;//Math.ceil = arredonda os valores para cima
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;// /24 para localizar a quantidade em dias
		}
		
		double tax = taxService.tax(basicPayment);//calcula o valor do imposto a partir do basicPayment
		
		carRental.setInvoice(new Invoice(basicPayment, tax));//criei um novo objeto de nota de pagamento e associei com o objeto carRental
	}
}
