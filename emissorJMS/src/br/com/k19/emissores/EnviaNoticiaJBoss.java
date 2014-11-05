package br.com.k19.emissores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class EnviaNoticiaJBoss {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jboss.naming.remote.client.InitialContextFactory");
		props.setProperty("java.naming.provider.url", "remote://localhost:4447");
		props.setProperty("java.naming.security.principal", "k19");
		props.setProperty("java.naming.security.credentials", "1234");

		InitialContext ic = new InitialContext(props);

		// fabrica de conexao jms
		ConnectionFactory factory = (ConnectionFactory) ic
				.lookup("jms/K19Factory");
		
		Topic topic = (Topic) ic.lookup("jms/topic/noticias");
		
		//conexao jms
		Connection connection = factory.createConnection("k19", "1234");
	
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//emissor de mensagem
		MessageProducer sender = session.createProducer(topic);
		
		//mensagem
		TextMessage message = session.createTextMessage();
		message.setText("A copa do muundo de 2014 sera no brasil = " + System.currentTimeMillis());
		
		sender.send(message);
		
		sender.close();
		session.close();
		connection.close();
		
		System.out.println("Mensagem enviada");
		System.exit(0);
	}
}
