package com.ngo.ducquang.appspa.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ducqu on 6/6/2018.
 */

public class ManagerTime
{
    public static long getMilisecondFromDate(String dateFormat)
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try
        {
            date = formatter.parse(dateFormat);
        }
        catch (ParseException e)
        {
            LogManager.tagDefault().error(e.toString());
        }

        return date.getTime();
    }

    public static String convertToMonthDayYear(long timestamp)
    {
        if (timestamp <= 0)
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertToTime(long timestamp)
    {
        if (timestamp <= 0)
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertToMonthDay(long timestamp)
    {
        if (timestamp <= 0)
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertToMonthDayYearHourMinuteFormat(long timestamp)
    {
        if (timestamp <= 0)
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String convertToMonthDayYearHourMinuteFormatSlash(long timestamp)
    {
        if (timestamp <= 0)
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static boolean checkTimeToday(String dateFormat)
    {
        Date currentTime = Calendar.getInstance().getTime();
        if (getMilisecondFromDate(dateFormat) > currentTime.getTime())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

//    public static boolean checkFailedVadidate(Context context, String startDate, String endDate)
//    {
//        Date currentTime = Calendar.getInstance().getTime();
//
//        if (StringUtilities.isEmpty(startDate) || StringUtilities.isEmpty(endDate))
//        {
//            CustomToast.error(context, "Không được để trống thời gian!!!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        if (!(getMilisecondFromDate(startDate) > currentTime.getTime() && getMilisecondFromDate(endDate) > currentTime.getTime()))
//        {
//            CustomToast.error(context, "Ngày bắt đầu và kết thúc không được nhỏ hơn ngày hiện tại", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (getMilisecondFromDate(startDate) >= getMilisecondFromDate(endDate) || startDate.equals(endDate))
//        {
//            CustomToast.error(context, "Ngày bắt đầu không được lớn hơn hoặc bằng ngày kết thúc", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return false;
//    }

//    public static boolean checkDateRemindVadidate(Context context, String startDate, String endDate, String dateRemind)
//    {
//        Date currentTime = Calendar.getInstance().getTime();
//        long startDateLong = getMilisecondFromDate(startDate);
//        long endDateLong = getMilisecondFromDate(endDate);
//        long remindDateLong = getMilisecondFromDate(dateRemind);
//
//        if (StringUtilities.isEmpty(startDate) || StringUtilities.isEmpty(endDate))
//        {
//            CustomToast.error(context, "Không được để trống thời gian!!!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (!(startDateLong > currentTime.getTime() && endDateLong > currentTime.getTime()))
//        {
//            CustomToast.error(context, "Ngày bắt đầu và kết thúc không được nhỏ hơn ngày hiện tại", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (startDateLong >= endDateLong || startDate.equals(endDate))
//        {
//            CustomToast.error(context, "Ngày bắt đầu không được lớn hơn hoặc bằng ngày kết thúc", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (startDateLong >= remindDateLong)
//        {
//            CustomToast.error(context, "Ngày bắt đầu không được lớn hơn hoặc bằng ngày nhắc nhở việc", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (remindDateLong >= endDateLong)
//        {
//            CustomToast.error(context, "Ngày nhắc việc không được lớn hơn hoặc bằng ngày kết thúc", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return false;
//    }

//    public static boolean checkFailedVadidateSearchChart(Context context, String startDate, String endDate)
//    {
//        Date currentTime = Calendar.getInstance().getTime();
//
//        if (StringUtilities.isEmpty(startDate) || StringUtilities.isEmpty(endDate))
//        {
//            CustomToast.error(context, "Không được để trống thời gian!!!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (getMilisecondFromDate(endDate) < getMilisecondFromDate(startDate))
//        {
//            CustomToast.error(context, "Ngày kết thúc không được nhỏ hơn ngày bắt đầu", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return false;
//    }

}
