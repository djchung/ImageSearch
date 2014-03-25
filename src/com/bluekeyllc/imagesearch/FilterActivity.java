package com.bluekeyllc.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterActivity extends Activity {
	
	Filters filters; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		filters = new Filters();
		
		
		
		Spinner sp_size = (Spinner) findViewById(R.id.spinner_size);
		ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource
													(this, R.array.size_array, android.R.layout.simple_spinner_dropdown_item);
		size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_size.setAdapter(size_adapter);
		
		sp_size.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> paretn, View view,
					int pos, long id) {
				if (pos == 0) {
					filters.size = "small";
				} else if (pos == 1) {
					filters.size = "medium";
					Log.d("hello", "selected medium");
				} else if (pos == 2) {
					filters.size = "large";
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Spinner sp_color = (Spinner) findViewById(R.id.spinner_color);
		ArrayAdapter<CharSequence> color_adapter = ArrayAdapter.createFromResource
				(this, R.array.color_array, android.R.layout.simple_spinner_dropdown_item);
		color_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_color.setAdapter(color_adapter);
		sp_color.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if (pos == 0) {
					filters.color = "blue";
				} else if (pos == 1) {
					filters.color = "red";
					Log.d("hello", "selected medium");
				} else if (pos == 2) {
					filters.color = "yellow";
				} else if (pos == 3) {
					filters.color = "green";
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Spinner sp_image = (Spinner) findViewById(R.id.spinner_image);
		ArrayAdapter<CharSequence> image_adapter = ArrayAdapter.createFromResource
				(this, R.array.image_array, android.R.layout.simple_spinner_dropdown_item);
		image_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_image.setAdapter(image_adapter);
		sp_image.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if (pos == 0) {
					filters.image = "face";
				} else if (pos == 1) {
					filters.image = "photo";
				} else if (pos == 2) {
					filters.image = "clipart";
				} else if (pos == 3) {
					filters.image = "lineart";
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}
	
	public void onSave(View v) {
		
		EditText etSite = (EditText) findViewById(R.id.et_site);
		String site = etSite.getText().toString();
		filters.site = site;
		
		Intent data = new Intent();
		data.putExtra(SearchActivity.FILTER_STRING, filters);
		setResult(RESULT_OK, data);
		finish();
		
	}
}
