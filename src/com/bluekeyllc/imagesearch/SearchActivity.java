package com.bluekeyllc.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	AsyncHttpClient client;
	public static final int FILTER_OPTIONS = 123;
	public static final String FILTER_STRING = "filter string";
	Filters filters;
	String size_filter;
	String color_filter;
	String image_filter;
	String site_filter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		client = new AsyncHttpClient();
		
		size_filter = "";
		color_filter = "";
		image_filter = "";
		site_filter = "";
		
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onClickSettings(MenuItem menuItem) {
		
		Intent i = new Intent(this, FilterActivity.class);
		startActivityForResult(i, FILTER_OPTIONS);
		
	}
	
	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadMoreData(page);
				
			}
		});
	}
	
	public void loadMoreData(int offset) {
		
		if (offset <=8) {
			String query = etQuery.getText().toString();
			//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Android
			client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
						+ "start=" + offset +
						"&imgsz=" + size_filter +
						"&imgtype=" + image_filter +
						"&as_sitesearch=" + site_filter +
						"&imgcolor=" + color_filter + "&v=1.0&q=" + Uri.encode(query), 
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject response) {
								JSONArray imageJsonResults = null;
								try {
									imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
									imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
									
									Log.d("DEBUG", imageResults.toString());
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
						});
		}
			
	}
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Android
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
					+ "start=" + 0 +"&imgsz=" + size_filter + 
					"&imgcolor=" + color_filter +
					"&imgtype=" + image_filter +
					"&as_sitesearch=" + site_filter +
					"&v=1.0&q=" + Uri.encode(query), 
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject response) {
							JSONArray imageJsonResults = null;
							try {
								imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
								imageResults.clear();
								imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
								
								Log.d("DEBUG", imageResults.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						
					});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FILTER_OPTIONS) {
			filters = (Filters) data.getSerializableExtra(FILTER_STRING);
			String size = filters.size;
			size_filter = size;
			color_filter = filters.color;
			image_filter = filters.image;
			site_filter = filters.site;
		}
		
	}
	

}
