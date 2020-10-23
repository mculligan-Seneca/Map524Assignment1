package com.example.map524assignment1;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import java.util.Locale;

public class LocaleHelper {

        private static Context changeLocale(Context context, String tag)
        {
            Locale locale = new Locale(tag);
            Locale.setDefault(locale);

            Configuration config = context.getResources().getConfiguration();
            config.setLocale(locale);
            config.setLayoutDirection(locale);

            return context.createConfigurationContext(config);

        }

    private static Context changeLocaleLegacy(Context context, String tag){


        Locale locale=new Locale(tag);
        //Context context = super.getBaseContext();
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        Locale.setDefault(locale);
      /*  if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            config.setLocale(locale);
        else */
        config.locale=locale;

        config.setLayoutDirection(locale);
        res.updateConfiguration(config,res.getDisplayMetrics());

        return context;

    }



    public static Context  updateLocale(Context context,String tag){


         if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            context = changeLocale(context,tag);
        else
            context = changeLocaleLegacy(context,tag);
        return context;
    }

}
