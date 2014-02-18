package com.appable.widget.iOs7;

import com.appable.widget.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class iSearchBar_iOs7 extends RelativeLayout {

	private static final int LAYOUT_ID = R.layout.searchbar;
	
	private Context mContext;
	private EditText edit;
	private Button btn_clear;
	private Button btn_cancel;
	private View layout;
	private onSearchBarAction mOnSearchBarListener;
	
	private boolean isNeedShowCancel = false;
	private boolean isSearchInChange = false;
	public void setSearchInChange(boolean mIsSearchInChange){
		isSearchInChange = mIsSearchInChange;
	}
	public void forceResetSearchBar(){
		edit.setText("");
		btn_cancel.setVisibility(View.GONE);
	}
	
	public void outFocusSearchBar(){
		if(edit.getText().toString().trim().length() == 0)
			btn_cancel.setVisibility(View.GONE);
	}
	
	public void setOnSearchBarAction(onSearchBarAction ltn){
		mOnSearchBarListener = ltn;
	}
	
	public interface onSearchBarAction{
		void onSearch(String searchStr);
		void onTouch();
	}
	
	public iSearchBar_iOs7(Context context) {
		super(context);
		mContext = context;
		layout = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public iSearchBar_iOs7(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		layout = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public iSearchBar_iOs7(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		layout = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){
		edit = (EditText)layout.findViewById(R.id.edit);
		btn_clear = (Button)layout.findViewById(R.id.btn_clear);
		btn_cancel = (Button)layout.findViewById(R.id.btn_cancel);

		if(!isInEditMode()){
			btn_cancel.setEnabled(false);
			
			btn_clear.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edit.setText("");
				}
			});
			
			edit.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(mOnSearchBarListener!=null){
						mOnSearchBarListener.onTouch();
					}
					int eventAction = event.getAction();
					switch (eventAction) {
					case MotionEvent.ACTION_DOWN:
						isNeedShowCancel = true;
						break;
					case MotionEvent.ACTION_UP:
						btn_cancel.setVisibility(View.VISIBLE);
						btn_cancel.setEnabled(true);
						isNeedShowCancel = false;
						break;
					}
					return false;
				}
			});
			
			edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if(hasFocus){
						if(edit.getText().length() == 0){
							btn_clear.setVisibility(View.GONE);
						}else{
							btn_clear.setVisibility(View.VISIBLE);
						}
					}else{
						btn_clear.setVisibility(View.GONE);
					}
				}
			});
			
			edit.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					if(s.length() == 0){
						btn_clear.setVisibility(View.GONE);
					}else{
						if(edit.isFocused()){
							btn_clear.setVisibility(View.VISIBLE);
						}else{
							btn_clear.setVisibility(View.GONE);
						}
						animationShow();
					}
					
					if(isSearchInChange){
						if(mOnSearchBarListener!=null){
							mOnSearchBarListener.onSearch(edit.getText().toString().trim());
						}
					}
				}
			});
			
			edit.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if(!isSearchInChange){
						if(mOnSearchBarListener!=null){
							mOnSearchBarListener.onSearch(edit.getText().toString().trim());
						}
					}
					return true;
				}
			});
			
			
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edit.setText("");
					animationHide();
					if(mOnSearchBarListener!=null){
						mOnSearchBarListener.onSearch("");
					}
				}
			});
		}
	}
	
	private void animationHide(){
		btn_cancel.setVisibility(View.GONE);
		btn_cancel.setEnabled(false);
	}
	
	private void animationShow(){
		btn_cancel.setVisibility(View.VISIBLE);
		btn_cancel.setEnabled(true);
	}
	
}
