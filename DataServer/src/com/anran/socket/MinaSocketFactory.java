package com.anran.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaSocketFactory {
	
	private int port;
	
	private SocketAcceptor acceptor;
	
	private DataHandler dataHandler;
	
	//apache �ṩ���̳߳أ��в���������ã�����֪������ʲôʱ��ȽϺã�������Ĭ�ϵ�
	private ExecutorService filterExecutor = new OrderedThreadPoolExecutor();
	
	
		
    public void setDataHandler(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public synchronized void start(){  
//        acceptor = new NioSocketAcceptor(Runtime.getRuntime()  
//                .availableProcessors());  
//          
//        acceptor.setReuseAddress(true);  
//        acceptor.getSessionConfig().setReadBufferSize(2048);  
//        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,  
//                10);  
//        // Decrease the default receiver buffer size  
//        ((SocketSessionConfig) acceptor.getSessionConfig())  
//                .setReceiveBufferSize(512);  
//  
//        MdcInjectionFilter mdcFilter = new MdcInjectionFilter();  
//  
//        acceptor.getFilterChain().addLast("mdcFilter", mdcFilter);  
//        acceptor.getFilterChain().addLast("threadPool",  
//                new ExecutorFilter(filterExecutor));//�����̳߳أ���֧�ֶ��߳�  
//        acceptor.getFilterChain().addLast("codec",  
//                new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName("UTF-8"))));//Э��  
//        acceptor.getFilterChain().addLast("mdcFilter2", mdcFilter);  
//        acceptor.getFilterChain().addLast("logger", new LoggingFilter());  
//        acceptor.setHandler(dataHandler);//���Լ��Ĵ�����  
//        
//        try{  
//        	acceptor.bind((new InetSocketAddress(port)));  
//        }catch(IOException e){  
//            e.printStackTrace();  
//        }  
          
    }
	
}
