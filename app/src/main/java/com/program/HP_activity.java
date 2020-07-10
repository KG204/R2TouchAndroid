package com.program;

import com.program.r2_touch_android.R;
import com.program.Main_activity.WizardPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;

public class HP_activity extends Activity implements View.OnTouchListener 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hp_screen);
        WizardPagerAdapter adapter = new WizardPagerAdapter();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        
        PreferenceManager.setDefaultValues(this, R.xml.settings, true);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        final string_parser ste = new string_parser ();
        
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
        btn1.setText(prefs.getString("hk1_name", "CloseD"));
    	Button btn2 = (Button) findViewById(R.id.buttons2);
    	btn2.setText(prefs.getString("hk2_name", "CloseD"));
    	Button btn3 = (Button) findViewById(R.id.buttons3);
    	btn3.setText(prefs.getString("hk3_name", "CloseD"));
    	Button btn4 = (Button) findViewById(R.id.buttons4);
    	btn4.setText(prefs.getString("hk4_name", "CloseD"));
    	Button btn5 = (Button) findViewById(R.id.buttons5);
    	btn5.setText(prefs.getString("hk5_name", "CloseD"));
    	Button btn6 = (Button) findViewById(R.id.buttons6);
    	btn6.setText(prefs.getString("hk6_name", "CloseD"));
    	Button btn7 = (Button) findViewById(R.id.buttons7);
    	btn7.setText(prefs.getString("hk7_name", "CloseD"));
    	Button btn8 = (Button) findViewById(R.id.buttons8);
    	btn8.setText(prefs.getString("hk8_name", "CloseD"));
    	Button btn9 = (Button) findViewById(R.id.buttons9);
    	btn9.setText(prefs.getString("hk9_name", "CloseD"));
    	Button btn10 = (Button) findViewById(R.id.buttons10);
    	btn10.setText(prefs.getString("hk10_name", "CloseD"));
    	Button btn11 = (Button) findViewById(R.id.buttons11);
    	btn11.setText(prefs.getString("hk11_name", "CloseD"));
    	Button btn12 = (Button) findViewById(R.id.buttons12); //>> Symbol
    	Button btn13 = (Button) findViewById(R.id.buttons13);
    	btn13.setText(prefs.getString("hk12_name", "CloseD"));
    	Button btn14 = (Button) findViewById(R.id.buttons14);
    	btn14.setText(prefs.getString("hk13_name", "CloseD"));
    	Button btn15 = (Button) findViewById(R.id.buttons15);
    	btn15.setText(prefs.getString("hk14_name", "CloseD"));
    	Button btn16 = (Button) findViewById(R.id.buttons16);
    	btn16.setText(prefs.getString("hk15_name", "CloseD"));
    	Button btn17 = (Button) findViewById(R.id.buttons17);
    	btn17.setText(prefs.getString("hk16_name", "CloseD"));
    	Button btn18 = (Button) findViewById(R.id.buttons18);
    	btn18.setText(prefs.getString("hk17_name", "CloseD"));
    	Button btn19 = (Button) findViewById(R.id.buttons19);
    	btn19.setText(prefs.getString("hk18_name", "CloseD"));
    	Button btn20 = (Button) findViewById(R.id.buttons20);
    	btn20.setText(prefs.getString("hk19_name", "CloseD"));
    	Button btn21 = (Button) findViewById(R.id.buttons21);
    	btn21.setText(prefs.getString("hk20_name", "CloseD"));
    	Button btn22 = (Button) findViewById(R.id.buttons22); //<< Symbol
    	Button btn23 = (Button) findViewById(R.id.buttons23);
    	btn23.setText(prefs.getString("pk21_name", "CloseD"));
    	Button btn24 = (Button) findViewById(R.id.buttons24);
    	btn24.setText(prefs.getString("pk22_name", "CloseD"));
        
    	ImageButton btnnone = (ImageButton) findViewById(R.id.button_non);
    	Button btnfront = (Button) findViewById(R.id.button_front);
    	Button btntopback = (Button) findViewById(R.id.button_topback);
    	Button btnall = (Button) findViewById(R.id.button_all);
    	
    	TextView line1 = (TextView) findViewById (R.id.textView1);
        TextView line2 = (TextView) findViewById (R.id.textView1H);
        TextView line3 = (TextView) findViewById (R.id.textView0H);
        Button butless = (Button) findViewById (R.id.button_topback);
        Button butmid = (Button) findViewById (R.id.button_all);
        Button butmore = (Button) findViewById (R.id.button_front);
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
        		line1.setTextSize(16);
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
        		line1.setTextSize(26);
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
        else {
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
        
    	btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				/*
				Context context1 = getApplicationContext();
				//1 if is selected, 0 if not
				if (Main_activity.hp1 == 1) {Toast.makeText(context1, "*ON01\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp2 == 1) {Toast.makeText(context1, "*ON02\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp3 == 1) {Toast.makeText(context1, "*ON03\r", Toast.LENGTH_LONG).show();}
				*/
				String send = prefs.getString("hk1", "*ONXX");
				ste.select_string(send);
			}
		});
    	btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				/*
				Context context1 = getApplicationContext();
				//1 if is selected, 0 if not
				if (Main_activity.hp1 == 1) {Toast.makeText(context1, "*OF01\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp2 == 1) {Toast.makeText(context1, "*OF02\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp3 == 1) {Toast.makeText(context1, "*OF03\r", Toast.LENGTH_LONG).show();}
				*/
				String send = prefs.getString("hk2", "*OFXX");
				ste.select_string(send);
			}
		});
    	btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*ST00\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*ST00\r");
				String send = prefs.getString("hk3", "*ST00");
				ste.select_string(send);
			}
		});
    	btn4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				/*
				Context context1 = getApplicationContext();
				if (Main_activity.hp1 == 1) {Toast.makeText(context1, "*RD01\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp2 == 1) {Toast.makeText(context1, "*RD02\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp3 == 1) {Toast.makeText(context1, "*RD03\r", Toast.LENGTH_LONG).show();}
				*/
				String send = prefs.getString("hk4", "*RDXX");
				ste.select_string(send);
			}
		});
    	btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				/*
				Context context1 = getApplicationContext();
				if (Main_activity.hp1 == 1) {Toast.makeText(context1, "*RC01\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp2 == 1) {Toast.makeText(context1, "*RC02\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp3 == 1) {Toast.makeText(context1, "*RC03\r", Toast.LENGTH_LONG).show();}
				*/
				String send = prefs.getString("hk5", "*RCXX");
				ste.select_string(send);
			}
		});
    	btn6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				/*
				Context context1 = getApplicationContext();
				if (Main_activity.hp1 == 1) {Toast.makeText(context1, "*HD01\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp2 == 1) {Toast.makeText(context1, "*HD02\r", Toast.LENGTH_LONG).show();}
				if (Main_activity.hp3 == 1) {Toast.makeText(context1, "*HD03\r", Toast.LENGTH_LONG).show();}
				*/
				String send = prefs.getString("hk6", "*HDXX");
				ste.select_string(send);
			}
		});
    	btn7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO99\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MO99\r");
				String send = prefs.getString("hk7", "*MO99");
				ste.select_string(send);
			}
		});
    	btn8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO03\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MD03\r");
				String send = prefs.getString("hk8", "*MD03");
				ste.select_string(send);
			}
		});
    	btn9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MO00\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MO00\r");
				String send = prefs.getString("hk9", "*MO00");
				ste.select_string(send);
			}
		});
    	btn10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MF99\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MF99\r");
				String send = prefs.getString("hk10", "*MF99");
				ste.select_string(send);
			}
		});
    	btn11.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Check to see what the current selected sound resource is. ex if its whis, $xyy x is 5
				//Context context1 = getApplicationContext();
				//Toast.makeText(context1, "*MF05\r", Toast.LENGTH_LONG).show();
				//Main_activity.sendstring("*MF05\r");
				String send = prefs.getString("hk11", "*MF05");
				ste.select_string(send);
			}
		});
    	btn12.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(1);
			}
		});
    	btn13.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk12", "");
				ste.select_string(send);
			}
		});
    	btn14.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk13", "");
				ste.select_string(send);
			}
		});
    	btn15.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk14", "");
				ste.select_string(send);
			}
		});
    	btn16.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk15", "");
				ste.select_string(send);
			}
		});
    	btn17.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk16", "");
				ste.select_string(send);
			}
		});
    	btn18.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk17", "");
				ste.select_string(send);
			}
		});
    	btn19.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk18", "");
				ste.select_string(send);
			}
		});
    	btn20.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk19", "");
				ste.select_string(send);
			}
		});
    	btn21.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk20", "");
				ste.select_string(send);
			}
		});
    	btn22.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(0);
			}
		});
    	btn23.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk21", "");
				ste.select_string(send);
			}
		});
    	btn24.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String send = prefs.getString("hk22", "");
				ste.select_string(send);
			}
		});
        /*
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.image_graphic, options);
        
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.image_sel_layer, options2);
        */
        ImageView iv = (ImageView) findViewById (R.id.image_graphic);
        ImageView jv = (ImageView) findViewById (R.id.image_sel_layer);
        ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
        ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
        ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
        
        /*
        iv.setImageBitmap(
        	    decodeSampledBitmapFromResource(getResources(), R.drawable.dome_hp_wireframe, 400, 400));        
        jv.setImageBitmap(
        	    decodeSampledBitmapFromResource(getResources(), R.drawable.hp_selection_layer, 400, 400));        
        kv.setImageBitmap(
        	    decodeSampledBitmapFromResource(getResources(), R.drawable.hp1_light, 400, 400));        
        lv.setImageBitmap(
        	    decodeSampledBitmapFromResource(getResources(), R.drawable.hp2_light, 400, 400));        
        mv.setImageBitmap(
        	    decodeSampledBitmapFromResource(getResources(), R.drawable.hp3_light, 400, 400));        
        */
        if (Main_activity.hp1 == 0) {kv.setVisibility(View.INVISIBLE);}
 	   	else {kv.setVisibility(View.VISIBLE);}
        if (Main_activity.hp2 == 0) {lv.setVisibility(View.INVISIBLE);}
 	   	else {lv.setVisibility(View.VISIBLE);}
        if (Main_activity.hp3 == 0) {mv.setVisibility(View.INVISIBLE);}
 	   	else {mv.setVisibility(View.VISIBLE);}
        
        ImageButton btnHome = (ImageButton) findViewById(R.id.home_Button);
        btnHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Goes back to the main screen
				finish();
			}
		});

        btnnone.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
		        ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
		        ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
				
				Main_activity.hp1 = 0;
				Main_activity.hp2 = 0;
				Main_activity.hp3 = 0;
				kv.setVisibility(View.INVISIBLE);
		        lv.setVisibility(View.INVISIBLE);
		        mv.setVisibility(View.INVISIBLE);
			}
		});
    	btnfront.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
		        ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
		        ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
				
				Main_activity.hp1 = 1;
				Main_activity.hp2 = 0;
				Main_activity.hp3 = 0;
				
				kv.setVisibility(View.VISIBLE);
		        lv.setVisibility(View.INVISIBLE);
		        mv.setVisibility(View.INVISIBLE);
			}
		});
    	btntopback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
		        ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
		        ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
				
				Main_activity.hp1 = 0;
				Main_activity.hp2 = 1;
				Main_activity.hp3 = 1;
				
		        kv.setVisibility(View.INVISIBLE);
		        lv.setVisibility(View.VISIBLE);
		        mv.setVisibility(View.VISIBLE);
			}
		});
    	btnall.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
		        ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
		        ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
				
				Main_activity.hp1 = 1;
				Main_activity.hp2 = 1;
				Main_activity.hp3 = 1;
				
		        kv.setVisibility(View.VISIBLE);
		        lv.setVisibility(View.VISIBLE);
		        mv.setVisibility(View.VISIBLE);
			}
		});
        
        if (iv != null) {
           iv.setOnTouchListener(this);
        }
        
    }

public boolean onTouch (View v, MotionEvent ev) 
{
    boolean handledHere = false;

    final int action = ev.getAction();
    int tolerance = 25;
    final int evX = (int) ev.getX();
    final int evY = (int) ev.getY();
    int nextImage = -1;			// resource id of the next image to display

    ImageView kv = (ImageView) findViewById (R.id.hp1_imageview);
    ImageView lv = (ImageView) findViewById (R.id.hp2_imageview);
    ImageView mv = (ImageView) findViewById (R.id.hp3_imageview);
    
    // If we cannot find the imageView, return.
    ImageView imageView = (ImageView) v.findViewById (R.id.image_graphic);
    if (imageView == null) return false;

    // When the action is Down, see if we should show the "pressed" image for the default image.
    // We do this when the default image is showing. That condition is detectable by looking at the
    // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
    Integer tagNum = (Integer) imageView.getTag ();
    int currentResource = (tagNum == null) ? R.drawable.dome_hp_wireframe : tagNum.intValue ();

    // Now that we know the current resource being displayed we can handle the DOWN and UP events.

    switch (action) {
    case MotionEvent.ACTION_DOWN :
        handledHere = true; 
       break;

    case MotionEvent.ACTION_UP :
       // On the UP, we do the click action.
       // The hidden image (image_areas) has three different hotspots on it.
       // The colors are red, blue, and yellow.
       // Use image_areas to determine which region the user touched.
       int touchColor = getHotspotColor (R.id.image_sel_layer, evX, evY);

       // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
       // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
       // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
       // varying pixel density.
       ColorTool ct = new ColorTool ();
       //nextImage = R.drawable.p2_ship_default;
       if (ct.closeMatch (Color.argb(255, 0, 0, 255), touchColor, tolerance)){
    	   if (kv.getVisibility() == View.VISIBLE) {kv.setVisibility(View.INVISIBLE); Main_activity.hp1 = 0;}
    	   else {kv.setVisibility(View.VISIBLE); Main_activity.hp1 = 1;}
       }// HP 1
       else if (ct.closeMatch (Color.argb(255, 255, 0, 0), touchColor, tolerance)){
    	   if (lv.getVisibility() == View.VISIBLE) {lv.setVisibility(View.INVISIBLE); Main_activity.hp2 = 0;}
    	   else {lv.setVisibility(View.VISIBLE); Main_activity.hp2 = 1;}
       } //HP 2
       else if (ct.closeMatch (Color.argb(255, 0, 255, 0), touchColor, tolerance)){
    	   if (mv.getVisibility() == View.VISIBLE) {mv.setVisibility(View.INVISIBLE); Main_activity.hp3 = 0;}
    	   else  {mv.setVisibility(View.VISIBLE); Main_activity.hp3 = 1;}
       } //HP 3

       // If the next image is the same as the last image, go back to the default.
       // toast ("Current image: " + currentResource + " next: " + nextImage);
       //if (currentResource == nextImage) {
       //   nextImage = R.drawable.p2_ship_default;
       //} 
       handledHere = true; 
       break;

    default:
       handledHere = false;
    } // end switch

    return handledHere;
}

public int getHotspotColor (int hotspotId, int x, int y) {
    ImageView img = (ImageView) findViewById (hotspotId);
    if (img == null) {
       Log.d ("ImageAreasActivity", "Hot spot image not found");
       return 0;
    } else {
      img.setDrawingCacheEnabled(true); 
      Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache()); 
      if (hotspots == null) {
         Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
         return 0;
      } else {
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
      }
    }
}

public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
final int height = options.outHeight;
final int width = options.outWidth;
int inSampleSize = 1;

if (height > reqHeight || width > reqWidth) {

    final int halfHeight = height / 2;
    final int halfWidth = width / 2;

    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
    // height and width larger than the requested height and width.
    while ((halfHeight / inSampleSize) > reqHeight
            && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
    }
}

return inSampleSize;
}

public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
        int reqWidth, int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
}

public class WizardPagerAdapter extends PagerAdapter {
    public Object instantiateItem(View collection, int position) {
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

}
