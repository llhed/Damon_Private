package com.anran.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadCastTest {

	
	 public static void main(String args[])throws Exception{  
	        sendBroadcast();  
	          
	    }  
	      
	    public static void sendBroadcast()throws Exception{  
	        DatagramSocket socket;  
	        DatagramPacket packet;  
//	        byte[] data="hello".getBytes(); 
	        
	        byte[] data = null;
	        StringBuilder sb = new StringBuilder();
	        
	        for(int i = 0 ; i < 100 ; i++){
	        	sb.append("a");
	        }
	        sb.append("0d0a");
	        sb.append("b");
	          
	        data = sb.toString().getBytes();
	        
	        socket = new DatagramSocket();  
	        socket.setBroadcast(true); //
	        //send端指定接受端的端口，自己的端口是随机的  
	        packet = new DatagramPacket(data,data.length,InetAddress.getByName("192.168.10.255"),30001); 
	        while(true){
	        	Thread.sleep(1000);
	        	socket.send(packet);
	        }
	        
//	        for(int i = 0 ; i < 50 ; i++){  
//	            Thread.sleep(2000);  
//	            socket.send(packet);  
//	        }  
	    } 
}
