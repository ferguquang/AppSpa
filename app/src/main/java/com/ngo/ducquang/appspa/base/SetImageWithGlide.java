package com.ngo.ducquang.appspa.base;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by QuangND on 1/8/2018.
 */

public class SetImageWithGlide
{
    public static void setImageUrlGlide(String url, ImageView imageView)
    {
        String root = GlobalVariables.ROOT;
        Glide.with(imageView.getContext())
                .load(root + url)
                .into(imageView);
    }

    public static void setImageLocal(int idImage, ImageView imageView)
    {
        Glide.with(imageView.getContext())
                .load(idImage)
                .into(imageView);
    }
}
