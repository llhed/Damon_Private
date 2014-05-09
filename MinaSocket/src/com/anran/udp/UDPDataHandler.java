package com.anran.udp;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class UDPDataHandler extends IoHandlerAdapter{
	
	private static String hexString = "0123456789ABCDEF";

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		
		   System.out.println("Session created...");
		   SocketAddress remoteAddress = session.getRemoteAddress();
		   System.out.println(remoteAddress);
		   
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Session Opened...");
		SocketAddress remoteAddress = session.getRemoteAddress();
		System.out.println(remoteAddress);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		 System.out.println("Session closed...");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		 System.out.println("Session idle...");
		 
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		    System.out.println("MessageReceived:");
		    String str = "a";
		    
		    byte[] array = {(byte)0x01,(byte)0x25,(byte)126,(byte)127,(byte)128,(byte)129,(byte)130,(byte)131,(byte)132,(byte)133} ;
		    
		    
//			ByteBuffer bf = ByteBuffer.wrap(array);
		    
	        if (message instanceof IoBuffer) {
	        IoBuffer buffer = (IoBuffer) message;
	            byte[] bb = buffer.array();
	            for(int i=0;i<bb.length;i++) {
	            	System.out.print((char)bb[i]);
	            }
	            
				IoBuffer buffer1 = IoBuffer.wrap(array);//返回信息给Clinet端
	            session.write(buffer1);

	            //声明这里message必须为IoBuffer类型
	        }

	}

	@Override
	/* messageSent是Server响应给Clinet成功后触发的事件
	 * (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	public void messageSent(IoSession session, Object message) throws Exception {
		//
		 if (message instanceof IoBuffer) {
			    IoBuffer buffer = (IoBuffer) message;
			    byte[] bb = buffer.array();
			    for (int i = 0; i < bb.length; i++) {
			    	System.out.print((char) bb[i]);
			    }
		}
	}
	


}
