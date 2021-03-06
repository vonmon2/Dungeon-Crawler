package com.rentarosato520.dungeoncrawler.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import com.rentarosato520.dungeoncrawler.GameMain;

public class GameServer extends Thread{
	private DatagramSocket socket;
	private GameMain game;
	
	public GameServer(GameMain game){
		this.game = game;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {

				e.printStackTrace();
			}
			String message = new String(packet.getData());
			if(message.trim().equalsIgnoreCase("ping")){
				System.out.println("CLIENT ["+packet.getAddress().getHostAddress()+ " : "+packet.getPort()+"]> "+message);
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipaddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipaddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
