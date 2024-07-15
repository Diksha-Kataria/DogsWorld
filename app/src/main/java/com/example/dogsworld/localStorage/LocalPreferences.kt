package com.example.dogsworld.localStorage

import android.content.Context
import com.example.dogsworld.data.Dog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class LocalPreferences(iContext: Context?) {

    var mContext: Context? = iContext
    private val PREFS_FILE_NAME = "dog_data_prefs"
    private val LOGIN_STATUS = "isLoggedIn"
    val USER_DETAIL = "phone_num"
    val DOG_LIST = "dog_list"


    private var mInstance: LocalPreferences? = null

    @Synchronized
    fun getInstance(iContext: Context?): LocalPreferences {
        if (mInstance == null) {
            mInstance = LocalPreferences(iContext)
        } else {
        }
        return mInstance as LocalPreferences
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEditor = aMyPrefs.edit()
        prefsEditor.putBoolean(LOGIN_STATUS, isLoggedIn)
        prefsEditor.commit()
    }

    fun getIsLoggedIn(): Boolean {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return aMyPrefs.getBoolean(LOGIN_STATUS, false)
    }

    fun setUser(phoneNum: String) {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEditor = aMyPrefs.edit()
        prefsEditor.putString(USER_DETAIL, phoneNum)
        prefsEditor.commit()
    }

    fun getUser(): String? {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return aMyPrefs.getString(USER_DETAIL, "000")
    }

    fun setDogList(dogsList: ArrayList<Dog>) {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEditor = aMyPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(dogsList)
        prefsEditor.putString(DOG_LIST, json)
        prefsEditor.commit()
    }

    fun getDogList(): ArrayList<Dog>? {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = aMyPrefs.getString(DOG_LIST, "")
        val type: Type = object : TypeToken<ArrayList<Dog?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearSavedData() {
        val aMyPrefs = mContext!!.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEditor = aMyPrefs.edit()
        prefsEditor.clear().commit()
    }
}