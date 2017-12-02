package com.upcomingmovies.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.upcomingmovies.R;
import com.upcomingmovies.model.ImageList;
import com.upcomingmovies.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Shuhab abs-pc-2f-28 on 2/12/17.
 */

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<ImageList> imageLists;

    public CustomPagerAdapter(Context context, ArrayList<ImageList> imageLists) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLists = imageLists;

    }

    @Override
    public int getCount() {
        return imageLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(Constant.IMAGE_BASE_URL+imageLists.get(position).getImagepath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView);
//        imageView.setImageResource(imageLists.get(position).getImagepath());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
