package com.anran.socket;

import org.apache.log4j.Logger;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.anran.services.Commands;
import com.anran.services.PositionStation;

public class DataHandler extends IoHandlerAdapter {

	private Logger logger = Logger.getLogger(MinaTimeServer.class);
	
	private Commands commands;
	private String[] targetAdress;
	private PositionStation positionStation;
	
	
	

	public void setPositionStation(PositionStation positionStation) {
		this.positionStation = positionStation;
	}

	public String[] getTargetAdress() {
		return targetAdress;
	}

	public void setTargetAdress(String[] targetAdress) {
		this.targetAdress = targetAdress;
	}

	public Commands getCommands() {
		return commands;
	}

	public void setCommands(Commands commands) {
		this.commands = commands;
	}

	

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		
		String[] stationNum = positionStation.getStationNum(session.getRemoteAddress().toString());
		this.setTargetAdress(stationNum);
		byte[] getDataCommand = commands.reqDataCommand(targetAdress );
		session.write(IoBuffer.wrap(getDataCommand));
			
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {


	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		
		byte[] getDataCommand = commands.reqDataCommand(targetAdress );
		session.write(IoBuffer.wrap(getDataCommand));
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}
	
	
	
	

}
