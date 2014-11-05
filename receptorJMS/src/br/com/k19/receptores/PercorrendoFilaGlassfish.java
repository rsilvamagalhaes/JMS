package br.com.k19.receptores;

import java.util.Enumeration;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class PercorrendoFilaGlassfish {
	public static void main(String[] args) throws Exception {
		// servicos de nomes - JNDI
		Properties props = new Properties();
		
		props.setProperty("java.naming.factory.initial", 
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", 
				"com.sun.enterprise.naming");
		props.setProperty("java.naming.factory.state", 
				"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		
		InitialContext ic = new InitialContext(props);
		
		//fabrica de conexoes jms
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/K19Factory");
		
		//fila 
		Queue queue = (Queue) ic.lookup("jms/pedidos");
		
		//conexao jms
		Connection connection = factory.createConnection();
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//queue browser
		QueueBrowser queueBrowser = session.createBrowser(queue);
		
		Enumeration<TextMessage> messages = queueBrowser.getEnumeration();			
		int count = 1;
		
		while (messages.hasMoreElements()) {
			TextMessage textMessage = (TextMessage) messages.nextElement();
			System.out.println(count + " : " + textMessage.getText());
			count++;
		}
		
		//fechando
		queueBrowser.close();
		session.close();
		connection.close();
		
		System.out.println("FIM");
		System.exit(0);
	}
}