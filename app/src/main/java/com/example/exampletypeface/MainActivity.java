package com.example.exampletypeface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView messageView;
	AppCompatDelegate mDelegate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT < 11) {
			getLayoutInflater().setFactory(this);
		} else {
			getLayoutInflater().setFactory2(this);
		}
		mDelegate = getDelegate();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		messageView = (TextView) findViewById(R.id.text_message);
//		AssetManager am = getResources().getAssets();
//		Typeface nanum = Typeface.createFromAsset(am, "nanumgothic.ttf");
//		messageView.setTypeface(nanum);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {
		View view = super.onCreateView(name, context, attrs);
		if (Build.VERSION.SDK_INT < 11) {
			if (view == null) {
				view = mDelegate.createView(null, name, context, attrs);
				view = setCustomFont(view, name, context, attrs);
			}
		}
    	return view;
	}

	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
		View view = super.onCreateView(parent, name, context, attrs);
		if (view == null) {
			view = mDelegate.createView(parent, name, context, attrs);
		}
		view = setCustomFont(view, name, context, attrs);
		return view;
	}

	private View setCustomFont(View view, String name, Context context, AttributeSet attrs) {
		if (view == null) {
			if (name.equals("TextView")) {
				view = new TextView(context, attrs);
			}
		}
		if (view != null && view instanceof TextView) {
//    		TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.fontFamily});
//    		String fontname = ta.getString(0);
//    		ta.recycle();
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCustomFont);
			String fontname = ta.getString(R.styleable.MyCustomFont_customFont);
			int style = ta.getInt(R.styleable.MyCustomFont_android_textStyle, Typeface.NORMAL);
			ta.recycle();
			Typeface font = TypefaceManager.getInstance().getTypeface(this, fontname);
			TextView tv = (TextView)view;
			if (tv == null) {
				tv = new TextView(context, attrs);
			}
			if (font != null) {
				tv.setTypeface(font,style);
			}
		}
		return view;
	}
}
