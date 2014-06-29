/**
 * ImageLoaderを使ったアダプターサンプルクラス
 */
package com.example.volleysample;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author kouichi
 *
 */
public class ImageLoaderSampleAdapter extends ArrayAdapter<String> implements ImageCache
{
	private RequestQueue mQueue;
	private ImageLoader mImageLoader;
	private LayoutInflater mInflater;
	
	static class ViewHolder
	{
		ImageView image;
		TextView body;
	}
	
	public ImageLoaderSampleAdapter(
			Context context, 
			List<String> urls, 
			RequestQueue queue)
	{
		super(context, 0, urls);
		this.mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		this.mImageLoader = new ImageLoader(queue, this);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		
		if(convertView == null)
		{
			convertView = this.mInflater.inflate(
					R.layout.volley_list_item, 
					parent, 
					false);
			holder = new ViewHolder();
			holder.body = (TextView)convertView.findViewById(R.id.volleyTextView);
			holder.image = (ImageView)convertView.findViewById(R.id.volleyImageView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		String url = this.getItem(position);		
		holder.body.setText(url);
		ImageListener imageListener = ImageLoader.getImageListener(
				holder.image, 
				android.R.drawable.spinner_background, 
				android.R.drawable.ic_dialog_alert);
		this.mImageLoader.get(url, imageListener, 100, 100);
		
		return convertView;
	}

	@Override
	public Bitmap getBitmap(String url){return null;}
	@Override
	public void putBitmap(String url, Bitmap bitmap){}
}
