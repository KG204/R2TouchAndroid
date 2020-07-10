package com.program;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.program.r2_touch_android.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Client extends Activity
{

    private Socket socket;

    public static final int SERVERPORT = Main_activity.ip_port;
    public static final String SERVER_IP = Main_activity.ip_address;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifitest);     
        new Thread(new ClientThread()).start();
    }
    public void onClick(View view) {
        /*
    	try {
            EditText et = (EditText) findViewById(R.id.EditText01);
            //String str = et.getText().toString();
            String str = "*ON00\r";
            //PrintWriter out = new PrintWriter(new BufferedWriter(
                    //new OutputStreamWriter(socket.getOutputStream())),
                    //true);
            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
            DOS.writeUTF(str);
            //out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    	OutputStream outStream = null;
    	try {
			outStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String message = "*ON00\r";
		/* In my example, I put a button that invoke this method and send a string to it */
		byte[] msgBuffer = message.getBytes();
		
		try {
			outStream.write(msgBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
