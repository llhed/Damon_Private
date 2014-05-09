package com.anran.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class UDPTestServer {

	
	 private static final long serialVersionUID = 1L;

	    public static final int PORT = 10001;

	    public UDPTestServer() throws IOException {

	        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();//����һ��UDP�Ľ�����
	        acceptor.setHandler(new UDPDataHandler());//���ý������Ĵ������

	        Executor threadPool = Executors.newFixedThreadPool(1500);//�����̳߳�
	        acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
	        acceptor.getFilterChain().addLast("logger", new LoggingFilter());

	        DatagramSessionConfig dcfg = acceptor.getSessionConfig();//�������ӵ������ļ�
	        dcfg.setReadBufferSize(4096);//���ý�������ֽ�Ĭ��2048
	        dcfg.setReceiveBufferSize(1024);//�������뻺�����Ĵ�С
	        dcfg.setSendBufferSize(1024);//��������������Ĵ�С
	        dcfg.setReuseAddress(true);//����ÿһ�������������ӵĶ˿ڿ�������
	        
	        acceptor.bind(new InetSocketAddress(PORT));//�󶨶˿�
	    	  

	    }
	    
	   
	    
	    public static void main(String[] args) throws IOException {
	        new UDPTestServer();
	    }

}
