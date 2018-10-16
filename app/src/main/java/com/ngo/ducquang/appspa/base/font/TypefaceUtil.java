package com.ngo.ducquang.appspa.base.font;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import com.ngo.ducquang.appspa.base.LogManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ducqu on 10/13/2018.
 */

public class TypefaceUtil {
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets)
    {
        final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("default", customFontTypeface);
            try
            {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
            }
            catch (NoSuchFieldException e)
            {
                LogManager.tagDefault().error(e.toString());
            }
            catch (IllegalAccessException e)
            {
                LogManager.tagDefault().error(e.toString());
            }
        }
        else
        {
            try
            {
                final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
                defaultFontTypefaceField.setAccessible(true);
                defaultFontTypefaceField.set(null, customFontTypeface);
            }
            catch (Exception e)
            {
                LogManager.tagDefault().error("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
            }
        }
    }
}
