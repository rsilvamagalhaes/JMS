package jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Conta {
	private double saldo;
	private double limite;
	private Cliente cliente;
	
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getLimite() {
		return limite;
	}
	public void setLimite(double limite) {
		this.limite = limite;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
