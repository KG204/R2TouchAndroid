package com.program;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

//import com.example.r2_touch_do_notclean.R;

//import com.program.Main_activity.ClientThread;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ClientThread2 implements Runnable {

    Socket socket;
    
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
    
    public void sendstring(String send_to_ard)
    {
		
		String message = send_to_ard;
    	//Converts to bytes and sends the text command to the Marcduino */
    	byte[] msgBuffer = message.getBytes();

        //DatagramSocket client_socket = null;
        
        int connection = Main_activity.connection;

    	if (connection == 3) {
        	OutputStream outStream = null;
        	try {
        		outStream = socket.getOutputStream();
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (NullPointerException e) {
        		//Todo
        	}
        	try {
        		outStream.write(msgBuffer);
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (NullPointerException e) {
        		//Todo
        	}
        }
    }
}