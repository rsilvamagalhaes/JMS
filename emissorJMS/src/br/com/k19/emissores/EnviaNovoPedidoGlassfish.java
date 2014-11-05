package br.com.k19.emissores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class EnviaNovoPedidoGlassfish {
	public static void main(String[] args) throws Exception {
		// serviço de nomes - JNDIstatus
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs",
				"con.sun.enterprise.naming");
		props.setProperty("java.naming.factory.state",
				"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

		InitialContext ic = new InitialContext(props);

		// Fabrica de conexoes
		ConnectionFactory factory = (ConnectionFactory) ic
				.lookup("jms/K19Factory");

		// Fila
		Queue queue = (Queue) ic.lookup("jms/pedidos");

		// Conexao jms
		Connection connection = factory.createConnection();

		// sessao jms
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// emissor de mensagens
		MessageProducer sender = session.createProducer(queue);

		// mensagem
		TextMessage message = session.createTextMessage();
		message.setText("Uma pizza de cinco queijos e uma coca-cola "
				+ System.currentTimeMillis());
		
		//enviando
		sender.send(message);
		
		//fechando
		sender.close();
		session.close();
		connection.close();
		
		System.out.println("Mensagem enviada!!");
		
		System.exit(0);
	}
}
