package com.assignment.manish.movies.utils;

import android.content.Context;
import android.os.Environment;

import com.assignment.manish.movies.persistence.SqliteDatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Utility
{
    public static void checkoutDB(Context context)
    {
        try {
            File file = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (file.canWrite()) {
                String currentPath = "/data/user/0/com.assignment.manish.movies/databases/Assignment";
                String copyPath = "copydb_name.db";
                File currentDB = new File(currentPath);
                File backupDB = new File(file, copyPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
