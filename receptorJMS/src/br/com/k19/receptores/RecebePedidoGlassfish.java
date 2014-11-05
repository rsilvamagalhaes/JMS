package br.com.k19.receptores;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class RecebePedidoGlassfish {
	public static void main(String[] args) throws Exception {
		//servicos de nomes - jndi
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
		
		//fila
		Queue queue = (Queue)ic.lookup("jms/pedidos");
		
		//conexao jms
		Connection connection = factory.createConnection("k19", "1234");
		
		//sessao jms
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//receptor  de mensagens
		MessageConsumer receiver = session.createConsumer(queue);
		
		//inicia conexao
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
