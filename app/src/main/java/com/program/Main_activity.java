package com.program;

import com.program.r2_touch_android.R;
import com.squareup.picasso.Picasso;

import android.R.string;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
//import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main_activity extends Activity implements View.OnTouchListener {
	// Initializing variables
	public static int hp1 = 0; // 0 means inactive, 1 means active
	public static int hp2 = 0;
	public static int hp3 = 0;
	public static int panel1 = 0;
	public static int panel2 = 0;
	public static int panel3 = 0;
	public static int panel4 = 0;
	public static int panel5 = 0;
	public static int panel6 = 0;
	public static int panel7 = 0;
	public static int panel8 = 0;
	public static int panel9 = 0;
	public static int panel10 = 0;
	public static int topLED = 0;
	public static int bottomLED = 0;
	public static int bigLED = 0;
	public static int sound_screen = 1; // Default for sound_screen should be on
										// 1
	public static boolean randomModeOn = false;
	public static String arduino_string = "";
	public static String ip_address = "";
	public static int ip_port = 0;
	public static int connection = 3;
	public static String main1 = "";
	public static String main2 = "";
	public static String main3 = "";
	public static String main4 = "";
	public static String main5 = "";
	public static String main6 = "";
	public static String main7 = "";
	public static String main8 = "";
	public static String main9 = "";
	public static String main10 = "";
	public static String main11 = "";
	public static String main12 = "";
	public static String test = "";
	public static int mainSound = 0;
	public static int screenSizeX = 0;
	public static int screenSizeY = 0;

	int imageWidth = 0;
	int imageHeight = 0;
	int imageWidthS = 0;
	int imageHeightS = 0;
	int sndON = 0; // If 0, means task isn't supposed to be active, if 1, task
					// is supposed to be active
	int holoON = 0;
	int quietON = 0;
	Random rand = new Random();
	int currentValue = 0;
	int setValue = 0;
	public static int ScurrentValue = 0;
	public static int SsetValue = 0;
	public static Socket socket;
	public static DatagramSocket client_socket;
	int SoundScreenTempValue;
	public static boolean mRun = false;
	public static String udpMessage;
	
	Thread networkThread;
	public static UsbManager manager;
	public static UsbDeviceConnection connect;

	//Sound
	public static boolean playAudioOnApp;
	MediaPlayer mediaPlayer;
	
	Timer soundTimer;
	TimerTask task;
	Timer soundsoundTimer;
	TimerTask soundtask;
	Timer wifiSymbol;
	TimerTask wifiTask;
	Timer connTest;
	TimerTask connTestTask;

	
	int wifiIntValue = 0; //0 for off, 1 for on

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen_2);

		System.out.println("On Create Called");

		/*
		 * LinearLayout backG = (LinearLayout)
		 * findViewById(R.id.backgroundBack);
		 * backG.setBackgroundResource(R.drawable.background_main);
		 */
		// Sets up the buttons to be able to be paged
		WizardPagerAdapter adapter = new WizardPagerAdapter();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		// Is used for paging between the circle and buttons on sound view on
		// main act
		WizardPagerAdapter2 adapter2 = new WizardPagerAdapter2();
		ViewPager pagerS = (ViewPager) findViewById(R.id.pagerSoundAct);
		pagerS.setAdapter(adapter2);

		// Is used for paging between sound buttons
		WizardPagerAdapter3 adapter3 = new WizardPagerAdapter3();
		ViewPager pagerS1 = (ViewPager) findViewById(R.id.pagerSound);
		pagerS1.setAdapter(adapter3);

		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);

		// final ScheduledThreadPoolExecutor executor = new
		// ScheduledThreadPoolExecutor( 1 );
		/*
		final Timer soundTimer = new Timer();

		final TimerTask task = new TimerTask() {

			@Override
			public void run() {
				BitmapFactory.Options options4 = new BitmapFactory.Options();
				options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Resources res = getResources();
				Bitmap source = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_bar);
				final Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888, true);

				int randomNum = rand.nextInt((2) + 1);

				Canvas canvas = new Canvas(bitmap);
				Bitmap mask = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_nun);
				Bitmap maskBitmap = mask.copy(Bitmap.Config.ARGB_8888, true);
				int w = mask.getWidth();
				Bitmap outlinetest = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_empty);
				Bitmap outlinetestBitmap = outlinetest.copy(
						Bitmap.Config.ARGB_8888, true);
				Paint paint = new Paint();
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));

				int valtoPrint = (w * setValue) / 100;
				if (setValue == 0) {
					if (currentValue >= 0)
						currentValue = currentValue - 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				} else if ((currentValue <= valtoPrint + 3)
						&& (currentValue >= valtoPrint - 3))
					canvas.drawBitmap(maskBitmap, (currentValue + randomNum),
							0, paint);
				else if (currentValue > valtoPrint) {
					currentValue = currentValue - 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				} else {
					currentValue = currentValue + 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				}

				paint.setXfermode(null);
				canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
				outlinetest.recycle();
				// We do not need the mask bitmap anymore.
				mask.recycle();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ImageView timetoTEST = (ImageView) findViewById(R.id.test);
						timetoTEST.setImageBitmap(bitmap);
					}
				});
				source.recycle();
				outlinetestBitmap.recycle();
				// this.cancel();
			}
		};
		*/
		/*
		 * final TimerTask task = new TimerTask() {
		 * 
		 * @Override public void run() { runOnUiThread(new Runnable() {
		 * 
		 * @Override public void run() { BitmapFactory.Options options4 = new
		 * BitmapFactory.Options(); options4.inPreferredConfig =
		 * Bitmap.Config.ARGB_8888; Resources res = getResources(); Bitmap
		 * source = BitmapFactory.decodeResource(res,
		 * R.drawable.volume_bar_bar); Bitmap bitmap =
		 * source.copy(Bitmap.Config.ARGB_8888, true);
		 * 
		 * int randomNum = rand.nextInt((2) + 1);
		 * 
		 * Canvas canvas = new Canvas(bitmap); Bitmap mask =
		 * BitmapFactory.decodeResource(res, R.drawable.volume_bar_nun); Bitmap
		 * maskBitmap = mask.copy(Bitmap.Config.ARGB_8888, true); int w =
		 * mask.getWidth(); Bitmap outlinetest =
		 * BitmapFactory.decodeResource(res, R.drawable.volume_bar_empty);
		 * Bitmap outlinetestBitmap = outlinetest.copy( Bitmap.Config.ARGB_8888,
		 * true); Paint paint = new Paint(); paint.setXfermode(new
		 * PorterDuffXfermode(Mode.SRC_OUT));
		 * 
		 * int valtoPrint = (w * setValue) / 100; if (setValue == 0) { if
		 * (currentValue >= 0) currentValue = currentValue - 4;
		 * canvas.drawBitmap(maskBitmap, currentValue, 0, paint); } else if
		 * ((currentValue <= valtoPrint + 3) && (currentValue >= valtoPrint -
		 * 3)) canvas.drawBitmap(maskBitmap, (currentValue + randomNum), 0,
		 * paint); else if (currentValue > valtoPrint) { currentValue =
		 * currentValue - 4; canvas.drawBitmap(maskBitmap, currentValue, 0,
		 * paint); } else { currentValue = currentValue + 4;
		 * canvas.drawBitmap(maskBitmap, currentValue, 0, paint); }
		 * 
		 * paint.setXfermode(null); canvas.drawBitmap(outlinetestBitmap, 0, 0,
		 * paint); outlinetest.recycle(); // We do not need the mask bitmap
		 * anymore. mask.recycle(); ImageView timetoTEST = (ImageView)
		 * findViewById(R.id.test); timetoTEST.setImageBitmap(bitmap);
		 * 
		 * } }); // this.cancel(); } };
		 */
		
		//soundTimer.scheduleAtFixedRate(task, 0, 50);
		/*
		Timer soundsoundTimer = new Timer();
		
		TimerTask soundtask = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						BitmapFactory.Options options4 = new BitmapFactory.Options();
						options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
						Resources res = getResources();
						Bitmap source = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_bar);
						Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888,
								true);

						Canvas canvas = new Canvas(bitmap);
						Bitmap mask = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_nun);
						Bitmap maskBitmap = mask.copy(Bitmap.Config.ARGB_8888,
								true);
						int w = mask.getWidth();
						Bitmap outlinetest = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_empty);
						Bitmap outlinetestBitmap = outlinetest.copy(
								Bitmap.Config.ARGB_8888, true);
						Paint paint = new Paint();
						paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));

						int valtoPrint = (w * SsetValue) / 100;
						if (SsetValue == 0) {
							if (ScurrentValue >= 0)
								ScurrentValue = ScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						} else if ((ScurrentValue <= valtoPrint + 3)
								&& (ScurrentValue >= valtoPrint - 3))
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						else if (ScurrentValue > valtoPrint) {
							ScurrentValue = ScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						} else {
							ScurrentValue = ScurrentValue + 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						}

						paint.setXfermode(null);
						canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
						outlinetest.recycle();
						// We do not need the mask bitmap anymore.
						mask.recycle();
						ImageView timetoTEST = (ImageView) findViewById(R.id.testS);
						timetoTEST.setImageBitmap(bitmap);

					}
				});
				// this.cancel();
			}
		};
		*/
		//soundsoundTimer.scheduleAtFixedRate(soundtask, 0, 50);
		
		/*
		 * BitmapFactory.Options options = new BitmapFactory.Options();
		 * options.inJustDecodeBounds = true;
		 * BitmapFactory.decodeResource(getResources(),
		 * R.id.imageViewMainSoundWheel, options);
		 * 
		 * ImageView iv = (ImageView) findViewById
		 * (R.id.imageViewMainSoundWheel);
		 * 
		 * iv.setImageBitmap( decodeSampledBitmapFromResource(getResources(),
		 * R.drawable.sound_wheel, 300, 300));
		 */

		Display display = getWindowManager().getDefaultDisplay();
		int screenSizeX = display.getWidth();
		int screenSizeY = display.getHeight();

		// Sets the preferences
		PreferenceManager.setDefaultValues(this, R.xml.settings, true);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		// On first startup gives a Notification that this app was not created
		// by CuriousMarc
		new EULA_and_notice(this).show();

		// Initializes the ip address and port, and connects to it on startup
		Main_activity.ip_address = prefs.getString("prefUserAddress",
				"169.254.1.1");
		Main_activity.ip_port = Integer.parseInt(prefs.getString(
				"prefUserPort", "2000"));
		Main_activity.connection = Integer.parseInt(prefs.getString(
				"connectionType", "3"));
		Main_activity.main1 = prefs.getString("main_button1", "Error");

		/*
		BitmapFactory.Options options4 = new BitmapFactory.Options();
		options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Resources res = getResources();
		Bitmap source = BitmapFactory.decodeResource(res, R.drawable.volume_bar_bar);
		Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888, true);

		Canvas canvas = new Canvas(bitmap);
		Bitmap mask = BitmapFactory.decodeResource(res,
				R.drawable.volume_bar_black);
		Bitmap maskBitmap = mask.copy(Bitmap.Config.ARGB_8888, true);
		Bitmap outlinetest = BitmapFactory.decodeResource(res,
				R.drawable.volume_bar_empty);
		Bitmap outlinetestBitmap = outlinetest.copy(Bitmap.Config.ARGB_8888,
				true);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
		canvas.drawBitmap(maskBitmap, 0, 0, paint);
		paint.setXfermode(null);
		canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
		outlinetest.recycle();
		outlinetestBitmap.recycle();
		// We do not need the mask bitmap anymore.
		mask.recycle();
		maskBitmap.recycle();
		ImageView timetoTEST = (ImageView) findViewById(R.id.test);
		timetoTEST.setImageBitmap(bitmap);
		source.recycle();
		bitmap.recycle();
		*/

		// networkThread = new Thread(new ClientThread()).start();
		//networkThread = new Thread(new ClientThread());

		//networkThread.start();

		// final string_parser ste = new string_parser();

		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText(prefs.getString("mk1_name", "CloseD"));
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText(prefs.getString("mk2_name", "CloseD"));
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText(prefs.getString("mk3_name", "CloseD"));
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText(prefs.getString("mk4_name", "CloseD"));
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText(prefs.getString("mk5_name", "CloseD"));
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText(prefs.getString("mk6_name", "CloseD"));
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText(prefs.getString("mk7_name", "CloseD"));
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText(prefs.getString("mk8_name", "CloseD"));
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText(prefs.getString("mk9_name", "CloseD"));
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText(prefs.getString("mk10_name", "CloseD"));
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText(prefs.getString("mk11_name", "CloseD"));
		Button btn12 = (Button) findViewById(R.id.buttons12); // >> Symbol
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText(prefs.getString("mk12_name", "CloseD"));
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText(prefs.getString("mk13_name", "CloseD"));
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText(prefs.getString("mk14_name", "CloseD"));
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText(prefs.getString("mk15_name", "CloseD"));
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText(prefs.getString("mk16_name", "CloseD"));
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText(prefs.getString("mk17_name", "CloseD"));
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText(prefs.getString("mk18_name", "CloseD"));
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText(prefs.getString("mk19_name", "CloseD"));
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText(prefs.getString("mk20_name", "CloseD"));
		Button btn22 = (Button) findViewById(R.id.buttons22); // << Symbol
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText(prefs.getString("mk21_name", "CloseD"));
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText(prefs.getString("mk22_name", "CloseD"));

		Button btn1S = (Button) findViewById(R.id.buttons1S);
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		Button btn12S = (Button) findViewById(R.id.buttons12S);
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		Button btn22S = (Button) findViewById(R.id.buttons22S);
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		Button btn24S = (Button) findViewById(R.id.buttons24S);

		switch (Main_activity.sound_screen) {
		case 1: // General
			btn1S.setText(prefs.getString("sgk1_name", "Error"));
			btn2S.setText(prefs.getString("sgk2_name", "Error"));
			btn3S.setText(prefs.getString("sgk3_name", "Error"));
			btn4S.setText(prefs.getString("sgk4_name", "Error"));
			btn5S.setText(prefs.getString("sgk5_name", "Error"));
			btn6S.setText(prefs.getString("sgk6_name", "Error"));
			btn7S.setText(prefs.getString("sgk7_name", "Error"));
			btn8S.setText(prefs.getString("sgk8_name", "Error"));
			btn9S.setText(prefs.getString("sgk9_name", "Error"));
			btn10S.setText(prefs.getString("sgk10_name", "Error"));
			btn11S.setText(prefs.getString("sgk11_name", "Error"));
			btn13S.setText(prefs.getString("sgk12_name", "Error"));
			btn14S.setText(prefs.getString("sgk13_name", "Error"));
			btn15S.setText(prefs.getString("sgk14_name", "Error"));
			btn16S.setText(prefs.getString("sgk15_name", "Error"));
			btn17S.setText(prefs.getString("sgk16_name", "Error"));
			btn18S.setText(prefs.getString("sgk17_name", "Error"));
			btn19S.setText(prefs.getString("sgk18_name", "Error"));
			btn20S.setText(prefs.getString("sgk19_name", "Error"));
			btn21S.setText(prefs.getString("sgk20_name", "Error"));
			btn23S.setText(prefs.getString("sgk21_name", "Error"));
			btn24S.setText(prefs.getString("sgk22_name", "Error"));
			imagebank.setText("GENERAL SOUNDS:");
			break;
		case 2: // Chat
			btn1S.setText(prefs.getString("sck1_name", "Error"));
			btn2S.setText(prefs.getString("sck2_name", "Error"));
			btn3S.setText(prefs.getString("sck3_name", "Error"));
			btn4S.setText(prefs.getString("sck4_name", "Error"));
			btn5S.setText(prefs.getString("sck5_name", "Error"));
			btn6S.setText(prefs.getString("sck6_name", "Error"));
			btn7S.setText(prefs.getString("sck7_name", "Error"));
			btn8S.setText(prefs.getString("sck8_name", "Error"));
			btn9S.setText(prefs.getString("sck9_name", "Error"));
			btn10S.setText(prefs.getString("sck10_name", "Error"));
			btn11S.setText(prefs.getString("sck11_name", "Error"));
			btn13S.setText(prefs.getString("sck12_name", "Error"));
			btn14S.setText(prefs.getString("sck13_name", "Error"));
			btn15S.setText(prefs.getString("sck14_name", "Error"));
			btn16S.setText(prefs.getString("sck15_name", "Error"));
			btn17S.setText(prefs.getString("sck16_name", "Error"));
			btn18S.setText(prefs.getString("sck17_name", "Error"));
			btn19S.setText(prefs.getString("sck18_name", "Error"));
			btn20S.setText(prefs.getString("sck19_name", "Error"));
			btn21S.setText(prefs.getString("sck20_name", "Error"));
			btn23S.setText(prefs.getString("sck21_name", "Error"));
			btn24S.setText(prefs.getString("sck22_name", "Error"));
			imagebank.setText("CHAT SOUNDS:");
			break;
		case 3: // Happy
			btn1S.setText(prefs.getString("shk1_name", "Error"));
			btn2S.setText(prefs.getString("shk2_name", "Error"));
			btn3S.setText(prefs.getString("shk3_name", "Error"));
			btn4S.setText(prefs.getString("shk4_name", "Error"));
			btn5S.setText(prefs.getString("shk5_name", "Error"));
			btn6S.setText(prefs.getString("shk6_name", "Error"));
			btn7S.setText(prefs.getString("shk7_name", "Error"));
			btn8S.setText(prefs.getString("shk8_name", "Error"));
			btn9S.setText(prefs.getString("shk9_name", "Error"));
			btn10S.setText(prefs.getString("shk10_name", "Error"));
			btn11S.setText(prefs.getString("shk11_name", "Error"));
			btn13S.setText(prefs.getString("shk12_name", "Error"));
			btn14S.setText(prefs.getString("shk13_name", "Error"));
			btn15S.setText(prefs.getString("shk14_name", "Error"));
			btn16S.setText(prefs.getString("shk15_name", "Error"));
			btn17S.setText(prefs.getString("shk16_name", "Error"));
			btn18S.setText(prefs.getString("shk17_name", "Error"));
			btn19S.setText(prefs.getString("shk18_name", "Error"));
			btn20S.setText(prefs.getString("shk19_name", "Error"));
			btn21S.setText(prefs.getString("shk20_name", "Error"));
			btn23S.setText(prefs.getString("shk21_name", "Error"));
			btn24S.setText(prefs.getString("shk22_name", "Error"));
			imagebank.setText("HAPPY SOUNDS:");
			break;
		case 4: // Sad
			btn1S.setText(prefs.getString("sdk1_name", "Error"));
			btn2S.setText(prefs.getString("sdk2_name", "Error"));
			btn3S.setText(prefs.getString("sdk3_name", "Error"));
			btn4S.setText(prefs.getString("sdk4_name", "Error"));
			btn5S.setText(prefs.getString("sdk5_name", "Error"));
			btn6S.setText(prefs.getString("sdk6_name", "Error"));
			btn7S.setText(prefs.getString("sdk7_name", "Error"));
			btn8S.setText(prefs.getString("sdk8_name", "Error"));
			btn9S.setText(prefs.getString("sdk9_name", "Error"));
			btn10S.setText(prefs.getString("sdk10_name", "Error"));
			btn11S.setText(prefs.getString("sdk11_name", "Error"));
			btn13S.setText(prefs.getString("sdk12_name", "Error"));
			btn14S.setText(prefs.getString("sdk13_name", "Error"));
			btn15S.setText(prefs.getString("sdk14_name", "Error"));
			btn16S.setText(prefs.getString("sdk15_name", "Error"));
			btn17S.setText(prefs.getString("sdk16_name", "Error"));
			btn18S.setText(prefs.getString("sdk17_name", "Error"));
			btn19S.setText(prefs.getString("sdk18_name", "Error"));
			btn20S.setText(prefs.getString("sdk19_name", "Error"));
			btn21S.setText(prefs.getString("sdk20_name", "Error"));
			btn23S.setText(prefs.getString("sdk21_name", "Error"));
			btn24S.setText(prefs.getString("sdk22_name", "Error"));
			imagebank.setText("SAD SOUNDS:");
			break;
		case 5: // Whistle
			btn1S.setText(prefs.getString("swk1_name", "Error"));
			btn2S.setText(prefs.getString("swk2_name", "Error"));
			btn3S.setText(prefs.getString("swk3_name", "Error"));
			btn4S.setText(prefs.getString("swk4_name", "Error"));
			btn5S.setText(prefs.getString("swk5_name", "Error"));
			btn6S.setText(prefs.getString("swk6_name", "Error"));
			btn7S.setText(prefs.getString("swk7_name", "Error"));
			btn8S.setText(prefs.getString("swk8_name", "Error"));
			btn9S.setText(prefs.getString("swk9_name", "Error"));
			btn10S.setText(prefs.getString("swk10_name", "Error"));
			btn11S.setText(prefs.getString("swk11_name", "Error"));
			btn13S.setText(prefs.getString("swk12_name", "Error"));
			btn14S.setText(prefs.getString("swk13_name", "Error"));
			btn15S.setText(prefs.getString("swk14_name", "Error"));
			btn16S.setText(prefs.getString("swk15_name", "Error"));
			btn17S.setText(prefs.getString("swk16_name", "Error"));
			btn18S.setText(prefs.getString("swk17_name", "Error"));
			btn19S.setText(prefs.getString("swk18_name", "Error"));
			btn20S.setText(prefs.getString("swk19_name", "Error"));
			btn21S.setText(prefs.getString("swk20_name", "Error"));
			btn23S.setText(prefs.getString("swk21_name", "Error"));
			btn24S.setText(prefs.getString("swk22_name", "Error"));
			imagebank.setText("WHISTLE SOUNDS:");
			break;
		case 6: // Scream
			btn1S.setText(prefs.getString("ssk1_name", "Error"));
			btn2S.setText(prefs.getString("ssk2_name", "Error"));
			btn3S.setText(prefs.getString("ssk3_name", "Error"));
			btn4S.setText(prefs.getString("ssk4_name", "Error"));
			btn5S.setText(prefs.getString("ssk5_name", "Error"));
			btn6S.setText(prefs.getString("ssk6_name", "Error"));
			btn7S.setText(prefs.getString("ssk7_name", "Error"));
			btn8S.setText(prefs.getString("ssk8_name", "Error"));
			btn9S.setText(prefs.getString("ssk9_name", "Error"));
			btn10S.setText(prefs.getString("ssk10_name", "Error"));
			btn11S.setText(prefs.getString("ssk11_name", "Error"));
			btn13S.setText(prefs.getString("ssk12_name", "Error"));
			btn14S.setText(prefs.getString("ssk13_name", "Error"));
			btn15S.setText(prefs.getString("ssk14_name", "Error"));
			btn16S.setText(prefs.getString("ssk15_name", "Error"));
			btn17S.setText(prefs.getString("ssk16_name", "Error"));
			btn18S.setText(prefs.getString("ssk17_name", "Error"));
			btn19S.setText(prefs.getString("ssk18_name", "Error"));
			btn20S.setText(prefs.getString("ssk19_name", "Error"));
			btn21S.setText(prefs.getString("ssk20_name", "Error"));
			btn23S.setText(prefs.getString("ssk21_name", "Error"));
			btn24S.setText(prefs.getString("ssk22_name", "Error"));
			imagebank.setText("SCREAM SOUNDS:");
			break;
		case 7: // Leia
			btn1S.setText(prefs.getString("slk1_name", "Error"));
			btn2S.setText(prefs.getString("slk2_name", "Error"));
			btn3S.setText(prefs.getString("slk3_name", "Error"));
			btn4S.setText(prefs.getString("slk4_name", "Error"));
			btn5S.setText(prefs.getString("slk5_name", "Error"));
			btn6S.setText(prefs.getString("slk6_name", "Error"));
			btn7S.setText(prefs.getString("slk7_name", "Error"));
			btn8S.setText(prefs.getString("slk8_name", "Error"));
			btn9S.setText(prefs.getString("slk9_name", "Error"));
			btn10S.setText(prefs.getString("slk10_name", "Error"));
			btn11S.setText(prefs.getString("slk11_name", "Error"));
			btn13S.setText(prefs.getString("slk12_name", "Error"));
			btn14S.setText(prefs.getString("slk13_name", "Error"));
			btn15S.setText(prefs.getString("slk14_name", "Error"));
			btn16S.setText(prefs.getString("slk15_name", "Error"));
			btn17S.setText(prefs.getString("slk16_name", "Error"));
			btn18S.setText(prefs.getString("slk17_name", "Error"));
			btn19S.setText(prefs.getString("slk18_name", "Error"));
			btn20S.setText(prefs.getString("slk19_name", "Error"));
			btn21S.setText(prefs.getString("slk20_name", "Error"));
			btn23S.setText(prefs.getString("slk21_name", "Error"));
			btn24S.setText(prefs.getString("slk22_name", "Error"));
			imagebank.setText("LEIA SOUNDS:");
			break;
		case 8: // Music
			btn1S.setText(prefs.getString("smk1_name", "Error"));
			btn2S.setText(prefs.getString("smk2_name", "Error"));
			btn3S.setText(prefs.getString("smk3_name", "Error"));
			btn4S.setText(prefs.getString("smk4_name", "Error"));
			btn5S.setText(prefs.getString("smk5_name", "Error"));
			btn6S.setText(prefs.getString("smk6_name", "Error"));
			btn7S.setText(prefs.getString("smk7_name", "Error"));
			btn8S.setText(prefs.getString("smk8_name", "Error"));
			btn9S.setText(prefs.getString("smk9_name", "Error"));
			btn10S.setText(prefs.getString("smk10_name", "Error"));
			btn11S.setText(prefs.getString("smk11_name", "Error"));
			btn13S.setText(prefs.getString("smk12_name", "Error"));
			btn14S.setText(prefs.getString("smk13_name", "Error"));
			btn15S.setText(prefs.getString("smk14_name", "Error"));
			btn16S.setText(prefs.getString("smk15_name", "Error"));
			btn17S.setText(prefs.getString("smk16_name", "Error"));
			btn18S.setText(prefs.getString("smk17_name", "Error"));
			btn19S.setText(prefs.getString("smk18_name", "Error"));
			btn20S.setText(prefs.getString("smk19_name", "Error"));
			btn21S.setText(prefs.getString("smk20_name", "Error"));
			btn23S.setText(prefs.getString("smk21_name", "Error"));
			btn24S.setText(prefs.getString("smk22_name", "Error"));
			imagebank.setText("MUSIC SOUNDS:");
			break;
		default:
			btn1S.setText(prefs.getString("sgk1_name", "Error"));
			btn2S.setText(prefs.getString("sgk2_name", "Error"));
			btn3S.setText(prefs.getString("sgk3_name", "Error"));
			btn4S.setText(prefs.getString("sgk4_name", "Error"));
			btn5S.setText(prefs.getString("sgk5_name", "Error"));
			btn6S.setText(prefs.getString("sgk6_name", "Error"));
			btn7S.setText(prefs.getString("sgk7_name", "Error"));
			btn8S.setText(prefs.getString("sgk8_name", "Error"));
			btn9S.setText(prefs.getString("sgk9_name", "Error"));
			btn10S.setText(prefs.getString("sgk10_name", "Error"));
			btn11S.setText(prefs.getString("sgk11_name", "Error"));
			btn13S.setText(prefs.getString("sgk12_name", "Error"));
			btn14S.setText(prefs.getString("sgk13_name", "Error"));
			btn15S.setText(prefs.getString("sgk14_name", "Error"));
			btn16S.setText(prefs.getString("sgk15_name", "Error"));
			btn17S.setText(prefs.getString("sgk16_name", "Error"));
			btn18S.setText(prefs.getString("sgk17_name", "Error"));
			btn19S.setText(prefs.getString("sgk18_name", "Error"));
			btn20S.setText(prefs.getString("sgk19_name", "Error"));
			btn21S.setText(prefs.getString("sgk20_name", "Error"));
			btn23S.setText(prefs.getString("sgk21_name", "Error"));
			btn24S.setText(prefs.getString("sgk22_name", "Error"));
			imagebank.setText("GENERAL SOUNDS:");
		}

		TextView line1 = (TextView) findViewById(R.id.textView5);
		// TextView line2 = (TextView) findViewById (R.id.textViewSound_title);
		TextView line3 = (TextView) findViewById(R.id.textView_soundbank);
		TextView mainViewPager1st = (TextView) findViewById(R.id.textViewViewPager1stPage);
		TextView mainViewPager2nd = (TextView) findViewById(R.id.textViewViewPager2ndPage);
		TextView mainSoundPager1st = (TextView) findViewById(R.id.textView_soundbank);
		TextView mainSoundPager2nd = (TextView) findViewById(R.id.textViewViewPager2ndPageS);
		//ImageButton butmainoff = (ImageButton) findViewById(R.id.button_off);
		Button butquiet = (Button) findViewById(R.id.button_quiet);
		Button butsndon = (Button) findViewById(R.id.button_snd_on);
		Button butholoon = (Button) findViewById(R.id.button_holo_on);
		//ImageButton butsoundoff = (ImageButton) findViewById(R.id.butoff);
		Button butless = (Button) findViewById(R.id.butless);
		Button butmid = (Button) findViewById(R.id.butmid);
		Button butmore = (Button) findViewById(R.id.butmore);
		
		setTextSize(screenSizeX);
		/*
		Button but1 = (Button) findViewById(R.id.buttons1);
		Button but2 = (Button) findViewById(R.id.buttons2);
		Button but3 = (Button) findViewById(R.id.buttons3);
		Button but4 = (Button) findViewById(R.id.buttons4);
		Button but5 = (Button) findViewById(R.id.buttons5);
		Button but6 = (Button) findViewById(R.id.buttons6);
		Button but7 = (Button) findViewById(R.id.buttons7);
		Button but8 = (Button) findViewById(R.id.buttons8);
		Button but9 = (Button) findViewById(R.id.buttons9);
		Button but10 = (Button) findViewById(R.id.buttons10);
		Button but11 = (Button) findViewById(R.id.buttons11);
		Button but12 = (Button) findViewById(R.id.buttons12);
		Button but13 = (Button) findViewById(R.id.buttons13);
		Button but14 = (Button) findViewById(R.id.buttons14);
		Button but15 = (Button) findViewById(R.id.buttons15);
		Button but16 = (Button) findViewById(R.id.buttons16);
		Button but17 = (Button) findViewById(R.id.buttons17);
		Button but18 = (Button) findViewById(R.id.buttons18);
		Button but19 = (Button) findViewById(R.id.buttons19);
		Button but20 = (Button) findViewById(R.id.buttons20);
		Button but21 = (Button) findViewById(R.id.buttons21);
		Button but22 = (Button) findViewById(R.id.buttons22);
		Button but23 = (Button) findViewById(R.id.buttons23);
		Button but24 = (Button) findViewById(R.id.buttons24);
		*/
		final ImageView sv = (ImageView) findViewById(R.id.imageViewR2_wire);
		final ImageView rvsel = (ImageView) findViewById(R.id.imageViewR2_wire_sel);
		if (sv != null) {
			sv.setOnTouchListener(this);
		}
		/*
		 final ViewTreeObserver observer= sv.getViewTreeObserver();
		 observer.addOnGlobalLayoutListener( new
		 ViewTreeObserver.OnGlobalLayoutListener() {
		 
		 @SuppressWarnings("deprecation")
		 
		 @Override public void onGlobalLayout() { Log.d("Log", "Height: " +
		 sv.getHeight()); Log.d("Log", "Width: " + sv.getWidth()); imageWidth
		 = sv.getWidth(); imageHeight = sv.getHeight(); sv.setImageBitmap(
		 decodeSampledBitmapFromResource(getResources(),
		 R.drawable.r2_wireframe, imageWidth, imageHeight));
		 
		 sv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		 BitmapWorkerTask task = new BitmapWorkerTask(sv);
		 task.execute(R.drawable.r2_wireframe); } });
		*/
		//imageWidth = sv.getWidth();
		//imageHeight = sv.getHeight();
		//sv.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.r2_wireframe, imageWidth, imageHeight));
		Picasso.with(this).load(R.drawable.r2_wireframe).into(sv);
		Picasso.with(this).load(R.drawable.r2_wireframe_selection).into(rvsel);
		//sv.setImageDrawable(R.drawable.r2_wireframe);
		//Drawable testingdraw = getResources().getDrawable(R.drawable.r2_wireframe);
		//sv.setImageDrawable(testingdraw);
		//Drawable testingframe = getResources().getDrawable(R.drawable.r2_wireframe_selection);
		//rvsel.setImageDrawable(testingframe);
		//Bitmap.createScaledBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.r2_wireframe, imageWidth, imageHeight), imageWidth, imageHeight, false);
		final ImageView soundchoose = (ImageView) findViewById(R.id.imageViewMainSoundWheel);
		final ImageView soundchooseSel = (ImageView) findViewById(R.id.imageViewMainSoundSelWheel);
		Picasso.with(this).load(R.drawable.sound_wheel_selection_wheel)
				.into(soundchooseSel);
		Picasso.with(this).load(R.drawable.sound_wheel).into(soundchoose);
		/*
		 final ViewTreeObserver observer2 = soundchoose.getViewTreeObserver();
		 observer2.addOnGlobalLayoutListener( new
		 ViewTreeObserver.OnGlobalLayoutListener() {
		 
		 @Override public void onGlobalLayout() { Log.d("Log", "Height: " +
		 soundchoose.getHeight()); Log.d("Log", "Width: " +
		 soundchoose.getWidth()); imageWidthS = soundchoose.getWidth();
		 imageHeightS = soundchoose.getHeight(); soundchoose.setImageBitmap(
		 decodeSampledBitmapFromResource(getResources(),
		 R.drawable.sound_wheel, imageWidthS, imageHeightS));
		 soundchooseSel.
		 setImageBitmap(decodeSampledBitmapFromResource(getResources(),
		 R.drawable.sound_wheel_selection_wheel, imageWidthS, imageHeightS));
		 soundchoose.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		 } });
		 */
		if (soundchoose != null) {
			soundchoose.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent ev) {
					boolean handledHere = false;
					Context context2 = getApplicationContext();
					final int action = ev.getAction();
					int tolerance = 25;
					final int evX = (int) ev.getX();
					final int evY = (int) ev.getY();
					int nextImage = -1; // resource id of the next image to
										// display

					final string_parser ste = new string_parser();

					// If we cannot find the imageView, return.
					ImageView imageView = (ImageView) v
							.findViewById(R.id.imageViewMainSoundWheel);
					int iHeight = imageView.getHeight();
					int iWidth = imageView.getWidth();
					if (imageView == null)
						return false;

					// When the action is Down, see if we should show the
					// "pressed" image for the default image.
					// We do this when the default image is showing. That
					// condition is detectable by looking at the
					// tag of the view. If it is null or contains the resource
					// number of the default image, display the pressed image.
					Integer tagNum = (Integer) imageView.getTag();
					int currentResource = (tagNum == null) ? R.drawable.sound_wheel
							: tagNum.intValue();

					// Now that we know the current resource being displayed we
					// can handle the DOWN and UP events.

					switch (action) {
					case MotionEvent.ACTION_DOWN:
						// if (currentResource == R.drawable.sound_wheel) {
						// nextImage = R.drawable.p2_ship_pressed;
						// handledHere = true;
						/*
						 * } else if (currentResource !=
						 * R.drawable.p2_ship_default) { nextImage =
						 * R.drawable.p2_ship_default; handledHere = true;
						 */
						// } else handledHere = true;
						SoundScreenTempValue = sound_screen;
						
						int touchColorI = getHotspotColor(
								R.id.imageViewMainSoundSelWheel, evX, evY);

						ColorTool ctI = new ColorTool();

						// nextImage = R.drawable.p2_ship_default;
						if (ctI.closeMatch(Color.BLACK, touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_auto_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_auto_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_auto_select)
							// .into(imageView);
						}// Auto
						else if (ctI.closeMatch(Color.WHITE, touchColorI,
								tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel, iWidth,
											iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel)
							// .into(imageView);
						} // Default
						else if (ctI.closeMatch(Color.argb(255, 255, 255, 51),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_gen_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_gen_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_gen_select)
							// .into(imageView);
							change_to_gen();
						} // General
						else if (ctI.closeMatch(Color.argb(255, 255, 153, 0),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_chat_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_chat_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_chat_select)
							// .into(imageView);
							change_to_chat();
						} // Chat
						else if (ctI.closeMatch(Color.argb(255, 255, 51, 51),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_happy_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_happy_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_happy_select)
							// .into(imageView);
							change_to_happy();
						} // Happy
						else if (ctI.closeMatch(Color.argb(255, 255, 51, 255),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_sad_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_sad_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_sad_select)
							// .into(imageView);
							change_to_sad();
						} // Sad
						else if (ctI.closeMatch(Color.argb(255, 51, 255, 255),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_whis_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_whis_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_whis_select)
							// .into(imageView);
							change_to_whis();
						} // Whisper
						else if (ctI.closeMatch(Color.argb(255, 102, 0, 255),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_scream_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_scream_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_scream_select)
							// .into(imageView);
							change_to_scream();
						} // Scream
						else if (ctI.closeMatch(Color.argb(255, 255, 0, 153),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_leia_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_leia_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_leia_select)
							// .into(imageView);
							change_to_leia();
						} // Leia
						else if (ctI.closeMatch(Color.argb(255, 153, 255, 102),
								touchColorI, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_mus_select);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_mus_select,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_mus_select)
							// .into(imageView);
							change_to_mus();
						} // Music
							// If the next image is the same as the last image,
							// go back to the default.
							// toast ("Current image: " + currentResource +
							// " next: " + nextImage);
							// if (currentResource == nextImage) {
							// nextImage = R.drawable.p2_ship_default;
							// }
						handledHere = true;
						break;

					case MotionEvent.ACTION_UP:
						// On the UP, we do the click action.
						// The hidden image (image_areas) has three different
						// hotspots on it.
						// The colors are red, blue, and yellow.
						// Use image_areas to determine which region the user
						// touched.
						int touchColor = 0;
						
						try {
							touchColor = getHotspotColor(R.id.imageViewMainSoundSelWheel, evX, evY);
						}
						catch (Exception e){
							touchColor = Color.argb(255, 0, 255, 255);
						}

						// Compare the touchColor to the expected values. Switch
						// to a different image, depending on what color was
						// touched.
						// Note that we use a Color Tool object to test whether
						// the observed color is close enough to the real color
						// to
						// count as a match. We do this because colors on the
						// screen do not match the map exactly because of
						// scaling and
						// varying pixel density.
						ColorTool ct = new ColorTool();
						// nextImage = R.drawable.p2_ship_default;
						if (ct.closeMatch(Color.BLACK, touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_auto_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_auto_selected,
											iWidth, iHeight));

							ste.select_string("$R");
							Main_activity.randomModeOn = true;
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_auto_selected)
							// .into(imageView);
						}// Auto
						else if (ct.closeMatch(Color.argb(255, 255, 255, 51),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_gen_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_gen_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_gen_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 1) && (Main_activity.sound_screen == 1)) {
								ste.select_string("$1");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 1;
								change_to_gen();
							}
						} // General
						else if (ct.closeMatch(Color.argb(255, 255, 153, 0),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_chat_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_chat_selected,
											iWidth, iHeight));
							if ((SoundScreenTempValue == 2) && (Main_activity.sound_screen == 2)) {
								ste.select_string("$2");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 2;
								change_to_chat();
							}
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_chat_selected)
							// .into(imageView);
						} // Chat
						else if (ct.closeMatch(Color.argb(255, 255, 51, 51),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_happy_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_happy_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_happy_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 3) && (Main_activity.sound_screen == 3)) {
								ste.select_string("$3");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 3;
								change_to_happy();
							}
						} // Happy
						else if (ct.closeMatch(Color.argb(255, 255, 51, 255),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_sad_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_sad_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_sad_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 4) && (Main_activity.sound_screen == 4)) {
								ste.select_string("$4");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 4;
								change_to_sad();
							}
						} // Sad
						else if (ct.closeMatch(Color.argb(255, 51, 255, 255),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_whis_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_whis_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_whis_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 5) && (Main_activity.sound_screen == 5)) {
								ste.select_string("$5");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 5;
								change_to_whis();
							}
						} // Whisper
						else if (ct.closeMatch(Color.argb(255, 102, 0, 255),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_scream_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_scream_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_scream_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 6) && (Main_activity.sound_screen == 6)) {
								ste.select_string("$6");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 6;
								change_to_scream();
							}
						} // Scream
						else if (ct.closeMatch(Color.argb(255, 255, 0, 153),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_leia_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_leia_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_leia_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 7) && (Main_activity.sound_screen == 7)) {
								ste.select_string("$7");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 7;
								change_to_leia();
							}
						} // Leia
						else if (ct.closeMatch(Color.argb(255, 153, 255, 102),
								touchColor, tolerance)) {
							// imageView.setImageResource(R.drawable.sound_wheel_mus_selected);
							imageView
									.setImageBitmap(decodeSampledBitmapFromResource(
											getResources(),
											R.drawable.sound_wheel_mus_selected,
											iWidth, iHeight));
							// Picasso.with(getApplicationContext())
							// .load(R.drawable.sound_wheel_mus_selected)
							// .into(imageView);
							if ((SoundScreenTempValue == 8) && (Main_activity.sound_screen == 8)) {
								ste.select_string("$8");
							} else {
								if (Main_activity.randomModeOn == true) {
									ste.select_string("$s");
									Main_activity.randomModeOn = false;
								}
								SoundScreenTempValue = 8;
								change_to_mus();
							}
						} // Music
							// If the next image is the same as the last image,
							// go
							// back to the default.
							// toast ("Current image: " + currentResource +
							// " next: " + nextImage);
							// if (currentResource == nextImage) {
							// nextImage = R.drawable.p2_ship_default;
							// }
						handledHere = true;
						break;

					default:
						handledHere = false;
					} // end switch

					if (handledHere) {

						if (nextImage > 0) {
							imageView.setImageResource(nextImage);
							imageView.setTag(nextImage);
						}
					}
					return handledHere;
				}

			});
		}
	}

	public boolean onTouch(View v, MotionEvent ev) {
		boolean handledHere = false;

		final int action = ev.getAction();
		int tolerance = 25;
		final int evX = (int) ev.getX();
		final int evY = (int) ev.getY();
		int nextImage = -1; // resource id of the next image to display

		// If we cannot find the imageView, return.
		ImageView imageView = (ImageView) v.findViewById(R.id.imageViewR2_wire);
		if (imageView == null)
			return false;

		// When the action is Down, see if we should show the "pressed" image
		// for the default image.
		// We do this when the default image is showing. That condition is
		// detectable by looking at the
		// tag of the view. If it is null or contains the resource number of the
		// default image, display the pressed image.
		Integer tagNum = (Integer) imageView.getTag();
		int currentResource = (tagNum == null) ? R.drawable.r2_wireframe
				: tagNum.intValue();

		Intent nextScreen = new Intent(getApplicationContext(),
				Sound_activity2.class);

		// Now that we know the current resource being displayed we can handle
		// the DOWN and UP events.

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			handledHere = true;
			break;

		case MotionEvent.ACTION_UP:
			// On the UP, we do the click action.
			// The hidden image (image_areas) has three different hotspots on
			// it.
			// The colors are red, blue, and yellow.
			// Use image_areas to determine which region the user touched.
			int touchColor = 0;

			try {
				touchColor = getHotspotColor(R.id.imageViewR2_wire_sel, evX, evY);
			}
			catch (Exception e){
				touchColor = 0;
			}
			// Compare the touchColor to the expected values. Switch to a
			// different image, depending on what color was touched.
			// Note that we use a Color Tool object to test whether the observed
			// color is close enough to the real color to
			// count as a match. We do this because colors on the screen do not
			// match the map exactly because of scaling and
			// varying pixel density.
			ColorTool ct = new ColorTool();
			// nextImage = R.drawable.p2_ship_default;

			if (ct.closeMatch(Color.argb(255, 0, 0, 255), touchColor, tolerance)) {
				Intent nextScreenPanel = new Intent(getApplicationContext(),
						Panel_activity.class);
				startActivity(nextScreenPanel);

			} // Panel

			if (ct.closeMatch(Color.argb(255, 255, 0, 255), touchColor,
					tolerance)) {
				// Intent i = new Intent(getApplicationContext(),
				// Wireless_activity.class);
				// startActivityForResult(i, 1);
				Intent nextScreenLED = new Intent(getApplicationContext(),
						LED_activity.class);
				startActivity(nextScreenLED);
			} // Logic

			if (ct.closeMatch(Color.argb(255, 255, 0, 0), touchColor, tolerance)) {
				Intent nextScreenHP = new Intent(getApplicationContext(),
						HP_activity.class);
				startActivity(nextScreenHP);

				// Intent testScreen = new Intent(getApplicationContext(),
				// test_activity.class);
				// startActivity(testScreen);
			} // Holo

			if (ct.closeMatch(Color.argb(255, 0, 255, 0), touchColor, tolerance)) {
				startActivity(nextScreen);
			} // Sound

			// If the next image is the same as the last image, go back to the
			// default.
			// toast ("Current image: " + currentResource + " next: " +
			// nextImage);
			// if (currentResource == nextImage) {
			// nextImage = R.drawable.p2_ship_default;
			// }
			handledHere = true;
			break;

		default:
			handledHere = false;
		} // end switch

		if (handledHere) {

			if (nextImage > 0) {
				imageView.setImageResource(nextImage);
				imageView.setTag(nextImage);
			}
		}
		return handledHere;
	}

	public int getHotspotColor(int hotspotId, int x, int y) {
		ImageView img = (ImageView) findViewById(hotspotId);
		if (img == null) {
			//Log.d("ImageAreasActivity", "Hot spot image not found");
			return 0;
		} else {
			img.setDrawingCacheEnabled(true);
			Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
			if (hotspots == null) {
				//Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
				return 0;
			} else {
				img.setDrawingCacheEnabled(false);
				return hotspots.getPixel(x, y);
			}
		}
	}

	public void onStop() {
		System.out.println("On Stop Called");
		mediaPlayer.release();
		// finish();
		super.onStop();
	}

	public void onPause() {
		System.out.println("On Pause Called");
		
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt("SoundBar", ScurrentValue);
        editor.putInt("ActivityBar", currentValue);
        editor.putInt("ActivitySet", setValue);
        editor.putInt("SoundSet", SsetValue);
        editor.commit();
        
        mediaPlayer.release();
		soundTimer.cancel();
		soundsoundTimer.cancel();
		wifiSymbol.cancel();
		
		ImageView wifiImage = (ImageView) findViewById(R.id.button_wifi);
		wifiImage.setImageResource(R.drawable.wifi);
		wifiIntValue = 0;
		
		super.onPause();
	}
	
	public void onDestroy() {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt("SoundBar", 0);
        editor.putInt("ActivityBar", 0);
        editor.putInt("ActivitySet", 0);
        editor.putInt("SoundSet", 0);
        editor.commit();
		
        mediaPlayer.release();
        
        connTest.cancel();
        
        if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				
			}
		}
		if (client_socket != null) {
			try {
				client_socket.close();
			} catch (Exception e) {
				
			}
		}
		
		networkThread = null;
		socket = null;
		client_socket = null;
        
		super.onDestroy();
	}

	public void onStart() {
		System.out.println("On Start Called");
		super.onStart();
	}

	public void onResume() {
		super.onResume();

		//networkThread = new Thread(new ClientThread());
		//networkThread.start();
		
		ScurrentValue = PreferenceManager.getDefaultSharedPreferences(this).getInt("SoundBar", 0);
		currentValue = PreferenceManager.getDefaultSharedPreferences(this).getInt("ActivityBar", 0);
		SsetValue = PreferenceManager.getDefaultSharedPreferences(this).getInt("SoundSet", 0);
		setValue = PreferenceManager.getDefaultSharedPreferences(this).getInt("ActivitySet", 0);
		connection = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("connectionType", "3"));
		
		//Get if the sound should be played through bluetooth or app
		playAudioOnApp = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("setAppSound", false);
		mediaPlayer = new MediaPlayer();
		
		wifiSymbol = new Timer();
		wifiTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ImageView wifiImage = (ImageView) findViewById(R.id.button_wifi);
						
						if (wifiIntValue == 0) {
							wifiImage.setImageResource(R.drawable.wifi_light);
							wifiIntValue = 1;
						}
						else {
							wifiImage.setImageResource(R.drawable.wifi);
							wifiIntValue = 0;
						}
					}
				});
			}
		};
		
		soundTimer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				BitmapFactory.Options options4 = new BitmapFactory.Options();
				options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Resources res = getResources();
				Bitmap source = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_bar);
				final Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888, true);

				int randomNum = rand.nextInt((2) + 1);

				Canvas canvas = new Canvas(bitmap);
				Bitmap mask = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_nun);
				Bitmap maskBitmap = mask.copy(Bitmap.Config.ARGB_8888, true);
				int w = mask.getWidth();
				Bitmap outlinetest = BitmapFactory.decodeResource(res,
						R.drawable.volume_bar_empty);
				Bitmap outlinetestBitmap = outlinetest.copy(
						Bitmap.Config.ARGB_8888, true);
				Paint paint = new Paint();
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));

				int valtoPrint = (w * setValue) / 100;
				if (setValue == 0) {
					if (currentValue >= 0)
						currentValue = currentValue - 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				} else if ((currentValue <= valtoPrint + 3)
						&& (currentValue >= valtoPrint - 3))
					canvas.drawBitmap(maskBitmap, (currentValue + randomNum),
							0, paint);
				else if (currentValue > valtoPrint) {
					currentValue = currentValue - 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				} else {
					currentValue = currentValue + 4;
					canvas.drawBitmap(maskBitmap, currentValue, 0, paint);
				}

				paint.setXfermode(null);
				canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
				outlinetest.recycle();
				// We do not need the mask bitmap anymore.
				mask.recycle();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ImageView timetoTEST = (ImageView) findViewById(R.id.test);
						timetoTEST.setImageBitmap(bitmap);
					}
				});
				source.recycle();
				outlinetestBitmap.recycle();
				// this.cancel();
			}
		};
		
		soundsoundTimer = new Timer();
		soundtask = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						BitmapFactory.Options options4 = new BitmapFactory.Options();
						options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
						Resources res = getResources();
						Bitmap source = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_bar);
						Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888,
								true);

						Canvas canvas = new Canvas(bitmap);
						Bitmap mask = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_nun);
						Bitmap maskBitmap = mask.copy(Bitmap.Config.ARGB_8888,
								true);
						int w = mask.getWidth();
						Bitmap outlinetest = BitmapFactory.decodeResource(res,
								R.drawable.volume_bar_empty);
						Bitmap outlinetestBitmap = outlinetest.copy(
								Bitmap.Config.ARGB_8888, true);
						Paint paint = new Paint();
						paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));

						int valtoPrint = (w * SsetValue) / 100;
						if (SsetValue == 0) {
							if (ScurrentValue >= 0)
								ScurrentValue = ScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						} else if ((ScurrentValue <= valtoPrint + 3)
								&& (ScurrentValue >= valtoPrint - 3))
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						else if (ScurrentValue > valtoPrint) {
							ScurrentValue = ScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						} else {
							ScurrentValue = ScurrentValue + 4;
							canvas.drawBitmap(maskBitmap, ScurrentValue, 0,
									paint);
						}

						paint.setXfermode(null);
						canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
						outlinetest.recycle();
						// We do not need the mask bitmap anymore.
						mask.recycle();
						ImageView timetoTEST = (ImageView) findViewById(R.id.testS);
						timetoTEST.setImageBitmap(bitmap);

					}
				});
				// this.cancel();
			}
		};

		soundTimer.scheduleAtFixedRate(task, 0, 50);
		soundsoundTimer.scheduleAtFixedRate(soundtask, 0, 50);
		
		
		connTest = new Timer();
		connTestTask = new TimerTask() {
			@Override
			public void run() {
				Main_activity.sendstring("" + '\r');
			}
		};
		
		System.out.println("On Resume Called");

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		Main_activity.ip_address = prefs.getString("prefUserAddress",
				"169.254.1.1");
		Main_activity.ip_port = Integer.parseInt(prefs.getString(
				"prefUserPort", "2000"));

		//networkThread = new Thread(new ClientThread());
		//networkThread.start();

		if (socket != null) {
			try {
				socket.close();
			} catch (Exception e) {
				
			}
		}
		if (client_socket != null) {
			try {
				client_socket.close();
			} catch (Exception e) {
				
			}
		}
		/*
		if (driver != null) {
			try {
				driver.close();
			} catch (IOException e) {
				//Ignore
			}
			driver = null;
		}
		*/
		
		networkThread = null;
		socket = null;
		client_socket = null;
		//driver = null;
		manager = null;
		
		if (networkThread == null) {
			networkThread = new Thread(new ClientThread());
			networkThread.start();
		}
		//if (networkThread.isAlive() == false)
			//networkThread.start();
		//networkThread.
		// final string_parser ste = new string_parser ();

		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText(prefs.getString("mk1_name", "CloseD"));
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText(prefs.getString("mk2_name", "CloseD"));
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText(prefs.getString("mk3_name", "CloseD"));
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText(prefs.getString("mk4_name", "CloseD"));
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText(prefs.getString("mk5_name", "CloseD"));
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText(prefs.getString("mk6_name", "CloseD"));
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText(prefs.getString("mk7_name", "CloseD"));
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText(prefs.getString("mk8_name", "CloseD"));
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText(prefs.getString("mk9_name", "CloseD"));
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText(prefs.getString("mk10_name", "CloseD"));
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText(prefs.getString("mk11_name", "CloseD"));
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText(prefs.getString("mk12_name", "CloseD"));
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText(prefs.getString("mk13_name", "CloseD"));
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText(prefs.getString("mk14_name", "CloseD"));
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText(prefs.getString("mk15_name", "CloseD"));
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText(prefs.getString("mk16_name", "CloseD"));
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText(prefs.getString("mk17_name", "CloseD"));
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText(prefs.getString("mk18_name", "CloseD"));
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText(prefs.getString("mk19_name", "CloseD"));
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText(prefs.getString("mk20_name", "CloseD"));
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText(prefs.getString("mk21_name", "CloseD"));
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText(prefs.getString("mk22_name", "CloseD"));

		switch (Main_activity.sound_screen) {
		case 1: // General
			change_to_gen();
			break;
		case 2: // Chat
			change_to_chat();
			break;
		case 3: // Happy
			change_to_happy();
			break;
		case 4: // Sad
			change_to_sad();
			break;
		case 5: // Whistle
			change_to_whis();
			break;
		case 6: // Scream
			change_to_scream();
			break;
		case 7: // Leia
			change_to_leia();
			break;
		case 8: // Music
			change_to_mus();
			break;
		default:
			change_to_gen();
		}

	}

	public void change_to_gen() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("sgk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("sgk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("sgk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("sgk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("sgk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("sgk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("sgk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("sgk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("sgk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("sgk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("sgk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("sgk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("sgk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("sgk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("sgk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("sgk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("sgk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("sgk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("sgk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("sgk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("sgk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("sgk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("GENERAL SOUNDS:");
		Main_activity.sound_screen = 1; // 5 is for whisper
	}

	public void change_to_whis() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("swk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("swk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("swk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("swk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("swk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("swk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("swk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("swk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("swk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("swk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("swk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("swk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("swk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("swk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("swk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("swk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("swk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("swk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("swk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("swk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("swk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("swk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("WHISPER SOUNDS:");
		Main_activity.sound_screen = 5; // 5 is for whisper
	}

	public void change_to_leia() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("slk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("slk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("slk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("slk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("slk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("slk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("slk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("slk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("slk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("slk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("slk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("slk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("slk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("slk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("slk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("slk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("slk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("slk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("slk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("slk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("slk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("slk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("LEIA SOUNDS:");
		Main_activity.sound_screen = 7; // 5 is for whisper
	}

	public void change_to_scream() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("ssk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("ssk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("ssk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("ssk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("ssk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("ssk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("ssk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("ssk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("ssk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("ssk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("ssk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("ssk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("ssk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("ssk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("ssk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("ssk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("ssk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("ssk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("ssk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("ssk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("ssk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("ssk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("SCREAM SOUNDS:");
		Main_activity.sound_screen = 6; // 6 is for scream
	}

	public void change_to_sad() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("sdk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("sdk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("sdk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("sdk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("sdk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("sdk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("sdk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("sdk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("sdk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("sdk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("sdk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("sdk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("sdk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("sdk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("sdk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("sdk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("sdk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("sdk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("sdk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("sdk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("sdk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("sdk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("SAD SOUNDS:");
		Main_activity.sound_screen = 4; // 4 is for sad
	}

	public void change_to_happy() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("shk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("shk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("shk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("shk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("shk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("shk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("shk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("shk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("shk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("shk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("shk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("shk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("shk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("shk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("shk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("shk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("shk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("shk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("shk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("shk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("shk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("shk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("HAPPY SOUNDS:");
		Main_activity.sound_screen = 3; // 5 is for whisper
	}

	public void change_to_chat() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("sck1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("sck2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("sck3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("sck4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("sck5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("sck6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("sck7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("sck8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("sck9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("sck10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("sck11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("sck12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("sck13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("sck14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("sck15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("sck16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("sck17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("sck18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("sck19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("sck20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("sck21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("sck22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("CHAT SOUNDS:");
		Main_activity.sound_screen = 2; // 5 is for whisper
	}

	public void change_to_mus() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		btn1S.setText(prefs.getString("smk1_name", "Error"));
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		btn2S.setText(prefs.getString("smk2_name", "Error"));
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		btn3S.setText(prefs.getString("smk3_name", "Error"));
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		btn4S.setText(prefs.getString("smk4_name", "Error"));
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		btn5S.setText(prefs.getString("smk5_name", "Error"));
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		btn6S.setText(prefs.getString("smk6_name", "Error"));
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		btn7S.setText(prefs.getString("smk7_name", "Error"));
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		btn8S.setText(prefs.getString("smk8_name", "Error"));
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		btn9S.setText(prefs.getString("smk9_name", "Error"));
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		btn10S.setText(prefs.getString("smk10_name", "Error"));
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		btn11S.setText(prefs.getString("smk11_name", "Error"));
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		btn13S.setText(prefs.getString("smk12_name", "Error"));
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		btn14S.setText(prefs.getString("smk13_name", "Error"));
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		btn15S.setText(prefs.getString("smk14_name", "Error"));
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		btn16S.setText(prefs.getString("smk15_name", "Error"));
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		btn17S.setText(prefs.getString("smk16_name", "Error"));
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		btn18S.setText(prefs.getString("smk17_name", "Error"));
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		btn19S.setText(prefs.getString("smk18_name", "Error"));
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		btn20S.setText(prefs.getString("smk19_name", "Error"));
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		btn21S.setText(prefs.getString("smk20_name", "Error"));
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		btn23S.setText(prefs.getString("smk21_name", "Error"));
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		btn24S.setText(prefs.getString("smk22_name", "Error"));
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("MUSIC SOUNDS:");
		Main_activity.sound_screen = 8; // 5 is for whisper
	}

	public void setbutto() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);

		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText(prefs.getString("main_button1name", "CloseD"));
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText(prefs.getString("main_button2name", "CloseD"));
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText(prefs.getString("main_button3name", "CloseD"));
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText(prefs.getString("main_button4name", "CloseD"));
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText(prefs.getString("main_button5name", "CloseD"));
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText(prefs.getString("main_button6name", "CloseD"));
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText(prefs.getString("main_button7name", "CloseD"));
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText(prefs.getString("main_button8name", "CloseD"));
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText(prefs.getString("main_button9name", "CloseD"));
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText(prefs.getString("main_button10name", "CloseD"));
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText(prefs.getString("main_button11name", "CloseD"));
		Button btn12 = (Button) findViewById(R.id.buttons12);
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText(prefs.getString("main_button12name", "CloseD"));
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText(prefs.getString("main_button13name", "CloseD"));
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText(prefs.getString("main_button14name", "CloseD"));
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText(prefs.getString("main_button15name", "CloseD"));
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText(prefs.getString("main_button16name", "CloseD"));
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText(prefs.getString("main_button17name", "CloseD"));
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText(prefs.getString("main_button18name", "CloseD"));
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText(prefs.getString("main_button19name", "CloseD"));
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText(prefs.getString("main_button20name", "CloseD"));
		Button btn22 = (Button) findViewById(R.id.buttons22);
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText(prefs.getString("main_button21name", "CloseD"));
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText(prefs.getString("main_button22name", "CloseD"));
	}

	public void handleButtonClick(View view) {

		final string_parser ste = new string_parser();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Main_activity.this);
		String send = "";
		ViewPager pager = null;
		ViewPager pager2 = null;

		switch (view.getId()) {

		case R.id.button_off:
			ste.select_string(":SE10\r");
			setValue = 0;
			break;
		case R.id.button_quiet:
			ste.select_string(":SE10\r");
			setValue = 33;
			break;
		case R.id.button_snd_on:
			ste.select_string(":SE13\r");
			setValue = 66;
			break;
		case R.id.button_holo_on:
			ste.select_string(":SE14\r");
			setValue = 99;
			break;
		case R.id.buttons1:
			send = prefs.getString("mk1", ":CL00");
			ste.select_string(send);
			break;
		case R.id.buttons2:
			send = prefs.getString("mk2", ":OP00");
			ste.select_string(send);
			break;
		case R.id.buttons3:
			send = prefs.getString("mk3", ":SE03");
			ste.select_string(send);
			break;
		case R.id.buttons4:
			send = prefs.getString("mk4", ":SE08");
			ste.select_string(send);
			break;
		case R.id.buttons5:
			send = prefs.getString("mk5", ":SE07");
			ste.select_string(send);
			break;
		case R.id.buttons6:
			send = prefs.getString("mk6", ":SE05");
			ste.select_string(send);
			break;
		case R.id.buttons7:
			send = prefs.getString("mk7", "*ON00");
			ste.select_string(send);
			break;
		case R.id.buttons8:
			send = prefs.getString("mk8", "*OF00");
			ste.select_string(send);
			break;
		case R.id.buttons9:
			send = prefs.getString("mk9", ":SE06");
			ste.select_string(send);
			break;
		case R.id.buttons10:
			send = prefs.getString("mk10", ":SE01");
			ste.select_string(send);
			break;
		case R.id.buttons11:
			send = prefs.getString("mk11", ":SE15");
			ste.select_string(send);
			break;
		case R.id.buttons12:
			pager = (ViewPager) findViewById(R.id.pager);
			pager.setCurrentItem(1);
			break;
		case R.id.buttons13:
			send = prefs.getString("mk12", ":SE09");
			ste.select_string(send);
			break;
		case R.id.buttons14:
			send = prefs.getString("mk13", ":SE02");
			ste.select_string(send);
			break;
		case R.id.buttons15:
			send = prefs.getString("mk14", ":SE04");
			ste.select_string(send);
			break;
		case R.id.buttons16:
			send = prefs.getString("mk15", ":RC00");
			ste.select_string(send);
			break;
		case R.id.buttons17:
			send = prefs.getString("mk16", ":ST00");
			ste.select_string(send);
			break;
		case R.id.buttons18:
			send = prefs.getString("mk17", "*MF05");
			ste.select_string(send);
			break;
		case R.id.buttons19:
			send = prefs.getString("mk18", ":CL00");
			ste.select_string(send);
			break;
		case R.id.buttons20:
			send = prefs.getString("mk19", ":OP11");
			ste.select_string(send);
			break;
		case R.id.buttons21:
			send = prefs.getString("mk20", ":SE12");
			ste.select_string(send);
			break;
		case R.id.buttons22:
			pager = (ViewPager) findViewById(R.id.pager);
			pager.setCurrentItem(0);
			break;
		case R.id.buttons23:
			send = prefs.getString("mk21", "*RC01");
			ste.select_string(send);
			break;
		case R.id.buttons24:
			send = prefs.getString("mk22", "*RD00");
			ste.select_string(send);
			break;

		case R.id.butoff:
			ste.select_string("$s");
			SsetValue = 0;
			break;

		case R.id.butless:
			if (SsetValue > 0)
				SsetValue = SsetValue - 5;
			if (SsetValue < 0)
				SsetValue = 0;
			ste.select_string("$-");
			break;
		case R.id.butmid:
			SsetValue = 50;
			ste.select_string("$m");
			break;
		case R.id.butmore:
			if (SsetValue < 100)
				SsetValue = SsetValue + 5;
			if (SsetValue > 100)
				SsetValue = 100;
			ste.select_string("$+");
			break;

		case R.id.buttons1S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk1", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("001");
				break;
			case 2: // Chat
				send = prefs.getString("sck1", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("026");
				break;
			case 3: // Happy
				send = prefs.getString("shk1", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("051");
				break;
			case 4: // Sad
				send = prefs.getString("sdk1", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("076");
				break;
			case 5: // Whistle
				send = prefs.getString("swk1", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("101");
				break;
			case 6: // Scream
				send = prefs.getString("ssk1", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("126");
				break;
			case 7: // Leia
				send = prefs.getString("slk1", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("151");
				break;
			case 8: // Music
				send = prefs.getString("smk1", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("176");
				break;
			default:
				send = prefs.getString("sgk1", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("001");
				break;
			}
			break;
		case R.id.buttons2S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk2", "$12");
				ste.select_string(send);
				playAudioThroughAppFunction("002");

				break;
			case 2: // Chat
				send = prefs.getString("sck2", "$22");
				ste.select_string(send);
				playAudioThroughAppFunction("027");
				break;
			case 3: // Happy
				send = prefs.getString("shk2", "$32");
				ste.select_string(send);
				playAudioThroughAppFunction("052");
				break;
			case 4: // Sad
				send = prefs.getString("sdk2", "$42");
				ste.select_string(send);
				playAudioThroughAppFunction("077");
				break;
			case 5: // Whistle
				send = prefs.getString("swk2", "$52");
				ste.select_string(send);
				playAudioThroughAppFunction("102");
				break;
			case 6: // Scream
				send = prefs.getString("ssk2", "$62");
				ste.select_string(send);
				playAudioThroughAppFunction("127");
				break;
			case 7: // Leia
				send = prefs.getString("slk2", "$72");
				ste.select_string(send);
				playAudioThroughAppFunction("152");
				break;
			case 8: // Music
				send = prefs.getString("smk2", "$82");
				ste.select_string(send);
				playAudioThroughAppFunction("177");
				break;
			default:
				send = prefs.getString("sgk2", "$12");
				ste.select_string(send);
				playAudioThroughAppFunction("002");
				break;
			}
			break;
		case R.id.buttons3S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk3", "$13");
				ste.select_string(send);
				playAudioThroughAppFunction("003");
				break;
			case 2: // Chat
				send = prefs.getString("sck3", "$23");
				ste.select_string(send);
				playAudioThroughAppFunction("028");
				break;
			case 3: // Happy
				send = prefs.getString("shk3", "$33");
				ste.select_string(send);
				playAudioThroughAppFunction("053");
				break;
			case 4: // Sad
				send = prefs.getString("sdk3", "$43");
				ste.select_string(send);
				playAudioThroughAppFunction("078");
				break;
			case 5: // Whistle
				send = prefs.getString("swk3", "$53");
				ste.select_string(send);
				playAudioThroughAppFunction("103");
				break;
			case 6: // Scream
				send = prefs.getString("ssk3", "$63");
				ste.select_string(send);
				playAudioThroughAppFunction("128");
				break;
			case 7: // Leia
				send = prefs.getString("slk3", "$73");
				ste.select_string(send);
				playAudioThroughAppFunction("153");
				break;
			case 8: // Music
				send = prefs.getString("smk3", "$83");
				ste.select_string(send);
				playAudioThroughAppFunction("178");
				break;
			default:
				send = prefs.getString("sgk3", "$13");
				ste.select_string(send);
				playAudioThroughAppFunction("003");
				break;
			}
			break;
		case R.id.buttons4S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk4", "$14");
				ste.select_string(send);
				playAudioThroughAppFunction("004");
				break;
			case 2: // Chat
				send = prefs.getString("sck4", "$24");
				ste.select_string(send);
				playAudioThroughAppFunction("029");
				break;
			case 3: // Happy
				send = prefs.getString("shk4", "$34");
				ste.select_string(send);
				playAudioThroughAppFunction("054");
				break;
			case 4: // Sad
				send = prefs.getString("sdk4", "$44");
				ste.select_string(send);
				playAudioThroughAppFunction("079");
				break;
			case 5: // Whistle
				send = prefs.getString("swk4", "$54");
				ste.select_string(send);
				playAudioThroughAppFunction("104");
				break;
			case 6: // Scream
				send = prefs.getString("ssk4", "$64");
				ste.select_string(send);
				playAudioThroughAppFunction("129");
				break;
			case 7: // Leia
				send = prefs.getString("slk4", "$74");
				ste.select_string(send);
				playAudioThroughAppFunction("154");
				break;
			case 8: // Music
				send = prefs.getString("smk4", "$84");
				ste.select_string(send);
				playAudioThroughAppFunction("179");
				break;
			default:
				send = prefs.getString("sgk4", "$14");
				ste.select_string(send);
				playAudioThroughAppFunction("004");
				break;
			}
			break;
		case R.id.buttons5S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk5", "$15");
				ste.select_string(send);
				playAudioThroughAppFunction("005");
				break;
			case 2: // Chat
				send = prefs.getString("sck5", "$25");
				ste.select_string(send);
				playAudioThroughAppFunction("030");
				break;
			case 3: // Happy
				send = prefs.getString("shk5", "$35");
				ste.select_string(send);
				playAudioThroughAppFunction("055");
				break;
			case 4: // Sad
				send = prefs.getString("sdk5", "$45");
				ste.select_string(send);
				playAudioThroughAppFunction("080");
				break;
			case 5: // Whistle
				send = prefs.getString("swk5", "$55");
				ste.select_string(send);
				playAudioThroughAppFunction("105");
				break;
			case 6: // Scream
				send = prefs.getString("ssk5", "$65");
				ste.select_string(send);
				playAudioThroughAppFunction("130");
				break;
			case 7: // Leia
				send = prefs.getString("slk5", "$75");
				ste.select_string(send);
				playAudioThroughAppFunction("155");
				break;
			case 8: // Music
				send = prefs.getString("smk5", "$85");
				ste.select_string(send);
				playAudioThroughAppFunction("180");
				break;
			default:
				send = prefs.getString("sgk5", "$15");
				ste.select_string(send);
				playAudioThroughAppFunction("005");
				break;
			}
			break;
		case R.id.buttons6S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk6", "$16");
				ste.select_string(send);
				playAudioThroughAppFunction("006");
				break;
			case 2: // Chat
				send = prefs.getString("sck6", "$26");
				ste.select_string(send);
				playAudioThroughAppFunction("031");
				break;
			case 3: // Happy
				send = prefs.getString("shk6", "$36");
				ste.select_string(send);
				playAudioThroughAppFunction("056");
				break;
			case 4: // Sad
				send = prefs.getString("sdk6", "$46");
				ste.select_string(send);
				playAudioThroughAppFunction("081");
				break;
			case 5: // Whistle
				send = prefs.getString("swk6", "$56");
				ste.select_string(send);
				playAudioThroughAppFunction("106");
				break;
			case 6: // Scream
				send = prefs.getString("ssk6", "$66");
				ste.select_string(send);
				playAudioThroughAppFunction("131");
				break;
			case 7: // Leia
				send = prefs.getString("slk6", "$76");
				ste.select_string(send);
				playAudioThroughAppFunction("156");
				break;
			case 8: // Music
				send = prefs.getString("smk6", "$86");
				ste.select_string(send);
				playAudioThroughAppFunction("181");
				break;
			default:
				send = prefs.getString("sgk6", "$16");
				ste.select_string(send);
				playAudioThroughAppFunction("006");
				break;
			}
			break;
		case R.id.buttons7S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk7", "$17");
				ste.select_string(send);
				playAudioThroughAppFunction("007");
				break;
			case 2: // Chat
				send = prefs.getString("sck7", "$27");
				ste.select_string(send);
				playAudioThroughAppFunction("032");
				break;
			case 3: // Happy
				send = prefs.getString("shk7", "$37");
				ste.select_string(send);
				playAudioThroughAppFunction("057");
				break;
			case 4: // Sad
				send = prefs.getString("sdk7", "$47");
				ste.select_string(send);
				playAudioThroughAppFunction("082");
				break;
			case 5: // Whistle
				send = prefs.getString("swk7", "$57");
				ste.select_string(send);
				playAudioThroughAppFunction("107");
				break;
			case 6: // Scream
				send = prefs.getString("ssk7", "$67");
				ste.select_string(send);
				playAudioThroughAppFunction("132");
				break;
			case 7: // Leia
				send = prefs.getString("slk7", "$77");
				ste.select_string(send);
				playAudioThroughAppFunction("157");
				break;
			case 8: // Music
				send = prefs.getString("smk7", "$87");
				ste.select_string(send);
				playAudioThroughAppFunction("182");
				break;
			default:
				send = prefs.getString("sgk7", "$17");
				ste.select_string(send);
				playAudioThroughAppFunction("007");
				break;
			}
			break;
		case R.id.buttons8S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk8", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("008");
				break;
			case 2: // Chat
				send = prefs.getString("sck8", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("033");
				break;
			case 3: // Happy
				send = prefs.getString("shk8", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("058");
				break;
			case 4: // Sad
				send = prefs.getString("sdk8", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("083");
				break;
			case 5: // Whistle
				send = prefs.getString("swk8", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("108");
				break;
			case 6: // Scream
				send = prefs.getString("ssk8", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("133");
				break;
			case 7: // Leia
				send = prefs.getString("slk8", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("158");
				break;
			case 8: // Music
				send = prefs.getString("smk8", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("183");
				break;
			default:
				send = prefs.getString("sgk8", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("008");
				break;
			}
			break;
		case R.id.buttons9S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk9", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("009");
				break;
			case 2: // Chat
				send = prefs.getString("sck9", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("034");
				break;
			case 3: // Happy
				send = prefs.getString("shk9", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("059");
				break;
			case 4: // Sad
				send = prefs.getString("sdk9", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("084");
				break;
			case 5: // Whistle
				send = prefs.getString("swk9", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("109");
				break;
			case 6: // Scream
				send = prefs.getString("ssk9", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("134");
				break;
			case 7: // Leia
				send = prefs.getString("slk9", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("159");
				break;
			case 8: // Music
				send = prefs.getString("smk9", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("184");
				break;
			default:
				send = prefs.getString("sgk9", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("009");
				break;
			}
			break;
		case R.id.buttons10S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk10", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("010");
				break;
			case 2: // Chat
				send = prefs.getString("sck10", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("035");
				break;
			case 3: // Happy
				send = prefs.getString("shk10", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("060");
				break;
			case 4: // Sad
				send = prefs.getString("sdk10", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("085");
				break;
			case 5: // Whistle
				send = prefs.getString("swk10", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("110");
				break;
			case 6: // Scream
				send = prefs.getString("ssk10", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("135");
				break;
			case 7: // Leia
				send = prefs.getString("slk10", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("160");
				break;
			case 8: // Music
				send = prefs.getString("smk10", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("185");
				break;
			default:
				send = prefs.getString("sgk10", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("010");
				break;
			}
			break;
		case R.id.buttons11S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk11", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("011");
				break;
			case 2: // Chat
				send = prefs.getString("sck11", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("036");
				break;
			case 3: // Happy
				send = prefs.getString("shk11", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("061");
				break;
			case 4: // Sad
				send = prefs.getString("sdk11", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("086");
				break;
			case 5: // Whistle
				send = prefs.getString("swk11", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("111");
				break;
			case 6: // Scream
				send = prefs.getString("ssk11", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("136");
				break;
			case 7: // Leia
				send = prefs.getString("slk11", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("161");
				break;
			case 8: // Music
				send = prefs.getString("smk11", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("186");
				break;
			default:
				send = prefs.getString("sgk11", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("011");
				break;
			}
			break;
		case R.id.buttons12S:
			pager2 = (ViewPager) findViewById(R.id.pagerSound);
			pager2.setCurrentItem(1);
			break;
		case R.id.buttons13S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk12", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("012");
				break;
			case 2: // Chat
				send = prefs.getString("sck12", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("037");
				break;
			case 3: // Happy
				send = prefs.getString("shk12", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("062");
				break;
			case 4: // Sad
				send = prefs.getString("sdk12", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("087");
				break;
			case 5: // Whistle
				send = prefs.getString("swk12", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("112");
				break;
			case 6: // Scream
				send = prefs.getString("ssk12", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("137");
				break;
			case 7: // Leia
				send = prefs.getString("slk12", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("162");
				break;
			case 8: // Music
				send = prefs.getString("smk12", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("187");
				break;
			default:
				send = prefs.getString("sgk12", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("012");
				break;
			}
			break;
		case R.id.buttons14S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk13", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("013");
				break;
			case 2: // Chat
				send = prefs.getString("sck13", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("038");
				break;
			case 3: // Happy
				send = prefs.getString("shk13", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("063");
				break;
			case 4: // Sad
				send = prefs.getString("sdk13", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("088");
				break;
			case 5: // Whistle
				send = prefs.getString("swk13", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("113");
				break;
			case 6: // Scream
				send = prefs.getString("ssk13", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("138");
				break;
			case 7: // Leia
				send = prefs.getString("slk13", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("163");
				break;
			case 8: // Music
				send = prefs.getString("smk13", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("188");
				break;
			default:
				send = prefs.getString("sgk13", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("013");
				break;
			}
			break;
		case R.id.buttons15S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk14", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("014");
				break;
			case 2: // Chat
				send = prefs.getString("sck14", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("039");
				break;
			case 3: // Happy
				send = prefs.getString("shk14", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("064");
				break;
			case 4: // Sad
				send = prefs.getString("sdk14", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("089");
				break;
			case 5: // Whistle
				send = prefs.getString("swk14", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("114");
				break;
			case 6: // Scream
				send = prefs.getString("ssk14", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("139");
				break;
			case 7: // Leia
				send = prefs.getString("slk14", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("164");
				break;
			case 8: // Music
				send = prefs.getString("smk14", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("189");
				break;
			default:
				send = prefs.getString("sgk14", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("014");
				break;
			}
			break;
		case R.id.buttons16S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk15", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("015");
				break;
			case 2: // Chat
				send = prefs.getString("sck15", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("040");
				break;
			case 3: // Happy
				send = prefs.getString("shk15", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("065");
				break;
			case 4: // Sad
				send = prefs.getString("sdk15", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("090");
				break;
			case 5: // Whistle
				send = prefs.getString("swk15", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("115");
				break;
			case 6: // Scream
				send = prefs.getString("ssk15", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("140");
				break;
			case 7: // Leia
				send = prefs.getString("slk15", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("165");
				break;
			case 8: // Music
				send = prefs.getString("smk15", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("190");
				break;
			default:
				send = prefs.getString("sgk15", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("015");
				break;
			}
			break;
		case R.id.buttons17S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk16", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("016");
				break;
			case 2: // Chat
				send = prefs.getString("sck16", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("041");
				break;
			case 3: // Happy
				send = prefs.getString("shk16", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("066");
				break;
			case 4: // Sad
				send = prefs.getString("sdk16", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("091");
				break;
			case 5: // Whistle
				send = prefs.getString("swk16", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("116");
				break;
			case 6: // Scream
				send = prefs.getString("ssk16", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("141");
				break;
			case 7: // Leia
				send = prefs.getString("slk16", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("166");
				break;
			case 8: // Music
				send = prefs.getString("smk16", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("191");
				break;
			default:
				send = prefs.getString("sgk16", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("016");
				break;
			}
			break;
		case R.id.buttons18S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk17", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("017");
				break;
			case 2: // Chat
				send = prefs.getString("sck17", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("042");
				break;
			case 3: // Happy
				send = prefs.getString("shk17", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("067");
				break;
			case 4: // Sad
				send = prefs.getString("sdk17", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("092");
				break;
			case 5: // Whistle
				send = prefs.getString("swk17", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("117");
				break;
			case 6: // Scream
				send = prefs.getString("ssk17", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("142");
				break;
			case 7: // Leia
				send = prefs.getString("slk17", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("167");
				break;
			case 8: // Music
				send = prefs.getString("smk17", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("192");
				break;
			default:
				send = prefs.getString("sgk17", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("017");
				break;
			}
			break;
		case R.id.buttons19S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk18", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("018");
				break;
			case 2: // Chat
				send = prefs.getString("sck18", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("043");
				break;
			case 3: // Happy
				send = prefs.getString("shk18", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("068");
				break;
			case 4: // Sad
				send = prefs.getString("sdk18", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("093");
				break;
			case 5: // Whistle
				send = prefs.getString("swk18", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("118");
				break;
			case 6: // Scream
				send = prefs.getString("ssk18", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("143");
				break;
			case 7: // Leia
				send = prefs.getString("slk18", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("168");
				break;
			case 8: // Music
				send = prefs.getString("smk18", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("193");
				break;
			default:
				send = prefs.getString("sgk18", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("018");
				break;
			}
			break;
		case R.id.buttons20S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk19", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("019");
				break;
			case 2: // Chat
				send = prefs.getString("sck19", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("044");
				break;
			case 3: // Happy
				send = prefs.getString("shk19", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("069");
				break;
			case 4: // Sad
				send = prefs.getString("sdk19", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("094");
				break;
			case 5: // Whistle
				send = prefs.getString("swk19", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("119");
				break;
			case 6: // Scream
				send = prefs.getString("ssk19", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("144");
				break;
			case 7: // Leia
				send = prefs.getString("slk19", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("169");
				break;
			case 8: // Music
				send = prefs.getString("smk19", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("194");
				break;
			default:
				send = prefs.getString("sgk19", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("019");
				break;
			}
			break;
		case R.id.buttons21S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk20", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("020");
				break;
			case 2: // Chat
				send = prefs.getString("sck20", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("045");
				break;
			case 3: // Happy
				send = prefs.getString("shk20", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("070");
				break;
			case 4: // Sad
				send = prefs.getString("sdk20", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("095");
				break;
			case 5: // Whistle
				send = prefs.getString("swk20", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("120");
				break;
			case 6: // Scream
				send = prefs.getString("ssk20", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("145");
				break;
			case 7: // Leia
				send = prefs.getString("slk20", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("170");
				break;
			case 8: // Music
				send = prefs.getString("smk20", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("195");
				break;
			default:
				send = prefs.getString("sgk20", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("020");
				break;
			}
			break;
		case R.id.buttons22S:
			pager2 = (ViewPager) findViewById(R.id.pagerSound);
			pager2.setCurrentItem(0);
			break;
		case R.id.buttons23S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk21", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("021");
				break;
			case 2: // Chat
				send = prefs.getString("sck21", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("046");
				break;
			case 3: // Happy
				send = prefs.getString("shk21", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("071");
				break;
			case 4: // Sad
				send = prefs.getString("sdk21", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("096");
				break;
			case 5: // Whistle
				send = prefs.getString("swk21", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("121");
				break;
			case 6: // Scream
				send = prefs.getString("ssk21", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("146");
				break;
			case 7: // Leia
				send = prefs.getString("slk21", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("171");
				break;
			case 8: // Music
				send = prefs.getString("smk21", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("196");
				break;
			default:
				send = prefs.getString("sgk21", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("021");
				break;
			}
			break;
		case R.id.buttons24S:
			switch (Main_activity.sound_screen) {
			case 1: // General
				send = prefs.getString("sgk22", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("022");
				break;
			case 2: // Chat
				send = prefs.getString("sck22", "$21");
				ste.select_string(send);
				playAudioThroughAppFunction("047");
				break;
			case 3: // Happy
				send = prefs.getString("shk22", "$31");
				ste.select_string(send);
				playAudioThroughAppFunction("072");
				break;
			case 4: // Sad
				send = prefs.getString("sdk22", "$41");
				ste.select_string(send);
				playAudioThroughAppFunction("097");
				break;
			case 5: // Whistle
				send = prefs.getString("swk22", "$51");
				ste.select_string(send);
				playAudioThroughAppFunction("122");
				break;
			case 6: // Scream
				send = prefs.getString("ssk22", "$61");
				ste.select_string(send);
				playAudioThroughAppFunction("147");
				break;
			case 7: // Leia
				send = prefs.getString("slk22", "$71");
				ste.select_string(send);
				playAudioThroughAppFunction("172");
				break;
			case 8: // Music
				send = prefs.getString("smk22", "$81");
				ste.select_string(send);
				playAudioThroughAppFunction("197");
				break;
			default:
				send = prefs.getString("sgk22", "$11");
				ste.select_string(send);
				playAudioThroughAppFunction("022");
				break;
			}
			break;

		case R.id.button_sound:
			ImageView selWire = (ImageView) findViewById(R.id.imageViewR2_wire_sel);
			ImageView mWire = (ImageView) findViewById(R.id.imageViewR2_wire);
			ViewPager soundPager = (ViewPager) findViewById(R.id.pagerSoundAct);
			if (Main_activity.mainSound == 0) // If 0, then turn sound activity
												// on
			{
				selWire.setVisibility(View.INVISIBLE);
				mWire.setVisibility(View.INVISIBLE);
				soundPager.setVisibility(View.VISIBLE);
				Main_activity.mainSound = 1;
			} else {
				soundPager.setVisibility(View.INVISIBLE);
				selWire.setVisibility(View.INVISIBLE);
				mWire.setVisibility(View.VISIBLE);
				Main_activity.mainSound = 0;
			}
			break;

		case R.id.button_settings:
			Intent intent = new Intent(Main_activity.this,
					UserSettingActivity.class);
			startActivity(intent);
			prefs = PreferenceManager
					.getDefaultSharedPreferences(Main_activity.this);
			Main_activity.ip_address = prefs.getString("prefUserAddress",
					"169.254.1.1");
			Main_activity.ip_port = Integer.parseInt(prefs.getString(
					"prefUserPort", "2000"));
			break;
		default:
			break;
		}
	}

	public void playAudioThroughAppFunction(final String thisSoundNumber) {
		if (playAudioOnApp == true) {
			//Following code is for playing audio file through app/bluetooth
			String tempstring = "" + Environment.getExternalStorageDirectory().getPath() + "/R2TouchAudio/";
			File dir = new File(tempstring);
			Boolean resulltSound = dir.isDirectory();
			// list the files using a anonymous FileFilter
			File[] audioFiles = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.getName().startsWith(thisSoundNumber);
				}
			});
			if (audioFiles != null) {
				if (audioFiles.length == 0) return;
				mediaPlayer.reset();
				String audiostring = audioFiles[0].getName();
				//MediaPlayer mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/R2TouchAudio/" + audiostring));
					mediaPlayer.prepareAsync();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer player) {
						player.start();
					}
				});
				mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mp.reset();
					}
				});
			}
		}
	}
	
	// This PagerAdapter Handles the Buttons in the Main screen
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

	// This handles the pageradapter when switching between circle and buttons
	public class WizardPagerAdapter2 extends PagerAdapter {
		public Object instantiateItem(View collection, int position) {
			int resId = 0;
			switch (position) {
			case 0:
				resId = R.id.page_oneS;
				break;
			case 1:
				resId = R.id.page_twoS;
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

	// This PagerAdapter handles the sound buttons on the main screen
	public class WizardPagerAdapter3 extends PagerAdapter {
		public Object instantiateItem(View collection, int position) {
			int resId = 0;
			switch (position) {
			case 0:
				resId = R.id.page_oneS1;
				break;
			case 1:
				resId = R.id.page_twoS1;
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

	class ClientThread implements Runnable {
		@Override
		public void run() {
			try {
				/*
				if (connection == 1) {
					manager = (UsbManager) getSystemService(Context.USB_SERVICE);
					driver = UsbSerialProber.findFirstDevice(manager);
				}
				*/
				if (connection == 3) {
					InetAddress serverAddr = InetAddress.getByName(ip_address);
					socket = new Socket(serverAddr, ip_port);
					if (socket.isConnected() == true) {
						wifiSymbol.scheduleAtFixedRate(wifiTask, 0, 1000);
						connTest.scheduleAtFixedRate(connTestTask, 0, 500);
					}
				} else if (connection == 4) {
					client_socket = new DatagramSocket(ip_port);
					connTest.scheduleAtFixedRate(connTestTask, 0, 500);
					//new Thread(new ClientUDP()).start();
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void sendstring(String send_to_ard) {
		String message = send_to_ard;
		Main_activity.test = message;
		// Converts to bytes and sends the text command to the Marcduino
		final byte[] msgBuffer = message.getBytes();
		if (connection == 4) {
			udpMessage = message;
			new Thread(new ClientUDP()).start();
			//try {
				//InetAddress IPAddress = InetAddress.getByName(ip_address);
				//DatagramPacket send_packet = new DatagramPacket(msgBuffer,
						//send_to_ard.length(), IPAddress, ip_port);
				//client_socket.
				//client_socket.send(send_packet);
				//client_socket.close();
				
			//} catch (IOException e) {

			//} finally {
			//.close();
			//}
		}
		if (connection == 3) {
			Thread send_command = new Thread() {
				public void run() {
					OutputStream outStream = null;
					try {
						outStream = socket.getOutputStream();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						// Todo
					}

					try {
						outStream.write(msgBuffer);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						// Todo
					}
				}
			};
			send_command.start();
		}
		/*
		if (connection == 1) {
			if (driver != null) {
				try {
					driver.open();
					driver.setParameters(9600, UsbSerialDriver.DATABITS_8, UsbSerialDriver.STOPBITS_1, UsbSerialDriver.PARITY_NONE);
					driver.write(msgBuffer, 100);
				} catch (IOException e) {
					// Deal with error.
				} finally {
					try {
						driver.close();
					} catch (IOException e) {
					}
				}
			}
		}
		*/
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	@SuppressWarnings("deprecation")
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inInputShareable = true;
		options.inPurgeable = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private int data = 0;

		public BitmapWorkerTask(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			data = params[0];
			return (decodeSampledBitmapFromResource(getResources(), data,
					imageWidth, imageHeight));
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}
	
	public void setTextSize(int screenSize) {
		
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean dontuseCustom = prefs.getBoolean("useCustomSize", true);
		int buttonTSize = 12;
		int labelTSize = 12;
		try {
		buttonTSize = Integer.parseInt(prefs.getString("buttonTSize", "12"));
		labelTSize = Integer.parseInt(prefs.getString("labelTSize", "12"));
		} catch (NumberFormatException e) {
			
		}
		
		TextView line1 = (TextView) findViewById(R.id.textView5);
		// TextView line2 = (TextView) findViewById (R.id.textViewSound_title);
		TextView line3 = (TextView) findViewById(R.id.textView_soundbank);
		TextView mainViewPager1st = (TextView) findViewById(R.id.textViewViewPager1stPage);
		TextView mainViewPager2nd = (TextView) findViewById(R.id.textViewViewPager2ndPage);
		TextView mainSoundPager1st = (TextView) findViewById(R.id.textView_soundbank);
		TextView mainSoundPager2nd = (TextView) findViewById(R.id.textViewViewPager2ndPageS);
		//ImageButton butmainoff = (ImageButton) findViewById(R.id.button_off);
		Button butquiet = (Button) findViewById(R.id.button_quiet);
		Button butsndon = (Button) findViewById(R.id.button_snd_on);
		Button butholoon = (Button) findViewById(R.id.button_holo_on);
		//ImageButton butsoundoff = (ImageButton) findViewById(R.id.butoff);
		Button butless = (Button) findViewById(R.id.butless);
		Button butmid = (Button) findViewById(R.id.butmid);
		Button butmore = (Button) findViewById(R.id.butmore);
		Button but1 = (Button) findViewById(R.id.buttons1);
		Button but2 = (Button) findViewById(R.id.buttons2);
		Button but3 = (Button) findViewById(R.id.buttons3);
		Button but4 = (Button) findViewById(R.id.buttons4);
		Button but5 = (Button) findViewById(R.id.buttons5);
		Button but6 = (Button) findViewById(R.id.buttons6);
		Button but7 = (Button) findViewById(R.id.buttons7);
		Button but8 = (Button) findViewById(R.id.buttons8);
		Button but9 = (Button) findViewById(R.id.buttons9);
		Button but10 = (Button) findViewById(R.id.buttons10);
		Button but11 = (Button) findViewById(R.id.buttons11);
		Button but12 = (Button) findViewById(R.id.buttons12);
		Button but13 = (Button) findViewById(R.id.buttons13);
		Button but14 = (Button) findViewById(R.id.buttons14);
		Button but15 = (Button) findViewById(R.id.buttons15);
		Button but16 = (Button) findViewById(R.id.buttons16);
		Button but17 = (Button) findViewById(R.id.buttons17);
		Button but18 = (Button) findViewById(R.id.buttons18);
		Button but19 = (Button) findViewById(R.id.buttons19);
		Button but20 = (Button) findViewById(R.id.buttons20);
		Button but21 = (Button) findViewById(R.id.buttons21);
		Button but22 = (Button) findViewById(R.id.buttons22);
		Button but23 = (Button) findViewById(R.id.buttons23);
		Button but24 = (Button) findViewById(R.id.buttons24);
		Button btn1S = (Button) findViewById(R.id.buttons1S);
		Button btn2S = (Button) findViewById(R.id.buttons2S);
		Button btn3S = (Button) findViewById(R.id.buttons3S);
		Button btn4S = (Button) findViewById(R.id.buttons4S);
		Button btn5S = (Button) findViewById(R.id.buttons5S);
		Button btn6S = (Button) findViewById(R.id.buttons6S);
		Button btn7S = (Button) findViewById(R.id.buttons7S);
		Button btn8S = (Button) findViewById(R.id.buttons8S);
		Button btn9S = (Button) findViewById(R.id.buttons9S);
		Button btn10S = (Button) findViewById(R.id.buttons10S);
		Button btn11S = (Button) findViewById(R.id.buttons11S);
		Button btn12S = (Button) findViewById(R.id.buttons12S);
		Button btn13S = (Button) findViewById(R.id.buttons13S);
		Button btn14S = (Button) findViewById(R.id.buttons14S);
		Button btn15S = (Button) findViewById(R.id.buttons15S);
		Button btn16S = (Button) findViewById(R.id.buttons16S);
		Button btn17S = (Button) findViewById(R.id.buttons17S);
		Button btn18S = (Button) findViewById(R.id.buttons18S);
		Button btn19S = (Button) findViewById(R.id.buttons19S);
		Button btn20S = (Button) findViewById(R.id.buttons20S);
		Button btn21S = (Button) findViewById(R.id.buttons21S);
		Button btn22S = (Button) findViewById(R.id.buttons22S);
		Button btn23S = (Button) findViewById(R.id.buttons23S);
		Button btn24S = (Button) findViewById(R.id.buttons24S);
		
		if (dontuseCustom == true) {
			if (screenSize <= 480) {
				line1.setTextSize(12);
				// line2.setTextSize(12);
				line3.setTextSize(12);
				mainViewPager1st.setTextSize(12);
				mainViewPager2nd.setTextSize(12);
				mainSoundPager1st.setTextSize(12);
				mainSoundPager2nd.setTextSize(12);
				butquiet.setTextSize(10);
				butsndon.setTextSize(10);
				butholoon.setTextSize(10);
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
				btn1S.setTextSize(10);
				btn2S.setTextSize(10);
				btn3S.setTextSize(10);
				btn4S.setTextSize(10);
				btn5S.setTextSize(10);
				btn6S.setTextSize(10);
				btn7S.setTextSize(10);
				btn8S.setTextSize(10);
				btn9S.setTextSize(10);
				btn10S.setTextSize(10);
				btn11S.setTextSize(10);
				btn12S.setTextSize(10);
				btn13S.setTextSize(10);
				btn14S.setTextSize(10);
				btn15S.setTextSize(10);
				btn16S.setTextSize(10);
				btn17S.setTextSize(10);
				btn18S.setTextSize(10);
				btn19S.setTextSize(10);
				btn20S.setTextSize(10);
				btn21S.setTextSize(10);
				btn22S.setTextSize(10);
				btn23S.setTextSize(10);
				btn24S.setTextSize(10);
			} else if (screenSize <= 720) {
				line1.setTextSize(16);
				// line2.setTextSize(18);
				line3.setTextSize(18);
				mainViewPager1st.setTextSize(18);
				mainViewPager2nd.setTextSize(18);
				mainSoundPager1st.setTextSize(18);
				mainSoundPager2nd.setTextSize(18);
				butquiet.setTextSize(18);
				butsndon.setTextSize(18);
				butholoon.setTextSize(18);
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
				btn1S.setTextSize(18);
				btn2S.setTextSize(18);
				btn3S.setTextSize(18);
				btn4S.setTextSize(18);
				btn5S.setTextSize(18);
				btn6S.setTextSize(18);
				btn7S.setTextSize(18);
				btn8S.setTextSize(18);
				btn9S.setTextSize(18);
				btn10S.setTextSize(18);
				btn11S.setTextSize(18);
				btn12S.setTextSize(18);
				btn13S.setTextSize(18);
				btn14S.setTextSize(18);
				btn15S.setTextSize(18);
				btn16S.setTextSize(18);
				btn17S.setTextSize(18);
				btn18S.setTextSize(18);
				btn19S.setTextSize(18);
				btn20S.setTextSize(18);
				btn21S.setTextSize(18);
				btn22S.setTextSize(18);
				btn23S.setTextSize(18);
				btn24S.setTextSize(18);
			} else if (screenSize <= 1080) {
				line1.setTextSize(32);
				// line2.setTextSize(36);
				line3.setTextSize(36);
				mainViewPager1st.setTextSize(36);
				mainViewPager2nd.setTextSize(36);
				mainSoundPager1st.setTextSize(36);
				mainSoundPager2nd.setTextSize(36);
				butquiet.setTextSize(18);
				butsndon.setTextSize(18);
				butholoon.setTextSize(18);
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
				btn1S.setTextSize(18);
				btn2S.setTextSize(18);
				btn3S.setTextSize(18);
				btn4S.setTextSize(18);
				btn5S.setTextSize(18);
				btn6S.setTextSize(18);
				btn7S.setTextSize(18);
				btn8S.setTextSize(18);
				btn9S.setTextSize(18);
				btn10S.setTextSize(18);
				btn11S.setTextSize(18);
				btn12S.setTextSize(18);
				btn13S.setTextSize(18);
				btn14S.setTextSize(18);
				btn15S.setTextSize(18);
				btn16S.setTextSize(18);
				btn17S.setTextSize(18);
				btn18S.setTextSize(18);
				btn19S.setTextSize(18);
				btn20S.setTextSize(18);
				btn21S.setTextSize(18);
				btn22S.setTextSize(18);
				btn23S.setTextSize(18);
				btn24S.setTextSize(18);
			} else {
			}
		}
		else { //Custom text size
			line1.setTextSize(labelTSize);
			// line2.setTextSize(36);
			line3.setTextSize(labelTSize);
			mainViewPager1st.setTextSize(labelTSize);
			mainViewPager2nd.setTextSize(labelTSize);
			mainSoundPager1st.setTextSize(labelTSize);
			mainSoundPager2nd.setTextSize(labelTSize);
			butquiet.setTextSize(buttonTSize);
			butsndon.setTextSize(buttonTSize);
			butholoon.setTextSize(buttonTSize);
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
			btn1S.setTextSize(buttonTSize);
			btn2S.setTextSize(buttonTSize);
			btn3S.setTextSize(buttonTSize);
			btn4S.setTextSize(buttonTSize);
			btn5S.setTextSize(buttonTSize);
			btn6S.setTextSize(buttonTSize);
			btn7S.setTextSize(buttonTSize);
			btn8S.setTextSize(buttonTSize);
			btn9S.setTextSize(buttonTSize);
			btn10S.setTextSize(buttonTSize);
			btn11S.setTextSize(buttonTSize);
			btn12S.setTextSize(buttonTSize);
			btn13S.setTextSize(buttonTSize);
			btn14S.setTextSize(buttonTSize);
			btn15S.setTextSize(buttonTSize);
			btn16S.setTextSize(buttonTSize);
			btn17S.setTextSize(buttonTSize);
			btn18S.setTextSize(buttonTSize);
			btn19S.setTextSize(buttonTSize);
			btn20S.setTextSize(buttonTSize);
			btn21S.setTextSize(buttonTSize);
			btn22S.setTextSize(buttonTSize);
			btn23S.setTextSize(buttonTSize);
			btn24S.setTextSize(buttonTSize);
		}
	}
	
	public static class ClientUDP implements Runnable { 
		@Override 
		public void run() { 
			try {
				InetAddress serverAddr = InetAddress.getByName(ip_address); 
				//client_socket = new DatagramSocket(ip_port); 
				
				byte[] msgBuffer = udpMessage.getBytes();
				
				DatagramPacket packet = new DatagramPacket(msgBuffer, udpMessage.length(), serverAddr, ip_port); 
				client_socket.send(packet); 
			} catch (Exception e) { 
			} finally {
				//client_socket.close();
			}
		}
	}
}