package com.itgenz.studentmanage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecureSharedPref
{
    private static final String FILE_NAME = "SecuredPref";
    private static SharedPreferences sharedPreferences;
    public static SharedPreferences getEncryptedSharedPreferences(Context context){
        if(sharedPreferences == null){
            try {
                MasterKey masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
                sharedPreferences = EncryptedSharedPreferences.create(
                        context,
                        FILE_NAME,
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            } catch (GeneralSecurityException | IOException e) {
                Log.d("ESP", "ERROR: " + e.getMessage());
            }
        }
        return sharedPreferences;
    }
}
