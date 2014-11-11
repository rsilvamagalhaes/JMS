package jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Deserializador {
	public static void main(String[] args) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Conta.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		Conta conta = (Conta) unmarshaller.unmarshal(new File("conta.xml"));
		
		System.out.println(conta.getLimite());
		System.out.println(conta.getSaldo());
		System.out.println(conta.getCliente().getNome());
	}
}
