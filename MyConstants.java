package com.example.statussaver.Utils;

import android.os.Environment;

import java.io.File;

public class MyConstants
{
    public static final File STATUS_DIRECTORY=
            new File(android.os.Environment.getExternalStorageDirectory()
                    .toString() +
                    File.separator + "Whatsapp/Media/.Statuses");

    //public static final String APP_DIR = Environment.getExternalStorageDirectory() +File.separator
          //  +"WhatsAppStatusProDir";
    public static final String APP_DIR = android.os.Environment.getExternalStorageDirectory()+File.separator
             +"WhatsAppStatusProDir";

    public static final int THUMBSIZE = 128;
}
