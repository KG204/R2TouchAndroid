package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomLEDViewGroup extends ViewGroup {
	
	public CustomLEDViewGroup(Context context){
		super(context);
		init();
	}
	
	public CustomLEDViewGroup(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public CustomLEDViewGroup(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		
	}
	/*
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int wspec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.8), MeasureSpec.EXACTLY);
		int hspec = MeasureSpec.makeMeasureSpec((int) (getMeasuredHeight() * 0.25), MeasureSpec.EXACTLY);

		int wspecPager = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.8), MeasureSpec.EXACTLY);
		int hspecPager = MeasureSpec.makeMeasureSpec((int) (getMeasuredHeight() * 0.68), MeasureSpec.EXACTLY);
		
		View v = getChildAt(1);
		View pager = getChildAt(2);
		Log.d(TAG, "Measured Width / Height: "+getMeasuredWidth()+", "+getMeasuredHeight());
		v.measure(wspec, hspec);
		pager.measure(wspecPager, hspecPager);
	}
	*/
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutWidth = r - l;
		int layoutHeight = b - t;
		
		View topLED = getChildAt(0);
		View topText = getChildAt(1);
		View bottomLED = getChildAt(2);
		View bottomText = getChildAt(3);
		View largeLED = getChildAt(4);
		View largeText = getChildAt(5);
		
		topLED.layout((int) (layoutWidth * 0.325), (int) (layoutHeight * 0.05), (int) (layoutWidth * 0.675), (int) (layoutHeight * 0.20));
		topText.layout((int) (layoutWidth * 0.05), (int) (layoutHeight * 0.20), (int) (layoutWidth * 0.95), (int) (layoutHeight * 0.35));
		bottomLED.layout((int) (layoutWidth * 0.325), (int) (layoutHeight * 0.352), (int) (layoutWidth * 0.675), (int) (layoutHeight * 0.502));
		bottomText.layout((int) (layoutWidth * 0.05), (int) (layoutHeight * 0.502), (int) (layoutWidth * 0.95), (int) (layoutHeight * 0.652));
		largeLED.layout((int) (layoutWidth * 0.17), (int) (layoutHeight * 0.654), (int) (layoutWidth * 0.83), (int) (layoutHeight * 0.804));
		largeText.layout((int) (layoutWidth * 0.05), (int) (layoutHeight * 0.804), (int) (layoutWidth * 0.95), (int) (layoutHeight * 0.954));
		//button.layout(0, (int) (layoutHeight * 0.05), (int) (layoutWidth * 0.18), (int) (layoutHeight * 0.3));
		//topTable.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.05), (int) (layoutWidth), (int) (layoutHeight * 0.3));
		//pager.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.32), layoutWidth, layoutHeight);
		/*topText.layout((int) (layoutWidth * 0.2), 0, (int) (layoutWidth), (int) (layoutHeight * 0.05));
		off.layout(0, (int) (layoutHeight * 0.05), (int) (layoutWidth * 0.18), (int) (layoutHeight * 0.3));
		topTable.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.05), (int) (layoutWidth), (int) (layoutHeight * 0.3));
		wifi.layout(0, (int) (layoutHeight * 0.35), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.48));
		wrench.layout(0, (int) (layoutHeight * 0.48), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.68));
		sound.layout(0, (int) (layoutHeight * 0.68), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.83));
		pager.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.32), (int) (layoutWidth), (int) (layoutHeight));*/
	}
}