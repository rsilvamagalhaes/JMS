package br.com.k19.receptores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class AssinanteDeNoticiasGlassfish {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", 
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", 
				"com.sun.enterprise.naming");
		props.setProperty("java.naming.factory.state", 
				"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		
		InitialContext ic = new InitialContext(props);
		
		//fabrica de conexoes JMS
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/K19Factory");
		
		//topico
		Topic topic = (Topic) ic.lookup("jms/noticias");
		
		//conexao JMS
		Connection connection = factory.createConnection();
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//receptor de mensagem
		MessageConsumer receiver = session.createConsumer(topic);
		
		//inicializa  conexao
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
