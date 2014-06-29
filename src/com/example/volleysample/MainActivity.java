/**
 * 
 */
package com.example.volleysample;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author kouichi
 *
 */
public class MainActivity extends Activity implements ImageCache
{
	private enum VOLLEY_REQUEST_TYPE
	{
		CLEAR_CACHE_REQUEST,
		IMAGE_REQUEST,
		JSON_REQUEST,
		JSON_ARRAY_REQUEST,
		JSON_OBJECT_REQUEST,
		STRING_REQUEST,
	}
	
	private AlertDialog.Builder mDialog;
	private String mSampleImageUrl1 = "http://www.interlink.ne.jp/common/images/lgo_interlink.png";
	private String mSampleImageUrl2 = "http://www.interlink.ne.jp/english/img/interlink_logo1.gif";
	private ImageView mSampleVolleyImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.mDialog = new AlertDialog.Builder(this);
		this.mSampleVolleyImageView = (ImageView)findViewById(R.id.sampleVolleyImageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void clickLoadImageBtn(View view)
	{
		this.vollyRequest(VOLLEY_REQUEST_TYPE.IMAGE_REQUEST, this.mSampleImageUrl1);
		NetworkImageView sampleNetworkImageView = (NetworkImageView)findViewById(R.id.sampleNetworkImageView);
		this.imageLoadByNetworkImageView(
				mSampleImageUrl2, 
				sampleNetworkImageView, 
				android.R.drawable.spinner_background, 
				android.R.drawable.ic_dialog_alert);		
	}
	
	public void clickVolleyListBtn(View view)
	{
		Intent intent = new Intent(this, ImageLoaderSampleActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Volleyリクエストキューを作成
	 * @return
	 */
	public static RequestQueue makeVolleyQueue(Context context)
	{
		// cookie有効化
//		DefaultHttpClient client = new DefaultHttpClient();
//		BasicClientCookie cookie = new BasicClientCookie("key", "value");
//		cookie.setDomain("domain");
//		cookie.setPath("/");
//		client.getCookieStore().addCookie(cookie);
////		CookieStore cookieStore = new BasicCookieStore();
////		cookieStore.addCookie(cookie);
////		client.setCookieStore(cookieStore);
//		HttpStack httpStack = new HttpClientStack(client);
	
//		RequestQueue queue = Volley.newRequestQueue(context, httpStack);
		RequestQueue queue = Volley.newRequestQueue(context);
		
		return queue;
	}
	
	/**
	 * GETリクエスト
	 * @param url　リクエストURL
	 */
	private void vollyRequest(VOLLEY_REQUEST_TYPE requestType, String url)
	{
		Listener<Bitmap> bitmapListener = new Listener<Bitmap>()
				{
					@Override
					public void onResponse(Bitmap response)
					{
						// 取得した画像結果が返ってくる
						// UIの更新などを行う。
						if(mSampleVolleyImageView == null)
						{
							return;
						}
						mSampleVolleyImageView.setImageBitmap(response);
					}
				};
		Listener<JSONObject> responseListener = new Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						// jsonパースしたレスポンス結果が返ってくる。
						// UIの更新などを行う。
					}
				};
		Response.ErrorListener errorListener = new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				// error.networkResponseにエラー内容が格納されている。
				// エラー処理を行う。
				mDialog.setMessage(error.toString());
				mDialog.show();
			}
		};
		
		RequestQueue queue = makeVolleyQueue(this.getApplicationContext());
		
		switch(requestType)
		{
		case CLEAR_CACHE_REQUEST:
			queue.add(new ClearCacheRequest(null, null));
			break;
		case IMAGE_REQUEST:
			queue.add(new ImageRequest(url, bitmapListener, 100, 100, null, errorListener));
			break;
		case JSON_ARRAY_REQUEST:
			queue.add(new JsonArrayRequest(url, null, errorListener));
			break;
		case JSON_OBJECT_REQUEST:
			queue.add(new JsonObjectRequest(
					Method.GET, 
					url, 
					null, 
					responseListener, 
					errorListener));
			break;
		case JSON_REQUEST:
//			queue.add(new JsonRequest());
			break;
		case STRING_REQUEST:
			queue.add(new StringRequest(0, url, null, errorListener));
			break;
		default:
			break;
		}
	}
	
	/**
	 * NetworkImageViewを使用して、画像をロードする
	 * @param url				ロードする画像URL
	 * @param networkImageView	NetworkImageView
	 * @param defaultImageResId	画像表示までの画像リソースID
	 * @param errorImageResId	エラー次の表示画像リソースID
	 */
	private void imageLoadByNetworkImageView(
			String url, 
			NetworkImageView networkImageView,
			int defaultImageResId,
			int errorImageResId
			)
	{
		if(networkImageView == null)
		{
			return;
		}
		
		RequestQueue queue = makeVolleyQueue(this.getApplicationContext());
		networkImageView.setDefaultImageResId(defaultImageResId);
		networkImageView.setErrorImageResId(errorImageResId);
		networkImageView.setImageUrl(url, new ImageLoader(queue, this));
	}

	@Override
	public Bitmap getBitmap(String url){return null;}
	@Override
	public void putBitmap(String url, Bitmap bitmap){}
}
