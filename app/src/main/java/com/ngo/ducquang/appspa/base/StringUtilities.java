package com.ngo.ducquang.appspa.base;

import android.text.TextUtils;

public class StringUtilities
{
    //    a ă â  e ê i o ô ơ u ư
//    / ` ? ~ .
    private static final char[] VIET_NAM_SOURCE_CHARACTERS =
            {'á', 'à', 'ả', 'ã', 'ạ',
                    'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ',
                    'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ',
                    'é', 'è', 'ẻ', 'ẽ', 'ẹ',
                    'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ',
                    'í', 'ì', 'ỉ', 'ĩ', 'ị',
                    'ó', 'ò', 'ỏ', 'õ', 'ọ',
                    'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ',
                    'ơ', 'ớ', 'ờ', 'ở', 'ỡ', 'ợ',
                    'ú', 'ù', 'ủ', 'ũ', 'ụ',
                    'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự',
                    'Á', 'À', 'Ả', 'Ã', 'Ạ',
                    'Ă', 'Ắ', 'Ằ', 'Ẳ', 'Ẵ', 'Ặ',
                    'Â', 'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ậ',
                    'É', 'È', 'Ẻ', 'Ẽ', 'Ẹ',
                    'Ê', 'Ế', 'Ề', 'Ể', 'Ễ', 'Ệ',
                    'Í', 'Ì', 'Ỉ', 'Ĩ', 'Ị',
                    'Ó', 'Ò', 'Ỏ', 'Õ', 'Ọ',
                    'Ô', 'Ố', 'Ồ', 'Ổ', 'Ỗ', 'Ộ',
                    'Ơ', 'Ớ', 'Ờ', 'Ở', 'Ỡ', 'Ợ',
                    'Ú', 'Ù', 'Ủ', 'Ũ', 'Ụ',
                    'Ư', 'Ứ', 'Ừ', 'Ử', 'Ữ', 'Ự'
            };

    private static final char[] VIET_NAM_DESTINATION_CHARACTERS =
            {'a', 'a', 'a', 'a', 'a',
                    'a', 'a', 'a', 'a', 'a', 'a',
                    'a', 'a', 'a', 'a', 'a', 'a',
                    'e', 'e', 'e', 'e', 'e',
                    'e', 'e', 'e', 'e', 'e', 'e',
                    'i', 'i', 'i', 'i', 'i',
                    'o', 'o', 'o', 'o', 'o',
                    'o', 'o', 'o', 'o', 'o', 'o',
                    'o', 'o', 'o', 'o', 'o', 'o',
                    'u', 'u', 'u', 'u', 'u',
                    'u', 'u', 'u', 'u', 'u', 'u',
                    'A', 'A', 'A', 'A', 'A',
                    'A', 'A', 'A', 'A', 'A', 'A',
                    'A', 'A', 'A', 'A', 'A', 'A',
                    'E', 'E', 'E', 'E', 'E',
                    'E', 'E', 'E', 'E', 'E', 'E',
                    'I', 'I', 'I', 'I', 'I',
                    'O', 'O', 'O', 'O', 'O',
                    'O', 'O', 'O', 'O', 'O', 'O',
                    'O', 'O', 'O', 'O', 'O', 'O',
                    'U', 'U', 'U', 'U', 'U',
                    'U', 'U', 'U', 'U', 'U', 'U',
            };

    private static final String EXTENSION_PNG = "png";

    public static String escapeJavaString(String st)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            for (int i = 0; i < st.length(); i++)
            {
                char c = st.charAt(i);
                if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && !Character.isWhitespace(c))
                {
                    String unicode = String.valueOf(c);
                    int code = (int) c;
                    if (!(code >= 0 && code <= 255))
                    {
                        unicode = "\\\\u" + Integer.toHexString(c);
                    }
                    builder.append(unicode);
                }
                else
                {
                    builder.append(c);
                }
            }

//            Log.i("Unicode Block", builder.toString());
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }

        return builder.toString();
    }

    public static boolean isEmpty(String string)
    {
        if (string == null)
        {
            return true;
        }

        string = string.trim();
        return string.isEmpty() || TextUtils.isEmpty(string) || string.equals("") || string.length() <= 0;
    }

    public static String removeVietNamAccent(String s)
    {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++)
        {
            sb.setCharAt(i, removeVietNamAccent(sb.charAt(i)));
        }

        return sb.toString();
    }

    private static char removeVietNamAccent(char ch)
    {
//        int index = Arrays.binarySearch(VIET_NAM_SOURCE_CHARACTERS, ch);
//        if (index >= 0)
//        {
//            ch = VIET_NAM_DESTINATION_CHARACTERS[index];
//        }

        int i = 0;
        while (i < VIET_NAM_SOURCE_CHARACTERS.length)
        {
            if (VIET_NAM_SOURCE_CHARACTERS[i] == ch)
            {
                return VIET_NAM_DESTINATION_CHARACTERS[i];
            }

            i++;
        }

//        LogManager.tagDefault().error(ch + "$");
        return ch;
    }

    private static String getExtension(String fileName)
    {
        int index = fileName.lastIndexOf(".");
        if (index > 0)
        {
            return fileName.substring(index + 1, fileName.length());
        }
        else
        {
            return "";
        }
    }
}
