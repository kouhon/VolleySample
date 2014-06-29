/**
 * 
 */
package com.example.volleysample;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author kouichi
 *
 */
public class ImageLoaderSampleActivity extends Activity
{
	private String mSampleImageUrl1 = "http://113.32.172.51/wp/wp-content/uploads/2012/04/thumb_hanahusa.jpg";
	private String mSampleImageUrl2 = "http://113.32.172.51/wp/wp-content/uploads/2012/04/thumb_yatu.jpg";
	private String mSampleImageUrl3 = "http://113.32.172.51/wp/wp-content/uploads/2012/04/c.jpg";
	private String mSampleImageUrl4 = "http://113.32.172.51/wp/wp-content/uploads/2012/04/thumb_110.jpg";
	private String mSampleImageUrl5 = "http://113.32.172.51/wp/wp-content/uploads/2012/03/thm_akatsu_s.jpg";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);
		ListView listView = (ListView)findViewById(R.id.volleyListView);
		
		ArrayList<String> urlList = new ArrayList<String>();
		urlList.add(mSampleImageUrl1);
		urlList.add(mSampleImageUrl2);
		urlList.add(mSampleImageUrl3);
		urlList.add(mSampleImageUrl4);
		urlList.add(mSampleImageUrl5);
		
		RequestQueue queue = MainActivity.makeVolleyQueue(getApplicationContext());
		ImageLoaderSampleAdapter adapter = new ImageLoaderSampleAdapter(
				this.getApplicationContext(), 
				urlList, 
				queue);
		
		listView.setAdapter(adapter);
	}
}
