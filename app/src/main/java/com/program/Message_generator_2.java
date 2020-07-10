package com.program;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.program.r2_touch_android.R;

//import com.program.Main_activity.ClientThread;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Message_generator_2{

	public static Socket socket;
	
	/*public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Thread(new ClientThread()).start();
	}*/
	public Message_generator_2() {
		
	}
	
	public void sendstring(final String send_to_ard)
    {
    	//Need to create different options for sending string
    	/*Intent serialout = new Intent();
    	serialout.setClassName("com.program", "com.program.Output");
    	serialout.putExtra("BAUD", "9600");
    	serialout.putExtra("DATA","String_to_send");
    	startActivity(serialout);*/
		//new Thread(new ClientThread()).start();
		
		//new sendString().execute(send_to_ard);
		new Thread(new Runnable() {
	        public void run() {
	        	String message = send_to_ard;
	        	//Converts to bytes and sends the text command to the Marcduino */
	        	byte[] msgBuffer = message.getBytes();

	            //DatagramSocket client_socket = null;
	            
	            int connection = Main_activity.connection;
	        	/*if (connection == 4) {
	        		try {
	        			client_socket = new DatagramSocket(Main_activity.ip_port);
	        			InetAddress IPAddress =  InetAddress.getByName(Main_activity.ip_address);
	        	
	        			DatagramPacket send_packet = new DatagramPacket(msgBuffer, send_to_ard.length(), IPAddress, Main_activity.ip_port);
	        			client_socket.send(send_packet);
	        			client_socket.close();
	        		} catch (IOException e) {
	        			
	        		} finally {
	        			client_socket.close();
	        		}
	        	}*/
	        	if (connection == 3) {
	        		OutputStream outStream = null;
	        		try {
	                    InetAddress serverAddr = InetAddress.getByName(Main_activity.ip_address);
	                    socket = new Socket(serverAddr, Main_activity.ip_port);
	                    
	                    outStream = socket.getOutputStream();
	                    outStream.write(msgBuffer);
	                    
	                    socket.close();
	                } catch (UnknownHostException e1) {
	                    e1.printStackTrace();
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        	return;
	        }
	    }).start();
    }
	
	private class sendString extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        	String message = params[0];
        	//Converts to bytes and sends the text command to the Marcduino */
        	byte[] msgBuffer = message.getBytes();

            //DatagramSocket client_socket = null;
            
            int connection = Main_activity.connection;
        	/*if (connection == 4) {
        		try {
        			client_socket = new DatagramSocket(Main_activity.ip_port);
        			InetAddress IPAddress =  InetAddress.getByName(Main_activity.ip_address);
        	
        			DatagramPacket send_packet = new DatagramPacket(msgBuffer, send_to_ard.length(), IPAddress, Main_activity.ip_port);
        			client_socket.send(send_packet);
        			client_socket.close();
        		} catch (IOException e) {
        			
        		} finally {
        			client_socket.close();
        		}
        	}*/
        	if (connection == 3) {
        		OutputStream outStream = null;
        		try {
                    InetAddress serverAddr = InetAddress.getByName(Main_activity.ip_address);
                    socket = new Socket(serverAddr, Main_activity.ip_port);
                    
                    outStream = socket.getOutputStream();
                    outStream.write(msgBuffer);
                    
                    socket.close();
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        	return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
	
	
	/*
	class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(Main_activity.ip_address);
                socket = new Socket(serverAddr, Main_activity.ip_port);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    */
}
