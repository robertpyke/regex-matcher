package com.robertpyke.regexmatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.robertpyke.regexmatcher.R.id;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class RegexMatcher extends TabActivity {
    /** Called when the activity is first created. */
	EditText inputRegex;
	EditText inputCheckValue;
    TextView output;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.help, tabHost.getTabContentView(), true);
        LayoutInflater.from(this).inflate(R.layout.info, tabHost.getTabContentView(), true);
        
        
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.main_label))
        		.setIndicator(getString(R.string.main_label))
        		.setContent(R.id.mainScrollView)
        );

        
        
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.help_label))
        		.setIndicator(getString(R.string.help_label))
        		.setContent(R.id.helpScrollView)
        );
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.info_label))
        		.setIndicator(getString(R.string.info_label))
        		.setContent(R.id.infoScrollView)
        );
        TextView infoText = (TextView) findViewById(R.id.infoTextView);
		StringBuilder info = new StringBuilder();
		info.append("App: " + getString(R.string.app_name) + "\n\n");
		info.append("Version: " + getString(R.string.version) + "\n\n");
		info.append("Description: " + getString(R.string.app_description) + "\n\n");
		info.append("Home Page: " + getString(R.string.home_page) + "\n\n");
		info.append("Support: " + getString(R.string.support_message) + "\n\n");
		info.append("Email: " + getString(R.string.email) + "\n\n");
		infoText.setText(info.toString());
        
        inputRegex = (EditText) findViewById(R.id.InputRegex);
        inputCheckValue = (EditText) findViewById(R.id.InputCheck);
        output = (TextView) findViewById(R.id.Output);
        Button checkButton = (Button) findViewById(R.id.Button01);
            
        checkButton.setOnClickListener(new RegexUpdateListener(this));        
        
    }
    
    private class RegexUpdateListener implements android.view.View.OnClickListener{
		RegexMatcher regexChecker;
    	public RegexUpdateListener(RegexMatcher regexChecker) {
			this.regexChecker = regexChecker;
		}
		@Override
		public void onClick(View arg0) {
			regexChecker.performRegexCheck();
		}
	}
    
    private void performRegexCheck() {
    	if (inputRegex.getText().toString().equals("")) {
    		output.setText("NO REGEX PATERN PROVIDED");
    		return;
    	}
    	try {
	    	Pattern pattern = Pattern.compile(inputRegex.getText().toString());
	
	        Matcher matcher = pattern.matcher(inputCheckValue.getText().toString());
	
	        output.setText("");
	        output.setBackgroundColor(Color.TRANSPARENT);
	        
	        boolean found = false;
	        while (matcher.find()) {
	            found = true;
	            int start = matcher.start() + 1;
	            int end = matcher.end();
	            if ( start == end) {
	            	output.setText(output.getText().toString()  + "\"" + matcher.group() + "\" : pos(" + start +  ")\n");
	            } else {
	            	output.setText(output.getText().toString()  + "\"" + matcher.group() + "\" : pos(" + start + "-" + end +  ")\n");
	            }
	        }
	        
	        if (!found) {
	        	output.setText("NO MATCHES FOUND");        	
	        }
    	} catch (PatternSyntaxException e) {
    		output.setText("BAD REGEX PATTERN PROVIDED:\n " + e.getMessage());
    	}
    }
}