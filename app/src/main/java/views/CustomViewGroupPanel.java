package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroupPanel extends ViewGroup {
	
	private static final String TAG = "CustomLayout";
	
	public CustomViewGroupPanel(Context context){
		super(context);
		init();
	}
	
	public CustomViewGroupPanel(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public CustomViewGroupPanel(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int wspec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.8), MeasureSpec.EXACTLY);
		int hspec = MeasureSpec.makeMeasureSpec((int) (getMeasuredHeight() * 0.23), MeasureSpec.EXACTLY);

		int wspecPager = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.8), MeasureSpec.EXACTLY);
		int hspecPager = MeasureSpec.makeMeasureSpec((int) (getMeasuredHeight() * 0.68), MeasureSpec.EXACTLY);
		
		View v = getChildAt(2);
		View pager = getChildAt(6);
		Log.d(TAG, "Measured Width / Height: "+getMeasuredWidth()+", "+getMeasuredHeight());
		v.measure(wspec, hspec);
		pager.measure(wspecPager, hspecPager);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutWidth = r - l;
		int layoutHeight = b - t;
		
		View topText = getChildAt(0);
		View off = getChildAt(1);
		View topTable = getChildAt(2);
		View wifi = getChildAt(3);
		View wrench = getChildAt(4);
		View sound = getChildAt(5);
		View pager = getChildAt(6);
		//View topText = findViewById(R.id.textView5);
		//View off = findViewById(R.id.button_off);
		//View topTable = findViewById(R.id.topTableMain);
		topText.layout((int) (layoutWidth * 0.2), 0, layoutWidth, (int) (layoutHeight * 0.07));
		off.layout(0, (int) (layoutHeight * 0.07), (int) (layoutWidth * 0.18), (int) (layoutHeight * 0.3));
		topTable.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.07), layoutWidth, (int) (layoutHeight * 0.3));
		wifi.layout(0, (int) (layoutHeight * 0.35), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.48));
		wrench.layout(0, (int) (layoutHeight * 0.48), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.68));
		sound.layout(0, (int) (layoutHeight * 0.68), (int) (layoutWidth * 0.2), (int) (layoutHeight * 0.83));
		pager.layout((int) (layoutWidth * 0.2), (int) (layoutHeight * 0.32), layoutWidth, layoutHeight);
	}
}