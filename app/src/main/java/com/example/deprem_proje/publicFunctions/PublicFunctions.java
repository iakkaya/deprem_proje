package com.example.deprem_proje.publicFunctions;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.deprem_proje.MainActivity;
import com.example.deprem_proje.auth.KayitOl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PublicFunctions {
    public void GoPage(android.content.Context packageContext,
                       Class<?> cls, Bundle options){
        Intent intent = new Intent(packageContext, cls);
        startActivity(packageContext, intent,options);
    }

    public String getCurrenDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        SimpleDateFormat sdf = new SimpleDateFormat("'t_'dd.MM.yyyy'__'HH:mm:ss");
        String biçimlendirilmişTarih = sdf.format(calendar.getTime());
        return  biçimlendirilmişTarih;
    }
}
