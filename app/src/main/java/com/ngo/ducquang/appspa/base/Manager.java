package com.ngo.ducquang.appspa.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QuangND on 12/13/2017.
 */

public class Manager {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void startActivity(Context context, Class<?> activity, boolean clearTask) {
        Intent intent = new Intent(context, activity);
        if (clearTask)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String convertToDayMonthYear(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static boolean checkHtmlFormat(String text) {
        Pattern p = Pattern.compile("<code>(\\S+)</code>");
        Matcher m = p.matcher(text);

        return m.find();
    }

    public static String getTheFirstName(String text) {
        if (text.contains(" ")) {
            LogManager.tagDefault().error("có nhiều từ");
            String firstLetters = "";
            text = text.replaceAll("[.,]", ""); // Replace dots, etc (optional)
            for (String s : text.split(" ")) {
                firstLetters += s.charAt(0);
            }
            return firstLetters;
        } else {
            LogManager.tagDefault().error("có 1 từ");
            return text;
        }
    }

    public static boolean checkSpecialCharacter(String text) {
        Pattern p = Pattern.compile("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        boolean checked = m.find();
        return checked;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void setPaddingView(Context context, View view, int left, int top, int right, int bottom)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpLeft  = (int) (left * scale);
        int dpTop = (int) (top * scale);
        int dpRight = (int) (right * scale);
        int dpBottom = (int) (bottom * scale);

        view.setPadding(dpLeft, dpTop, dpRight, dpBottom);
    }
}
