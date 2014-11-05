package br.com.k19.emissores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class EnviaNoticiaGlassfish {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", 
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", 
				"com.sun.enterprise.naming");
		props.setProperty("java.naming.factory.state", 
				"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		
		InitialContext ic = new InitialContext(props);
		
		//fábrica de conexoes jms
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/K19Factory");
		
		//topico
		Topic topic = (Topic) ic.lookup("jms/noticias");
		
		//conexao jms
		Connection connection = factory.createConnection();
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//emissor de mensagens
		MessageProducer sender = session.createProducer(topic);
		
		//mensagem
		TextMessage message = session.createTextMessage();
		message.setText("A copa do mundo de 2014 sera no Brasil" + System.currentTimeMillis());
		
		//enviando
		sender.send(message);
		
		//fechando
		sender.close();
		session.close();
		connection.close();
		
		System.out.println("Mensagem enviada");
		System.exit(0);
	}
}
