package com.program;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import com.program.r2_touch_android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Sound_activity3 extends Activity implements View.OnTouchListener {
	/** Called when the activity is first created. */
	int imageHeight = 0;
	int imageWidth = 0;
	public static int SSsetValue = 0;
	public static int SScurrentValue = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sound_screen2);
		WizardPagerAdapter adapter = new WizardPagerAdapter();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		SSsetValue = Main_activity.SsetValue;
		SScurrentValue = Main_activity.ScurrentValue;

		PreferenceManager.setDefaultValues(this, R.xml.settings, true);
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		final string_parser ste = new string_parser();

		final Timer soundTimer = new Timer();

		final TimerTask task = new TimerTask() {

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

						int valtoPrint = (w * SSsetValue) / 100;
						if (SSsetValue == 0) {
							if (SScurrentValue >= 0)
								SScurrentValue = SScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, SScurrentValue, 0,
									paint);
						} else if ((SScurrentValue <= valtoPrint + 3)
								&& (SScurrentValue >= valtoPrint - 3))
							canvas.drawBitmap(maskBitmap, SScurrentValue, 0,
									paint);
						else if (SScurrentValue > valtoPrint) {
							SScurrentValue = SScurrentValue - 4;
							canvas.drawBitmap(maskBitmap, SScurrentValue, 0,
									paint);
						} else {
							SScurrentValue = SScurrentValue + 4;
							canvas.drawBitmap(maskBitmap, SScurrentValue, 0,
									paint);
						}

						paint.setXfermode(null);
						canvas.drawBitmap(outlinetestBitmap, 0, 0, paint);
						outlinetest.recycle();
						// We do not need the mask bitmap anymore.
						mask.recycle();
						ImageView timetoTEST = (ImageView) findViewById(R.id.test);
						timetoTEST.setImageBitmap(bitmap);

					}
				});
				// this.cancel();
			}
		};

		soundTimer.scheduleAtFixedRate(task, 0, 50);

		/*
		 * BitmapFactory.Options options = new BitmapFactory.Options();
		 * options.inJustDecodeBounds = true;
		 * BitmapFactory.decodeResource(getResources(), R.id.imageView2,
		 * options);
		 * 
		 * BitmapFactory.Options options2 = new BitmapFactory.Options();
		 * options2.inJustDecodeBounds = true;
		 * BitmapFactory.decodeResource(getResources(), R.id.imageView1,
		 * options2);
		 */
		change_to_gen();

		// ImageView iv = (ImageView) findViewById (R.id.imageView2);
		// imageWidth = iv.getLayoutParams().width;
		// imageHeight = iv.getLayoutParams().height;
		/*
		 * imageWidth = iv.getWidth(); imageHeight = iv.getHeight();
		 */
		// iv.setImageBitmap(
		// decodeSampledBitmapFromResource(getResources(),
		// R.drawable.sound_wheel, imageWidth, imageHeight));

		final ImageView iv = (ImageView) findViewById(R.id.imageView2);

		final ViewTreeObserver observer = iv.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Log.d("Log", "Height: " + iv.getHeight());
				Log.d("Log", "Width: " + iv.getWidth());
				imageWidth = iv.getWidth();
				imageHeight = iv.getHeight();
				iv.setImageBitmap(decodeSampledBitmapFromResource(
						getResources(), R.drawable.sound_wheel, imageWidth,
						imageHeight));
				// BitmapWorkerTask taskcircle = new BitmapWorkerTask(iv);
				// taskcircle.execute(R.drawable.sound_wheel);
				iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		final ImageView iv2 = (ImageView) findViewById(R.id.imageView1);

		// BitmapWorkerTask taskcircle = new BitmapWorkerTask(iv);
		// taskcircle.execute(R.drawable.sound_wheel);
		/*
		 * final ViewTreeObserver observer2 = iv2.getViewTreeObserver();
		 * observer2.addOnGlobalLayoutListener( new
		 * ViewTreeObserver.OnGlobalLayoutListener() {
		 * 
		 * @Override public void onGlobalLayout() { Log.d("Log", "Height: " +
		 * iv2.getHeight()); Log.d("Log", "Width: " + iv2.getWidth());
		 * //imageWidth = iv2.getWidth(); //imageHeight = iv2.getHeight();
		 * iv2.setImageBitmap( decodeSampledBitmapFromResource(getResources(),
		 * R.drawable.sound_wheel_selection_wheel, imageWidth, imageHeight));
		 * iv2.setVisibility(View.VISIBLE);
		 * iv2.getViewTreeObserver().removeGlobalOnLayoutListener(this); } });
		 */

		Display display = getWindowManager().getDefaultDisplay();
		int screenSizeX = display.getWidth();
		int screenSizeY = display.getHeight();

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
			btn1.setText(prefs.getString("sgk1_name", "Error"));
			btn2.setText(prefs.getString("sgk2_name", "Error"));
			btn3.setText(prefs.getString("sgk3_name", "Error"));
			btn4.setText(prefs.getString("sgk4_name", "Error"));
			btn5.setText(prefs.getString("sgk5_name", "Error"));
			btn6.setText(prefs.getString("sgk6_name", "Error"));
			btn7.setText(prefs.getString("sgk7_name", "Error"));
			btn8.setText(prefs.getString("sgk8_name", "Error"));
			btn9.setText(prefs.getString("sgk9_name", "Error"));
			btn10.setText(prefs.getString("sgk10_name", "Error"));
			btn11.setText(prefs.getString("sgk11_name", "Error"));
			btn13.setText(prefs.getString("sgk12_name", "Error"));
			btn14.setText(prefs.getString("sgk13_name", "Error"));
			btn15.setText(prefs.getString("sgk14_name", "Error"));
			btn16.setText(prefs.getString("sgk15_name", "Error"));
			btn17.setText(prefs.getString("sgk16_name", "Error"));
			btn18.setText(prefs.getString("sgk17_name", "Error"));
			btn19.setText(prefs.getString("sgk18_name", "Error"));
			btn20.setText(prefs.getString("sgk19_name", "Error"));
			btn21.setText(prefs.getString("sgk20_name", "Error"));
			btn23.setText(prefs.getString("sgk21_name", "Error"));
			btn24.setText(prefs.getString("sgk22_name", "Error"));
			break;
		case 2: // Chat
			btn1.setText(prefs.getString("sck1_name", "Error"));
			btn2.setText(prefs.getString("sck2_name", "Error"));
			btn3.setText(prefs.getString("sck3_name", "Error"));
			btn4.setText(prefs.getString("sck4_name", "Error"));
			btn5.setText(prefs.getString("sck5_name", "Error"));
			btn6.setText(prefs.getString("sck6_name", "Error"));
			btn7.setText(prefs.getString("sck7_name", "Error"));
			btn8.setText(prefs.getString("sck8_name", "Error"));
			btn9.setText(prefs.getString("sck9_name", "Error"));
			btn10.setText(prefs.getString("sck10_name", "Error"));
			btn11.setText(prefs.getString("sck11_name", "Error"));
			btn13.setText(prefs.getString("sck12_name", "Error"));
			btn14.setText(prefs.getString("sck13_name", "Error"));
			btn15.setText(prefs.getString("sck14_name", "Error"));
			btn16.setText(prefs.getString("sck15_name", "Error"));
			btn17.setText(prefs.getString("sck16_name", "Error"));
			btn18.setText(prefs.getString("sck17_name", "Error"));
			btn19.setText(prefs.getString("sck18_name", "Error"));
			btn20.setText(prefs.getString("sck19_name", "Error"));
			btn21.setText(prefs.getString("sck20_name", "Error"));
			btn23.setText(prefs.getString("sck21_name", "Error"));
			btn24.setText(prefs.getString("sck22_name", "Error"));
			break;
		case 3: // Happy
			btn1.setText(prefs.getString("shk1_name", "Error"));
			btn2.setText(prefs.getString("shk2_name", "Error"));
			btn3.setText(prefs.getString("shk3_name", "Error"));
			btn4.setText(prefs.getString("shk4_name", "Error"));
			btn5.setText(prefs.getString("shk5_name", "Error"));
			btn6.setText(prefs.getString("shk6_name", "Error"));
			btn7.setText(prefs.getString("shk7_name", "Error"));
			btn8.setText(prefs.getString("shk8_name", "Error"));
			btn9.setText(prefs.getString("shk9_name", "Error"));
			btn10.setText(prefs.getString("shk10_name", "Error"));
			btn11.setText(prefs.getString("shk11_name", "Error"));
			btn13.setText(prefs.getString("shk12_name", "Error"));
			btn14.setText(prefs.getString("shk13_name", "Error"));
			btn15.setText(prefs.getString("shk14_name", "Error"));
			btn16.setText(prefs.getString("shk15_name", "Error"));
			btn17.setText(prefs.getString("shk16_name", "Error"));
			btn18.setText(prefs.getString("shk17_name", "Error"));
			btn19.setText(prefs.getString("shk18_name", "Error"));
			btn20.setText(prefs.getString("shk19_name", "Error"));
			btn21.setText(prefs.getString("shk20_name", "Error"));
			btn23.setText(prefs.getString("shk21_name", "Error"));
			btn24.setText(prefs.getString("shk22_name", "Error"));
			break;
		case 4: // Sad
			btn1.setText(prefs.getString("sdk1_name", "Error"));
			btn2.setText(prefs.getString("sdk2_name", "Error"));
			btn3.setText(prefs.getString("sdk3_name", "Error"));
			btn4.setText(prefs.getString("sdk4_name", "Error"));
			btn5.setText(prefs.getString("sdk5_name", "Error"));
			btn6.setText(prefs.getString("sdk6_name", "Error"));
			btn7.setText(prefs.getString("sdk7_name", "Error"));
			btn8.setText(prefs.getString("sdk8_name", "Error"));
			btn9.setText(prefs.getString("sdk9_name", "Error"));
			btn10.setText(prefs.getString("sdk10_name", "Error"));
			btn11.setText(prefs.getString("sdk11_name", "Error"));
			btn13.setText(prefs.getString("sdk12_name", "Error"));
			btn14.setText(prefs.getString("sdk13_name", "Error"));
			btn15.setText(prefs.getString("sdk14_name", "Error"));
			btn16.setText(prefs.getString("sdk15_name", "Error"));
			btn17.setText(prefs.getString("sdk16_name", "Error"));
			btn18.setText(prefs.getString("sdk17_name", "Error"));
			btn19.setText(prefs.getString("sdk18_name", "Error"));
			btn20.setText(prefs.getString("sdk19_name", "Error"));
			btn21.setText(prefs.getString("sdk20_name", "Error"));
			btn23.setText(prefs.getString("sdk21_name", "Error"));
			btn24.setText(prefs.getString("sdk22_name", "Error"));
			break;
		case 5: // Whistle
			btn1.setText(prefs.getString("swk1_name", "Error"));
			btn2.setText(prefs.getString("swk2_name", "Error"));
			btn3.setText(prefs.getString("swk3_name", "Error"));
			btn4.setText(prefs.getString("swk4_name", "Error"));
			btn5.setText(prefs.getString("swk5_name", "Error"));
			btn6.setText(prefs.getString("swk6_name", "Error"));
			btn7.setText(prefs.getString("swk7_name", "Error"));
			btn8.setText(prefs.getString("swk8_name", "Error"));
			btn9.setText(prefs.getString("swk9_name", "Error"));
			btn10.setText(prefs.getString("swk10_name", "Error"));
			btn11.setText(prefs.getString("swk11_name", "Error"));
			btn13.setText(prefs.getString("swk12_name", "Error"));
			btn14.setText(prefs.getString("swk13_name", "Error"));
			btn15.setText(prefs.getString("swk14_name", "Error"));
			btn16.setText(prefs.getString("swk15_name", "Error"));
			btn17.setText(prefs.getString("swk16_name", "Error"));
			btn18.setText(prefs.getString("swk17_name", "Error"));
			btn19.setText(prefs.getString("swk18_name", "Error"));
			btn20.setText(prefs.getString("swk19_name", "Error"));
			btn21.setText(prefs.getString("swk20_name", "Error"));
			btn23.setText(prefs.getString("swk21_name", "Error"));
			btn24.setText(prefs.getString("swk22_name", "Error"));
			break;
		case 6: // Scream
			btn1.setText(prefs.getString("ssk1_name", "Error"));
			btn2.setText(prefs.getString("ssk2_name", "Error"));
			btn3.setText(prefs.getString("ssk3_name", "Error"));
			btn4.setText(prefs.getString("ssk4_name", "Error"));
			btn5.setText(prefs.getString("ssk5_name", "Error"));
			btn6.setText(prefs.getString("ssk6_name", "Error"));
			btn7.setText(prefs.getString("ssk7_name", "Error"));
			btn8.setText(prefs.getString("ssk8_name", "Error"));
			btn9.setText(prefs.getString("ssk9_name", "Error"));
			btn10.setText(prefs.getString("ssk10_name", "Error"));
			btn11.setText(prefs.getString("ssk11_name", "Error"));
			btn13.setText(prefs.getString("ssk12_name", "Error"));
			btn14.setText(prefs.getString("ssk13_name", "Error"));
			btn15.setText(prefs.getString("ssk14_name", "Error"));
			btn16.setText(prefs.getString("ssk15_name", "Error"));
			btn17.setText(prefs.getString("ssk16_name", "Error"));
			btn18.setText(prefs.getString("ssk17_name", "Error"));
			btn19.setText(prefs.getString("ssk18_name", "Error"));
			btn20.setText(prefs.getString("ssk19_name", "Error"));
			btn21.setText(prefs.getString("ssk20_name", "Error"));
			btn23.setText(prefs.getString("ssk21_name", "Error"));
			btn24.setText(prefs.getString("ssk22_name", "Error"));
			break;
		case 7: // Leia
			btn1.setText(prefs.getString("slk1_name", "Error"));
			btn2.setText(prefs.getString("slk2_name", "Error"));
			btn3.setText(prefs.getString("slk3_name", "Error"));
			btn4.setText(prefs.getString("slk4_name", "Error"));
			btn5.setText(prefs.getString("slk5_name", "Error"));
			btn6.setText(prefs.getString("slk6_name", "Error"));
			btn7.setText(prefs.getString("slk7_name", "Error"));
			btn8.setText(prefs.getString("slk8_name", "Error"));
			btn9.setText(prefs.getString("slk9_name", "Error"));
			btn10.setText(prefs.getString("slk10_name", "Error"));
			btn11.setText(prefs.getString("slk11_name", "Error"));
			btn13.setText(prefs.getString("slk12_name", "Error"));
			btn14.setText(prefs.getString("slk13_name", "Error"));
			btn15.setText(prefs.getString("slk14_name", "Error"));
			btn16.setText(prefs.getString("slk15_name", "Error"));
			btn17.setText(prefs.getString("slk16_name", "Error"));
			btn18.setText(prefs.getString("slk17_name", "Error"));
			btn19.setText(prefs.getString("slk18_name", "Error"));
			btn20.setText(prefs.getString("slk19_name", "Error"));
			btn21.setText(prefs.getString("slk20_name", "Error"));
			btn23.setText(prefs.getString("slk21_name", "Error"));
			btn24.setText(prefs.getString("slk22_name", "Error"));
			break;
		case 8: // Music
			btn1.setText(prefs.getString("smk1_name", "Error"));
			btn2.setText(prefs.getString("smk2_name", "Error"));
			btn3.setText(prefs.getString("smk3_name", "Error"));
			btn4.setText(prefs.getString("smk4_name", "Error"));
			btn5.setText(prefs.getString("smk5_name", "Error"));
			btn6.setText(prefs.getString("smk6_name", "Error"));
			btn7.setText(prefs.getString("smk7_name", "Error"));
			btn8.setText(prefs.getString("smk8_name", "Error"));
			btn9.setText(prefs.getString("smk9_name", "Error"));
			btn10.setText(prefs.getString("smk10_name", "Error"));
			btn11.setText(prefs.getString("smk11_name", "Error"));
			btn13.setText(prefs.getString("smk12_name", "Error"));
			btn14.setText(prefs.getString("smk13_name", "Error"));
			btn15.setText(prefs.getString("smk14_name", "Error"));
			btn16.setText(prefs.getString("smk15_name", "Error"));
			btn17.setText(prefs.getString("smk16_name", "Error"));
			btn18.setText(prefs.getString("smk17_name", "Error"));
			btn19.setText(prefs.getString("smk18_name", "Error"));
			btn20.setText(prefs.getString("smk19_name", "Error"));
			btn21.setText(prefs.getString("smk20_name", "Error"));
			btn23.setText(prefs.getString("smk21_name", "Error"));
			btn24.setText(prefs.getString("smk22_name", "Error"));
			break;
		default:
			btn1.setText(prefs.getString("sgk1_name", "Error"));
			btn2.setText(prefs.getString("sgk2_name", "Error"));
			btn3.setText(prefs.getString("sgk3_name", "Error"));
			btn4.setText(prefs.getString("sgk4_name", "Error"));
			btn5.setText(prefs.getString("sgk5_name", "Error"));
			btn6.setText(prefs.getString("sgk6_name", "Error"));
			btn7.setText(prefs.getString("sgk7_name", "Error"));
			btn8.setText(prefs.getString("sgk8_name", "Error"));
			btn9.setText(prefs.getString("sgk9_name", "Error"));
			btn10.setText(prefs.getString("sgk10_name", "Error"));
			btn11.setText(prefs.getString("sgk11_name", "Error"));
			btn13.setText(prefs.getString("sgk12_name", "Error"));
			btn14.setText(prefs.getString("sgk13_name", "Error"));
			btn15.setText(prefs.getString("sgk14_name", "Error"));
			btn16.setText(prefs.getString("sgk15_name", "Error"));
			btn17.setText(prefs.getString("sgk16_name", "Error"));
			btn18.setText(prefs.getString("sgk17_name", "Error"));
			btn19.setText(prefs.getString("sgk18_name", "Error"));
			btn20.setText(prefs.getString("sgk19_name", "Error"));
			btn21.setText(prefs.getString("sgk20_name", "Error"));
			btn23.setText(prefs.getString("sgk21_name", "Error"));
			btn24.setText(prefs.getString("sgk22_name", "Error"));
		}

		ImageButton btnHome = (ImageButton) findViewById(R.id.home_Button);
		btnHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Goes back to the main screen
				Main_activity.SsetValue = SSsetValue;
				Main_activity.ScurrentValue = SScurrentValue;
				task.cancel();
				soundTimer.cancel();
				finish();
			}
		});

		/*
		 * Button btn1 = (Button) findViewById(R.id.buttons1); Button btn2 =
		 * (Button) findViewById(R.id.buttons2); Button btn3 = (Button)
		 * findViewById(R.id.buttons3); Button btn4 = (Button)
		 * findViewById(R.id.buttons4); Button btn5 = (Button)
		 * findViewById(R.id.buttons5); Button btn6 = (Button)
		 * findViewById(R.id.buttons6); Button btn7 = (Button)
		 * findViewById(R.id.buttons7); Button btn8 = (Button)
		 * findViewById(R.id.buttons8); Button btn9 = (Button)
		 * findViewById(R.id.buttons9); Button btn10 = (Button)
		 * findViewById(R.id.buttons10); Button btn11 = (Button)
		 * findViewById(R.id.buttons11); Button btn12 = (Button)
		 * findViewById(R.id.buttons12); Button btn13 = (Button)
		 * findViewById(R.id.buttons13); Button btn14 = (Button)
		 * findViewById(R.id.buttons14); Button btn15 = (Button)
		 * findViewById(R.id.buttons15); Button btn16 = (Button)
		 * findViewById(R.id.buttons16); Button btn17 = (Button)
		 * findViewById(R.id.buttons17); Button btn18 = (Button)
		 * findViewById(R.id.buttons18); Button btn19 = (Button)
		 * findViewById(R.id.buttons19); Button btn20 = (Button)
		 * findViewById(R.id.buttons20); Button btn21 = (Button)
		 * findViewById(R.id.buttons21); Button btn22 = (Button)
		 * findViewById(R.id.buttons22); Button btn23 = (Button)
		 * findViewById(R.id.buttons23); Button btn24 = (Button)
		 * findViewById(R.id.buttons24);
		 */

		Button btn12 = (Button) findViewById(R.id.buttons12);
		Button btn22 = (Button) findViewById(R.id.buttons22);

		TextView line2 = (TextView) findViewById(R.id.textView_soundbank);
		TextView pager2text = (TextView) findViewById(R.id.textViewViewPager2ndPage);

		ImageButton butsoundoff = (ImageButton) findViewById(R.id.butoff);
		Button butless = (Button) findViewById(R.id.button_less);
		Button butmid = (Button) findViewById(R.id.button_mid);
		Button butmore = (Button) findViewById(R.id.button_more);
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

		if (screenSizeX <= 480) {
			line2.setTextSize(12);
			pager2text.setTextSize(12);
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
		} else if (screenSizeX <= 720) {
			line2.setTextSize(18);
			pager2text.setTextSize(18);
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
		} else if (screenSizeX <= 1080) {
			line2.setTextSize(36);
			pager2text.setTextSize(36);
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
		} else {

		}

		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// PreferenceManager.setDefaultValues(this, R.xml.settings,
				// true);
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(this);

				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk1", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck1", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk1", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk1", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk1", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk1", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk1", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk1", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk1", "$11");
					ste.select_string(send);
				}
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk2", "$12");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck2", "$22");
					ste.select_string(send);
					break;
				case 3: // Happy
					send = prefs.getString("shk2", "$32");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk2", "$42");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk2", "$52");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk2", "$62");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk2", "$72");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk2", "$82");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk2", "$12");
					ste.select_string(send);
				}
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk3", "$13");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck3", "$23");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk3", "$33");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk3", "$43");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk3", "$53");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk3", "$63");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk3", "$73");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk3", "$83");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk3", "$13");
					ste.select_string(send);
				}
			}
		});

		btn4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk4", "$14");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck4", "$24");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk4", "$34");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk4", "$44");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk4", "$54");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk4", "$64");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk4", "$74");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk4", "$84");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk4", "$14");
					ste.select_string(send);
				}
			}
		});

		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk5", "$15");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck5", "$25");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk5", "$35");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk5", "$45");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk5", "$55");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk5", "$65");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk5", "$75");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk5", "$85");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk5", "$15");
					ste.select_string(send);
				}
			}
		});

		btn6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk6", "$16");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck6", "$26");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk6", "$36");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk6", "$46");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk6", "$56");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk6", "$66");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk6", "$76");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk6", "$86");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk6", "$16");
					ste.select_string(send);
				}
			}
		});

		btn7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk7", "$17");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck7", "$27");
					ste.select_string(send);
					break;
				case 3: // Happy
					send = prefs.getString("shk7", "$37");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk7", "$47");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk7", "$57");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk7", "$67");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk7", "$77");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk7", "$87");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk7", "$17");
					ste.select_string(send);
				}
			}
		});

		btn8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk8", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck8", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk8", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk8", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk8", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk8", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk8", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk8", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk8", "$11");
					ste.select_string(send);
				}
			}
		});
		btn9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk9", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck9", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk9", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk9", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk9", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk9", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk9", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk9", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk9", "$11");
					ste.select_string(send);
				}
			}
		});

		btn10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk10", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck10", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk10", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk10", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk10", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk10", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk10", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk10", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk10", "$11");
					ste.select_string(send);
				}
			}
		});

		btn11.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk11", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck11", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk11", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk11", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk11", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk11", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk11", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk11", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk11", "$11");
					ste.select_string(send);
				}
			}
		});

		btn13.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk12", "$11");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck12", "$21");
					ste.select_string(send);
					break;
				case 3: // Happy
					send = prefs.getString("shk12", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk12", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk12", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk12", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk12", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk12", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk12", "$11");
					ste.select_string(send);
				}
			}
		});

		btn14.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk13", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck13", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk13", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk13", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk13", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk13", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk13", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk13", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk13", "$11");
					ste.select_string(send);
				}
			}
		});

		btn15.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk14", "$11");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck14", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk14", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk14", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk14", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk14", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk14", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk14", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk14", "$11");
					ste.select_string(send);
				}
			}
		});

		btn16.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk15", "$11");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck15", "$21");
					ste.select_string(send);
					break;
				case 3: // Happy
					send = prefs.getString("shk15", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk15", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk15", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk15", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk15", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk15", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk15", "$11");
					ste.select_string(send);
				}
			}
		});

		btn17.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk16", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck16", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk16", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk16", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk16", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk16", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk16", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk16", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk16", "$11");
					ste.select_string(send);
				}
			}
		});

		btn18.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk17", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck17", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk17", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk17", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk17", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk17", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk17", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk17", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk17", "$11");
					ste.select_string(send);
				}
			}
		});

		btn19.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk18", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck18", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk18", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk18", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk18", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk18", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk18", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk18", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk18", "$11");
					ste.select_string(send);
				}
			}
		});

		btn20.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk19", "$11");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck19", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk19", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk19", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk19", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk19", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk19", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk19", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk19", "$11");
					ste.select_string(send);
				}
			}
		});

		btn21.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk20", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck20", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk20", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk20", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk20", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk20", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk20", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk20", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk20", "$11");
					ste.select_string(send);
				}
			}
		});

		btn23.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk21", "$11");
					ste.select_string(send);
					break;
				case 2: // Chat
					send = prefs.getString("sck21", "$21");
					ste.select_string(send);
					break;
				case 3: // Happy
					send = prefs.getString("shk21", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk21", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk21", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk21", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk21", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk21", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk21", "$11");
					ste.select_string(send);
				}
			}
		});

		btn24.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// SharedPreferences prefs =
				// PreferenceManager.getDefaultSharedPreferences(Main_activity.this);
				String send = "";
				switch (Main_activity.sound_screen) {
				case 1: // General
					send = prefs.getString("sgk22", "$11");
					ste.select_string(send);
					break;

				case 2: // Chat
					send = prefs.getString("sck22", "$21");
					ste.select_string(send);
					break;

				case 3: // Happy
					send = prefs.getString("shk22", "$31");
					ste.select_string(send);
					break;
				case 4: // Sad
					send = prefs.getString("sdk22", "$41");
					ste.select_string(send);
					break;
				case 5: // Whistle
					send = prefs.getString("swk22", "$51");
					ste.select_string(send);
					break;
				case 6: // Scream
					send = prefs.getString("ssk22", "$61");
					ste.select_string(send);
					break;
				case 7: // Leia
					send = prefs.getString("slk22", "$71");
					ste.select_string(send);
					break;
				case 8: // Music
					send = prefs.getString("smk22", "$81");
					ste.select_string(send);
					break;
				default:
					send = prefs.getString("sgk22", "$11");
					ste.select_string(send);
				}
			}
		});

		ImageButton btnoffsound = (ImageButton) findViewById(R.id.butoff);
		btnoffsound.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ste.select_string("$s");
			}
		});
		Button btnless = (Button) findViewById(R.id.button_less);
		btnless.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (SSsetValue > 0)
					SSsetValue = SSsetValue - 5;
				if (SSsetValue < 0)
					SSsetValue = 0;
				ste.select_string("$-");
			}
		});
		Button btnmid = (Button) findViewById(R.id.button_mid);
		btnmid.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SSsetValue = 50;
				ste.select_string("$m");
			}
		});
		Button btnmore = (Button) findViewById(R.id.button_more);
		btnmore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (SSsetValue < 100)
					SSsetValue = SSsetValue + 5;
				if (SSsetValue > 100)
					SSsetValue = 100;
				ste.select_string("$+");
			}
		});
		/*
		 * btn1.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "1", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "1\r"); }
		 * }); btn2.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "2", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "2\r"); }
		 * }); btn3.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "3", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "3\r"); }
		 * }); btn4.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "4", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "4\r"); }
		 * }); btn5.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "5", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "5\r"); }
		 * }); btn6.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "6", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "6\r"); }
		 * }); btn7.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "7", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "7\r"); }
		 * }); btn8.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "8", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "8\r"); }
		 * }); btn9.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "9", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "9\r"); }
		 * }); btn10.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "10", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "10\r");
		 * } }); btn11.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "11", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "11\r");
		 * } });
		 */
		btn12.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(1);
			}
		});
		/*
		 * btn13.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "12", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "12\r");
		 * } }); btn14.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "13", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "13\r");
		 * } }); btn15.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "14", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "14\r");
		 * } }); btn16.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "15", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "15\r");
		 * } }); btn17.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "16", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "16\r");
		 * } }); btn18.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "17", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "17\r");
		 * } }); btn19.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "18", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "18\r");
		 * } }); btn20.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "19", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "19\r");
		 * } }); btn21.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "20", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "20\r");
		 * } });
		 */
		btn22.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ViewPager pager = (ViewPager) findViewById(R.id.pager);
				pager.setCurrentItem(0);
			}
		});
		/*
		 * btn23.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "21", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "21\r");
		 * } }); btn24.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { //Check to see what the current selected sound
		 * resource is. ex if its whis, $xyy x is 5 //Context context1 =
		 * getApplicationContext(); //Toast.makeText(context1, "$" +
		 * Main_activity.sound_screen + "22", Toast.LENGTH_LONG).show();
		 * Main_activity.sendstring("$" + Main_activity.sound_screen + "22\r");
		 * } });
		 */

		if (iv != null) {
			iv.setOnTouchListener(this);
		}

	}

	public boolean onTouch(View v, MotionEvent ev) {
		PreferenceManager.setDefaultValues(this, R.xml.settings, true);
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		final string_parser ste = new string_parser();

		boolean handledHere = false;
		Context context2 = getApplicationContext();
		final int action = ev.getAction();
		int tolerance = 25;
		final int evX = (int) ev.getX();
		final int evY = (int) ev.getY();
		int nextImage = -1; // resource id of the next image to display

		// If we cannot find the imageView, return.
		/*
		 * ImageView gen = (ImageView) v.findViewById(R.id.imageViewGEN);
		 * ImageView chat = (ImageView) v.findViewById(R.id.imageViewCHAT);
		 * ImageView happy = (ImageView) v.findViewById(R.id.imageViewHAPPY);
		 * ImageView sad = (ImageView) v.findViewById(R.id.imageViewSAD);
		 * ImageView whis = (ImageView) v.findViewById(R.id.imageViewWHIS);
		 * ImageView scream = (ImageView) v.findViewById(R.id.imageViewSCREAM);
		 * ImageView leia = (ImageView) v.findViewById(R.id.imageViewLEIA);
		 * ImageView mus = (ImageView) v.findViewById(R.id.imageViewMUS);
		 * ImageView auto = (ImageView) v.findViewById(R.id.imageViewAUTO);
		 */
		ImageView imageView = (ImageView) v.findViewById(R.id.imageView2);
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		if (imageView == null)
			return false;

		// When the action is Down, see if we should show the "pressed" image
		// for the default image.
		// We do this when the default image is showing. That condition is
		// detectable by looking at the
		// tag of the view. If it is null or contains the resource number of the
		// default image, display the pressed image.
		Integer tagNum = (Integer) imageView.getTag();
		int currentResource = (tagNum == null) ? R.drawable.sound_wheel
				: tagNum.intValue();

		// Now that we know the current resource being displayed we can handle
		// the DOWN and UP events.

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// if (currentResource == R.drawable.sound_wheel) {
			// nextImage = R.drawable.p2_ship_pressed;
			// handledHere = true;
			/*
			 * } else if (currentResource != R.drawable.p2_ship_default) {
			 * nextImage = R.drawable.p2_ship_default; handledHere = true;
			 */
			// } else handledHere = true;
			int touchColorI = getHotspotColor(R.id.imageView1, evX, evY);

			ColorTool ctI = new ColorTool();

			// nextImage = R.drawable.p2_ship_default;
			if (ctI.closeMatch(Color.BLACK, touchColorI, tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_auto_select);
				task.execute(R.drawable.sound_wheel_auto_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_auto_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.VISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			}// Auto
			else if (ctI.closeMatch(Color.WHITE, touchColorI, tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel);
				task.execute(R.drawable.sound_wheel);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel, imageWidth, imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Default
			else if (ctI.closeMatch(Color.argb(255, 255, 255, 51), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_gen_select);
				task.execute(R.drawable.sound_wheel_gen_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_gen_select, imageWidth, imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.VISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // General
			else if (ctI.closeMatch(Color.argb(255, 255, 153, 0), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_chat_select);
				task.execute(R.drawable.sound_wheel_chat_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_chat_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.VISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Chat
			else if (ctI.closeMatch(Color.argb(255, 255, 51, 51), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_happy_select);
				task.execute(R.drawable.sound_wheel_happy_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_happy_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.VISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Happy
			else if (ctI.closeMatch(Color.argb(255, 255, 51, 255), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_sad_select);
				task.execute(R.drawable.sound_wheel_sad_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_sad_select, imageWidth, imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.VISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Sad
			else if (ctI.closeMatch(Color.argb(255, 51, 255, 255), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_whis_select);
				task.execute(R.drawable.sound_wheel_whis_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_whis_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.VISIBLE);
				 */
			} // Whisper
			else if (ctI.closeMatch(Color.argb(255, 102, 0, 255), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_scream_select);
				task.execute(R.drawable.sound_wheel_scream_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_scream_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.VISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Scream
			else if (ctI.closeMatch(Color.argb(255, 255, 0, 153), touchColorI,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_leia_select);
				task.execute(R.drawable.sound_wheel_leia_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_leia_select, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.VISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Leia
			else if (ctI.closeMatch(Color.argb(255, 153, 255, 102),
					touchColorI, tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_mus_select);
				task.execute(R.drawable.sound_wheel_mus_select);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_mus_select, imageWidth, imageHeight));
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.VISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Music
				// If the next image is the same as the last image, go back to
				// the default.
				// toast ("Current image: " + currentResource + " next: " +
				// nextImage);
				// if (currentResource == nextImage) {
				// nextImage = R.drawable.p2_ship_default;
				// }
			handledHere = true;
			break;

		case MotionEvent.ACTION_UP:
			// On the UP, we do the click action.
			// The hidden image (image_areas) has three different hotspots on
			// it.
			// The colors are red, blue, and yellow.
			// Use image_areas to determine which region the user touched.
			int touchColor = getHotspotColor(R.id.imageView1, evX, evY);

			// Compare the touchColor to the expected values. Switch to a
			// different image, depending on what color was touched.
			// Note that we use a Color Tool object to test whether the observed
			// color is close enough to the real color to
			// count as a match. We do this because colors on the screen do not
			// match the map exactly because of scaling and
			// varying pixel density.
			ColorTool ct = new ColorTool();
			// nextImage = R.drawable.p2_ship_default;
			if (ct.closeMatch(Color.BLACK, touchColor, tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_auto_selected);
				task.execute(R.drawable.sound_wheel_auto_selected);
				ste.select_string("$R");
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_auto_selected, imageWidth,
				// imageHeight));
				/*
				 * auto.setVisibility(View.VISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			}// Auto
			else if (ct.closeMatch(Color.argb(255, 255, 255, 51), touchColor,
					tolerance)) {
				// imageView.setImageResource(R.drawable.sound_wheel_gen_selected);
				task.execute(R.drawable.sound_wheel_gen_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_gen_selected, imageWidth,
				// imageHeight));
				if (Main_activity.sound_screen == 1) {
					ste.select_string("$1");
				} else {
					ste.select_string("O");
					change_to_gen();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.VISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // General
			else if (ct.closeMatch(Color.argb(255, 255, 153, 0), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_chat_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_chat_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_chat_selected);
				if (Main_activity.sound_screen == 2) {
					ste.select_string("$2");
				} else {
					ste.select_string("O");
					change_to_chat();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.VISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Chat
			else if (ct.closeMatch(Color.argb(255, 255, 51, 51), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_happy_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_happy_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_happy_selected);
				if (Main_activity.sound_screen == 3) {
					ste.select_string("$3");
				} else {
					ste.select_string("O");
					change_to_happy();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.VISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Happy
			else if (ct.closeMatch(Color.argb(255, 255, 51, 255), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_sad_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_sad_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_sad_selected);
				if (Main_activity.sound_screen == 4) {
					ste.select_string("$4");
				} else {
					ste.select_string("O");
					change_to_sad();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.VISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Sad
			else if (ct.closeMatch(Color.argb(255, 51, 255, 255), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_whis_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_whis_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_whis_selected);
				if (Main_activity.sound_screen == 5) {
					ste.select_string("$5");
				} else {
					ste.select_string("O");
					change_to_whis();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.VISIBLE);
				 */
			} // Whisper
			else if (ct.closeMatch(Color.argb(255, 102, 0, 255), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_scream_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_scream_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_scream_selected);
				if (Main_activity.sound_screen == 6) {
					ste.select_string("$6");
				} else {
					ste.select_string("O");
					change_to_scream();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.VISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Scream
			else if (ct.closeMatch(Color.argb(255, 255, 0, 153), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_leia_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_leia_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_leia_selected);
				if (Main_activity.sound_screen == 7) {
					ste.select_string("$7");
				} else {
					ste.select_string("O");
					change_to_leia();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.VISIBLE);
				 * mus.setVisibility(View.INVISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Leia
			else if (ct.closeMatch(Color.argb(255, 153, 255, 102), touchColor,
					tolerance)) {
				task.execute(R.drawable.sound_wheel_mus_selected);
				// imageView.setImageBitmap(
				// decodeSampledBitmapFromResource(getResources(),
				// R.drawable.sound_wheel_mus_selected, imageWidth,
				// imageHeight));
				// imageView.setImageResource(R.drawable.sound_wheel_mus_selected);
				if (Main_activity.sound_screen == 8) {
					ste.select_string("$8");
				} else {
					ste.select_string("O");
					change_to_mus();
				}
				/*
				 * auto.setVisibility(View.INVISIBLE);
				 * gen.setVisibility(View.INVISIBLE);
				 * chat.setVisibility(View.INVISIBLE);
				 * happy.setVisibility(View.INVISIBLE);
				 * sad.setVisibility(View.INVISIBLE);
				 * scream.setVisibility(View.INVISIBLE);
				 * leia.setVisibility(View.INVISIBLE);
				 * mus.setVisibility(View.VISIBLE);
				 * whis.setVisibility(View.INVISIBLE);
				 */
			} // Music
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
			Log.d("ImageAreasActivity", "Hot spot image not found");
			return 0;
		} else {
			// img.setImageBitmap(
			// decodeSampledBitmapFromResource(getResources(),
			// R.drawable.sound_wheel_selection_wheel, imageWidth,
			// imageHeight));
			img.setDrawingCacheEnabled(true);
			Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());

			if (hotspots == null) {
				Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
				return 0;
			} else {
				img.setDrawingCacheEnabled(false);
				return hotspots.getPixel(x, y);
			}
		}
	}

	public void change_to_whis() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("Whistle");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("Beep Whistle");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("High Whistle");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("WHISPER SOUNDS:");
		Main_activity.sound_screen = 5; // 5 is for whisper
	}

	public void change_to_gen() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("1");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("2");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("3");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("4");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("5");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("6");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("7");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("8");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("9");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("10");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("11");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("12");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("13");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("14");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("15");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("16");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("17");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("18");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("19");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("GENERAL SOUNDS:");
		Main_activity.sound_screen = 1; // 5 is for whisper
	}

	public void change_to_leia() {
		// float size = new Button(this).getTextSize();
		Button btn1 = (Button) findViewById(R.id.buttons1);
		// btn1.setTextSize(12);
		btn1.setText("Message (clean)");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("Help me!");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("Message");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("LEIA SOUNDS:");
		Main_activity.sound_screen = 7; // 5 is for whisper
	}

	public void change_to_scream() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("SCREAM");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("ERROR");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("SHORT CRCT");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("SCREAM SOUNDS:");
		Main_activity.sound_screen = 6; // 6 is for scream
	}

	public void change_to_sad() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("1");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("2");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("3");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("4");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("SAD SOUNDS:");
		Main_activity.sound_screen = 4; // 4 is for sad
	}

	public void change_to_happy() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("1");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("2");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("3");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("4");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("5");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("6");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("7");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("HAPPY SOUNDS:");
		Main_activity.sound_screen = 3; // 5 is for whisper
	}

	public void change_to_chat() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("1");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("2");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("3");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("4");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("5");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("6");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("7");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("8");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("9");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("10");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("11");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("12");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("13");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("14");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("15");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("16");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("17");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("18");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("CHAT SOUNDS:");
		Main_activity.sound_screen = 2; // 5 is for whisper
	}

	public void change_to_mus() {
		Button btn1 = (Button) findViewById(R.id.buttons1);
		btn1.setText("CANTINA BEEP");
		Button btn2 = (Button) findViewById(R.id.buttons2);
		btn2.setText("STAR WARS");
		Button btn3 = (Button) findViewById(R.id.buttons3);
		btn3.setText("MARCH");
		Button btn4 = (Button) findViewById(R.id.buttons4);
		btn4.setText("MAHNA MAHNA");
		Button btn5 = (Button) findViewById(R.id.buttons5);
		btn5.setText("CANTINA BEEP");
		Button btn6 = (Button) findViewById(R.id.buttons6);
		btn6.setText("STAYIN' ALIVE");
		Button btn7 = (Button) findViewById(R.id.buttons7);
		btn7.setText("");
		Button btn8 = (Button) findViewById(R.id.buttons8);
		btn8.setText("");
		Button btn9 = (Button) findViewById(R.id.buttons9);
		btn9.setText("");
		Button btn10 = (Button) findViewById(R.id.buttons10);
		btn10.setText("");
		Button btn11 = (Button) findViewById(R.id.buttons11);
		btn11.setText("");
		Button btn13 = (Button) findViewById(R.id.buttons13);
		btn13.setText("");
		Button btn14 = (Button) findViewById(R.id.buttons14);
		btn14.setText("");
		Button btn15 = (Button) findViewById(R.id.buttons15);
		btn15.setText("");
		Button btn16 = (Button) findViewById(R.id.buttons16);
		btn16.setText("");
		Button btn17 = (Button) findViewById(R.id.buttons17);
		btn17.setText("");
		Button btn18 = (Button) findViewById(R.id.buttons18);
		btn18.setText("");
		Button btn19 = (Button) findViewById(R.id.buttons19);
		btn19.setText("");
		Button btn20 = (Button) findViewById(R.id.buttons20);
		btn20.setText("");
		Button btn21 = (Button) findViewById(R.id.buttons21);
		btn21.setText("");
		Button btn23 = (Button) findViewById(R.id.buttons23);
		btn23.setText("");
		Button btn24 = (Button) findViewById(R.id.buttons24);
		btn24.setText("");
		TextView imagebank = (TextView) findViewById(R.id.textView_soundbank);
		imagebank.setText("MUSIC SOUNDS:");
		Main_activity.sound_screen = 8; // 5 is for whisper
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

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

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

}