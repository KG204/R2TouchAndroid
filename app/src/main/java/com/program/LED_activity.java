package com.program;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.program.r2_touch_android.R;
import com.program.Main_activity.ClientThread;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LED_activity extends Activity
{
	public int topfrontldselect = 0;
	public int bottomfrontldselect = 0;
	public int rearldselect = 0;
	
	//public static Socket socket;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.led_screen);
		
		WizardPagerAdapter adapter = new WizardPagerAdapter();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
    
		PreferenceManager.setDefaultValues(this, R.xml.settings, true);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        final string_parser ste = new string_parser();
        
        Display display = getWindowManager().getDefaultDisplay();
        int screenSizeX = display.getWidth();
        int screenSizeY = display.getHeight();
        
        boolean dontuseCustom = prefs.getBoolean("useCustomSize", true);
		int buttonTSize = 12;
		int labelTSize = 12;
		try {
		buttonTSize = Integer.parseInt(prefs.getString("buttonTSize", "12"));
		labelTSize = Integer.parseInt(prefs.getString("labelTSize", "12"));
		} catch (NumberFormatException e) {
			
		}
    	
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText(prefs.getString("lk1_name", "CloseD"));
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText(prefs.getString("lk2_name", "CloseD"));
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText(prefs.getString("lk3_name", "CloseD"));
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText(prefs.getString("lk4_name", "CloseD"));
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText(prefs.getString("lk5_name", "CloseD"));
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText(prefs.getString("lk6_name", "CloseD"));
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText(prefs.getString("lk7_name", "CloseD"));
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText(prefs.getString("lk8_name", "CloseD"));
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText(prefs.getString("lk9_name", "CloseD"));
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText(prefs.getString("lk10_name", "CloseD"));
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText(prefs.getString("lk11_name", "CloseD"));
		Button btn12 = (Button) findViewById(R.id.buttons12); //>> Symbol
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText(prefs.getString("lk12_name", "CloseD"));
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText(prefs.getString("lk13_name", "CloseD"));
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText(prefs.getString("lk14_name", "CloseD"));
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText(prefs.getString("lk15_name", "CloseD"));
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText(prefs.getString("lk16_name", "CloseD"));
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText(prefs.getString("lk17_name", "CloseD"));
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText(prefs.getString("lk18_name", "CloseD"));
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText(prefs.getString("lk19_name", "CloseD"));
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText(prefs.getString("lk20_name", "CloseD"));
		Button btn22 = (Button) findViewById(R.id.buttons22); //<< Symbol
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText(prefs.getString("lk21_name", "CloseD"));
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText(prefs.getString("lk22_name", "CloseD"));

		
		TextView line1 = (TextView) findViewById (R.id.textView1);
        TextView line2 = (TextView) findViewById (R.id.logics1);
        TextView line3 = (TextView) findViewById (R.id.logics2);
        Button butless = (Button) findViewById (R.id.button_text);
        Button butmid = (Button) findViewById (R.id.button_aura);
        Button butmore = (Button) findViewById (R.id.button_latin);
        Button but1 = (Button) findViewById (R.id.buttons1);
        Button but2 = (Button) findViewById (R.id.buttons2);
        Button but3 = (Button) findViewById (R.id.buttons3);
        Button but4 = (Button) findViewById (R.id.buttons4);
        Button but5 = (Button) findViewById (R.id.buttons5);
        Button but6 = (Button) findViewById (R.id.buttons6);
        Button but7 = (Button) findViewById (R.id.buttons7);
        Button but8 = (Button) findViewById (R.id.buttons8);
        Button but9 = (Button) findViewById (R.id.buttons9);
        Button but10 = (Button) findViewById (R.id.buttons10);
        Button but11 = (Button) findViewById (R.id.buttons11);
        Button but12 = (Button) findViewById (R.id.buttons12);
        Button but13 = (Button) findViewById (R.id.buttons13);
        Button but14 = (Button) findViewById (R.id.buttons14);
        Button but15 = (Button) findViewById (R.id.buttons15);
        Button but16 = (Button) findViewById (R.id.buttons16);
        Button but17 = (Button) findViewById (R.id.buttons17);
        Button but18 = (Button) findViewById (R.id.buttons18);
        Button but19 = (Button) findViewById (R.id.buttons19);
        Button but20 = (Button) findViewById (R.id.buttons20);
        Button but21 = (Button) findViewById (R.id.buttons21);
        Button but22 = (Button) findViewById (R.id.buttons22);
        Button but23 = (Button) findViewById (R.id.buttons23);
        Button but24 = (Button) findViewById (R.id.buttons24);
        
        if (dontuseCustom == true) {
        	if (screenSizeX <= 480)
        	{
        		line1.setTextSize(12);
        		line2.setTextSize(12);
        		line3.setTextSize(12);
        		butless.setTextSize(10);
        		butmid.setTextSize(10);
        		butmore.setTextSize(10);
        		but1.setTextSize(10);
        		but2.setTextSize(10);
        		but3.setTextSize(10);
        		but4.setTextSize(10);
        		but5.setTextSize(10);
        		but6.setTextSize(10);
        		but7.setTextSize(10);
        		but8.setTextSize(10);
        		but9.setTextSize(10);
        		but10.setTextSize(10);
        		but11.setTextSize(10);
        		but12.setTextSize(10);
        		but13.setTextSize(10);
        		but14.setTextSize(10);
        		but15.setTextSize(10);
        		but16.setTextSize(10);
        		but17.setTextSize(10);
        		but18.setTextSize(10);
        		but19.setTextSize(10);
        		but20.setTextSize(10);
        		but21.setTextSize(10);
        		but22.setTextSize(10);
        		but23.setTextSize(10);
        		but24.setTextSize(10);
        	}
        	else if (screenSizeX <= 720)
        	{
        		line1.setTextSize(18);
        		line2.setTextSize(18);
        		line3.setTextSize(18);
        		butless.setTextSize(18);
        		butmid.setTextSize(18);
        		butmore.setTextSize(18);
        		but1.setTextSize(18);
        		but2.setTextSize(18);
        		but3.setTextSize(18);
        		but4.setTextSize(18);
        		but5.setTextSize(18);
        		but6.setTextSize(18);
        		but7.setTextSize(18);
        		but8.setTextSize(18);
        		but9.setTextSize(18);
        		but10.setTextSize(18);
        		but11.setTextSize(18);
        		but12.setTextSize(18);
        		but13.setTextSize(18);
        		but14.setTextSize(18);
        		but15.setTextSize(18);
        		but16.setTextSize(18);
        		but17.setTextSize(18);
        		but18.setTextSize(18);
        		but19.setTextSize(18);
        		but20.setTextSize(18);
        		but21.setTextSize(18);
        		but22.setTextSize(18);
        		but23.setTextSize(18);
        		but24.setTextSize(18);
        	}
        	else if (screenSizeX <= 1080)
        	{
        		line1.setTextSize(32);
        		line2.setTextSize(36);
        		line3.setTextSize(36);
        		butless.setTextSize(18);
        		butmid.setTextSize(18);
        		butmore.setTextSize(18);
        		but1.setTextSize(18);
        		but2.setTextSize(18);
        		but3.setTextSize(18);
        		but4.setTextSize(18);
        		but5.setTextSize(18);
        		but6.setTextSize(18);
        		but7.setTextSize(18);
        		but8.setTextSize(18);
        		but9.setTextSize(18);
        		but10.setTextSize(18);
        		but11.setTextSize(18);
        		but12.setTextSize(18);
        		but13.setTextSize(18);
        		but14.setTextSize(18);
        		but15.setTextSize(18);
        		but16.setTextSize(18);
        		but17.setTextSize(18);
        		but18.setTextSize(18);
        		but19.setTextSize(18);
        		but20.setTextSize(18);
        		but21.setTextSize(18);
        		but22.setTextSize(18);
        		but23.setTextSize(18);
        		but24.setTextSize(18);
        	}
        	else
        	{
        	}
        }
        else { //Custom size text
        	line1.setTextSize(labelTSize);
    		line2.setTextSize(labelTSize);
    		line3.setTextSize(labelTSize);
    		butless.setTextSize(buttonTSize);
    		butmid.setTextSize(buttonTSize);
    		butmore.setTextSize(buttonTSize);
    		but1.setTextSize(buttonTSize);
    		but2.setTextSize(buttonTSize);
    		but3.setTextSize(buttonTSize);
    		but4.setTextSize(buttonTSize);
    		but5.setTextSize(buttonTSize);
    		but6.setTextSize(buttonTSize);
    		but7.setTextSize(buttonTSize);
    		but8.setTextSize(buttonTSize);
    		but9.setTextSize(buttonTSize);
    		but10.setTextSize(buttonTSize);
    		but11.setTextSize(buttonTSize);
    		but12.setTextSize(buttonTSize);
    		but13.setTextSize(buttonTSize);
    		but14.setTextSize(buttonTSize);
    		but15.setTextSize(buttonTSize);
    		but16.setTextSize(buttonTSize);
    		but17.setTextSize(buttonTSize);
    		but18.setTextSize(buttonTSize);
    		but19.setTextSize(buttonTSize);
    		but20.setTextSize(buttonTSize);
    		but21.setTextSize(buttonTSize);
    		but22.setTextSize(buttonTSize);
    		but23.setTextSize(buttonTSize);
    		but24.setTextSize(buttonTSize);
        }

		//This button will go back to the main_activity
		ImageButton btnHome = (ImageButton) findViewById(R.id.home_Button);
        btnHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Goes back to the main screen
				finish();
			}
		});
        
        //The following buttons are at the top, and select how the Logic Displays
        //Are going to send data
        ImageButton btnreset = (ImageButton) findViewById(R.id.button_reset);
        btnreset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				topfrontldselect = 0;
				bottomfrontldselect = 0;
				rearldselect = 0;
				Main_activity.topLED = 0;
				Main_activity.bottomLED = 0;
				Main_activity.bigLED = 0;
				ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
    			logiclisten.setImageResource(R.drawable.led);
    			ImageView logiclisten2 = (ImageView) findViewById (R.id.bottomlogic);
    			logiclisten2.setImageResource(R.drawable.led);
    			ImageView logiclisten3 = (ImageView) findViewById (R.id.rearlogic);
    			logiclisten3.setImageResource(R.drawable.led2);
    			ste.select_string("@0T1\r");
			}
		});	
        Button btntext = (Button) findViewById(R.id.button_text);
        btntext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//@xxT100
				topfrontldselect = 1;
				bottomfrontldselect = 1;
				rearldselect = 1;
				Main_activity.topLED = 1;
				Main_activity.bottomLED = 1;
				Main_activity.bigLED = 1;
				ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
    			logiclisten.setImageResource(R.drawable.led_select);
    			ImageView logiclisten2 = (ImageView) findViewById (R.id.bottomlogic);
    			logiclisten2.setImageResource(R.drawable.led_select);
    			ImageView logiclisten3 = (ImageView) findViewById (R.id.rearlogic);
    			logiclisten3.setImageResource(R.drawable.led2_selected);
    			ste.select_string("@1T100\r");
    			ste.select_string("@2T100\r");
    			ste.select_string("@3T100\r");
			}
		});	
        //For some reason, the commands aren't working for button aura (cannot be certain on latin)
        //For that reason, these two buttons are deactivated until further investigation
        Button btnaura = (Button) findViewById(R.id.button_aura);
        btnaura.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//@xxP61\r
				if (topfrontldselect == 1)
				{
					ste.select_string("@1P61\r");
				}
				if (bottomfrontldselect == 1)
				{
					ste.select_string("@2P61\r");
				}
				if (rearldselect == 1)
				{
					ste.select_string("@3P61\r");
					ste.select_string("@0P61\r");
				}
				ste.select_string("@0P61\r");
			}
		});
        Button btnlatin = (Button) findViewById(R.id.button_latin);
        btnlatin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//@xxP60\r
				if (topfrontldselect == 1)
				{
					ste.select_string("@1P60\r");
				}
				if (bottomfrontldselect == 1)
				{
					ste.select_string("@2P60\r");
				}
				if (rearldselect == 1)
				{
					ste.select_string("@3P60\r");
				}
			}
		});	
        
        
		//The following button actions send a string command to the
        //MarcDuino
		btn1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T1\r\p*MO00\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T1\r");
				//Main_activity.sendstring("*MO00\r");
				String send = prefs.getString("lk1", "@0T1\\p*MO00");
				ste.select_string(send);
			}
		});
		btn2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T5\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T5\r");
				String send = prefs.getString("lk2", "@0T5");
				ste.select_string(send);
			}
		});
		btn3.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T1\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T4\r");
				String send = prefs.getString("lk3", "@0T4");
				ste.select_string(send);
			}
		});
		btn4.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T2\r\p@0W5\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T2\r");
				//Main_activity.sendstring("*@0W5\r");
				String send = prefs.getString("lk4", "@0T2\\p@0W5");
				ste.select_string(send);
			}
		});
		btn5.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T3\r\p@0W5\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T3\r");
				//Main_activity.sendstring("*@0W5\r");
				String send = prefs.getString("lk5", "@0T3\\p@0W5");
				ste.select_string(send);
			}
		});
		btn6.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T6\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T6\r");
				String send = prefs.getString("lk6", "@0T6");
				ste.select_string(send);
			}
		});
		btn7.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T2\r\p@0W5\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T11\r");
				String send = prefs.getString("lk7", "@0T11");
				ste.select_string(send);
			}
		});
		btn8.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T92\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T92\r");
				String send = prefs.getString("lk8", "@0T92");
				ste.select_string(send);
			}
		});
		btn9.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@6T1\r\p@7T1\r\p@8T1\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@6T1\r");
				//Main_activity.sendstring("*@7T1\r");
				//Main_activity.sendstring("*@8T1\r");
				String send = prefs.getString("lk9", "@6T1\\p@7T1\\p@8T1");
				ste.select_string(send);
			}
		});
		btn10.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0D\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0D\r");
				String send = prefs.getString("lk10", "@0D");
				ste.select_string(send);
			}
		});
		btn11.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MF05\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MF05\r");
				String send = prefs.getString("lk11", "*MF05");
				ste.select_string(send);
			}
		});
		btn12.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(1);
			}
		});
		btn13.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T0\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T0\r");
				String send = prefs.getString("lk12", "@0T0");
				ste.select_string(send);
			}
		});
		btn14.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "@0T10\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T10\r");
				String send = prefs.getString("lk13", "@0T10");
				ste.select_string(send);
			}
		});
		btn15.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO99\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MO99\r");
				String send = prefs.getString("lk14", "*MO99");
				ste.select_string(send);
			}
		});
		btn16.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO00\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MO00\r");
				String send = prefs.getString("lk15", "*MO00");
				ste.select_string(send);
			}
		});
		btn17.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO05\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MO05\r");
				String send = prefs.getString("lk16", "*MO05");
				ste.select_string(send);
			}
		});
		btn18.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MF99\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MF99\r");
				String send = prefs.getString("lk17", "*MF99");
				ste.select_string(send);
			}
		});
		btn19.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*@0T1\r\pMO00\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("@0T1\r");
				//Main_activity.sendstring("*MO00\r");
				String send = prefs.getString("lk18", "@0T1\\pMO00");
				ste.select_string(send);
			}
		});
		btn20.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String send = prefs.getString("lk19", "");
				ste.select_string(send);
			}
		});
		btn21.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String send = prefs.getString("lk20", "");
				ste.select_string(send);
			}
		});
		btn22.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(0);
			}
		});
		btn23.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String send = prefs.getString("lk21", "");
				ste.select_string(send);
			}
		});
		btn24.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String send = prefs.getString("lk22", "");
				ste.select_string(send);
			}
		});
		
		//The following listen to the edittext fields. Once the message is finished
		//The code sends the message to the MarcDuino, which stores it.
		//The message will show up if the ALL TEXT Button is Pressed, or
		//Individual Logic Display Graphic is Pressed
		EditText topText = (EditText) findViewById(R.id.top_ld);
		topText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					EditText top_fld = (EditText) findViewById(R.id.top_ld);
					String message = top_fld.getText().toString().toUpperCase();
					ste.select_string("@1M" + message + "\r");
					
					if (topfrontldselect == 1)
					{
						ste.select_string("@1T100\r");
					}
	                return true;
	            }
				return false;
			}
	    });
		EditText bottomText = (EditText) findViewById(R.id.bottom_ld);
		bottomText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					EditText bottom_fld = (EditText) findViewById(R.id.bottom_ld);
					String message = bottom_fld.getText().toString().toUpperCase();
					ste.select_string("@2M" + message + "\r");
					if (bottomfrontldselect == 1)
					{
						ste.select_string("@2T100\r");
					}
	                return true;
	            }
				return false;
			}
	    });
		EditText rearText = (EditText) findViewById(R.id.rear_ld);
		rearText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					EditText rld = (EditText) findViewById(R.id.rear_ld);
					String message = rld.getText().toString().toUpperCase();
					ste.select_string("@3M" + message + "\r");
					if (rearldselect == 1)
					{
						ste.select_string("@3T100\r");
					}
	                return true;
	            }
				return false;
			}
	    });
		
		//These lines change the image of the logic displays to display on and off
		//If they are selected on, they send a command to display the text that was
		//Previously typed
		//If off, they randomize it again
		ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
        logiclisten.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		if (topfrontldselect == 0)
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
        			logiclisten.setImageResource(R.drawable.led_select);
        			Main_activity.topLED = 1;
        			topfrontldselect = 1;
        			ste.select_string("@1T100\r");
        		}
        		else
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
        			logiclisten.setImageResource(R.drawable.led);
        			Main_activity.topLED = 0;
        			topfrontldselect = 0;
        			ste.select_string("@1T1\r");
        		}
        	}
        });
        ImageView logiclisten2 = (ImageView) findViewById (R.id.bottomlogic);
        logiclisten2.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		if (bottomfrontldselect == 0)
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.bottomlogic);
        			logiclisten.setImageResource(R.drawable.led_select);
        			Main_activity.bottomLED = 1;
        			bottomfrontldselect = 1;
        			ste.select_string("@2T100\r");
        		}
        		else
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.bottomlogic);
        			logiclisten.setImageResource(R.drawable.led);
        			Main_activity.bottomLED = 0;
        			bottomfrontldselect = 0;
        			ste.select_string("@2T1\r");
        		}
        	}
        });
        ImageView logiclisten3 = (ImageView) findViewById (R.id.rearlogic);
        logiclisten3.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		if (rearldselect == 0)
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.rearlogic);
        			logiclisten.setImageResource(R.drawable.led2_selected);
        			Main_activity.bigLED = 1;
        			rearldselect = 1;
        			ste.select_string("@3T100\r");
        		}
        		else
        		{
        			ImageView logiclisten = (ImageView) findViewById (R.id.rearlogic);
        			logiclisten.setImageResource(R.drawable.led2);
        			Main_activity.bigLED = 0;
        			rearldselect = 0;
        			ste.select_string("@3T1\r");
        		}
        	}
        });
		
	}
	
	//This class controls and defines the ViewPager, which is the swipe buttons
	public class WizardPagerAdapter extends PagerAdapter
	{
	    public Object instantiateItem(View collection, int position)
	    {
	        int resId = 0;
	        switch (position) {
	        case 0:
	            resId = R.id.page_one;
	            break;
	        case 1:
	            resId = R.id.page_two;
	            break;
	        }
	        return findViewById(resId);
	    }
	    @Override
	    public int getCount() {
	        return 2;
	    }
	    @Override
	    public boolean isViewFromObject(View arg0, Object arg1) {
	        return arg0 == ((View) arg1);
	    }
	}
	
	public void onResume() {
		super.onResume();
		
		topfrontldselect = Main_activity.topLED;
    	bottomfrontldselect = Main_activity.bottomLED;
    	rearldselect = Main_activity.bigLED;
        
    	if (topfrontldselect == 0)
		{
			ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
			logiclisten.setImageResource(R.drawable.led);
		}
		else
		{
			ImageView logiclisten = (ImageView) findViewById (R.id.toplogic);
			logiclisten.setImageResource(R.drawable.led_select);
		}
    	if (bottomfrontldselect == 0)
		{
			ImageView logiclisten = (ImageView) findViewById (R.id.bottomlogic);
			logiclisten.setImageResource(R.drawable.led);
		}
		else
		{
			ImageView logiclisten = (ImageView) findViewById (R.id.bottomlogic);
			logiclisten.setImageResource(R.drawable.led_select);
		}
    	if (rearldselect == 0)
    	{
    		ImageView logiclisten = (ImageView) findViewById (R.id.rearlogic);
			logiclisten.setImageResource(R.drawable.led2);
    	}
    	else
    	{
    		ImageView logiclisten = (ImageView) findViewById (R.id.rearlogic);
			logiclisten.setImageResource(R.drawable.led2_selected);
    	}
	}
}