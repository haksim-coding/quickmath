package com.example.quickmath;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

public class NumPadView extends FrameLayout implements View.OnClickListener {

    private EditText input;

    public NumPadView(Context context) {
        super(context);
        init();
    }

    public NumPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.numpad_layout, this);
        initViews();
    }

    public interface CityButtonClickListener {
        void onCityButtonClick(int input);
    }

    private CityButtonClickListener buttonClickListener;

    public void setCityButtonClickListener(CityButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    private void initViews() {
        input = $(R.id.input);
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_submit).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            input.append(((TextView) v).getText());
            return;
        }
        switch (v.getId()) {
            case R.id.t9_key_submit: { // handle clear button
                if (buttonClickListener != null) {
                    if(input.getText().length()>0){
                        buttonClickListener.onCityButtonClick(Integer.parseInt(String.valueOf(input.getText())));
                        input.setText("");
                    }

                }
            }
            break;
            case R.id.t9_key_backspace: { // handle backspace button
                // delete one character
                Editable editable = input.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }
            break;
        }
    }

    public String getInputText() {
        return input.getText().toString();
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
}