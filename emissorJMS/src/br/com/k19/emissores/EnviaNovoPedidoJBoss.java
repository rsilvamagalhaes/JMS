package br.com.k19.emissores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class EnviaNovoPedidoJBoss {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
				"org.jboss.naming.remote.client.InitialContextFactory");
		props.setProperty(Context.PROVIDER_URL, "remote://localhost:4447");
		props.setProperty(Context.SECURITY_PRINCIPAL, "k19");
		props.setProperty(Context.SECURITY_CREDENTIALS, "1234");
		
		InitialContext ic = new InitialContext(props);
		
		//fabrica de conexao jms
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("RemoteConnectionFactory");
		
		//fila
		Queue queue = (Queue) ic.lookup("jms/queue/pedidos");
		
		//conexao jms
		Connection connection = factory.createConnection("k19", "1234");
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//emissor de mensagem
		MessageProducer sender = session.createProducer(queue);
		
		TextMessage message = session.createTextMessage();
		message.setText("Uma pizza de cinco queijos e uma coca-cola de 2l - " + 
		System.currentTimeMillis());
		
		//enviando
		sender.send(message);
		
		// fechando
		sender.close();
		session.close();
		connection.close();
		
		System.out.println("Mensagem enviada");
		System.exit(0);
	}
}
