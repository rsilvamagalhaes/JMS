package br.com.k19.receptores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class RecebePedidoJboss {
	
	public static void main(String[] args) throws Exception {
		//servcos de nomes jndi
		Properties props = new Properties();
		
		props.setProperty("java.naming.factory.initial", 
						"org.jboss.naming.remote.client.InitialContextFactory");
		
		props.setProperty("java.naming.provider.url", "remote://localhost:4447");
		props.setProperty("java.naming.security.principal", "k19");
		props.setProperty("java.naming.security.credentials", "1234");
		
		InitialContext ic = new InitialContext(props);
		
		//fábrica de conexoes JMS
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("RemoteConnectionFactory");
		
		//fila
		Queue queue = (Queue) ic.lookup("jms/queue/pedidos");
		
		//conexao jms
		Connection connection = factory.createConnection("k19", "1234");
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//receptor de mensagens
		MessageConsumer receiver = session.createConsumer(queue);
		
		//inicializa conexao
		connection.start();
		
		//recebendo
		TextMessage message = (TextMessage) receiver.receive();
		
		System.out.println(message.getText());
		
		//fechando
		receiver.close();
		session.close();
		connection.close();
		
		System.out.println("FIM");
		System.exit(0);
	}
}