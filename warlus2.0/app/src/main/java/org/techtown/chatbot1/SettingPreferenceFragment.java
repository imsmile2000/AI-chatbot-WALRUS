package org.techtown.chatbot1;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

public class SettingPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
    private static final String TAG = "SettingPreferenceFragment";
    SharedPreferences prefs;
    private static final String COLOR_LIST="color_list";
    private static final String FONT_LIST="font_list";
    private static final String SIZE_LIST="size_list";

    private ListPreference keywordSoundPreference;
    private ListPreference mTextsize;
    private PreferenceScreen keywordScreen;



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);

        mTextsize = (ListPreference)findPreference(SIZE_LIST);
        mTextsize.setOnPreferenceChangeListener(this);




        keywordSoundPreference = (ListPreference)findPreference("keyword_sound_list");



        keywordScreen = (PreferenceScreen)findPreference("keyword_screen");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        if(!prefs.getString("keyword_sound_list", "").equals("")){
            keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list", "20pt"));
        }

        if(prefs.getBoolean("keyword", false)){
            keywordScreen.setSummary("사용");
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }// onCreate

    @Override
    public void onResume(){
        super.onResume();
    }


    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("keyword_sound_list")){
                keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list", "20pt"));
            } else if (key.equals("size_list")) {
                Log.d("TAG", key + "SELECTED");
                Intent intent=new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(intent);
            }

            if(key.equals("keyword")){

                if(prefs.getBoolean("keyword", false)){
                    keywordScreen.setSummary("사용");

                }else{
                    keywordScreen.setSummary("사용안함");
                }

                //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
                ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
            }

        }
    };

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i(TAG, "preference:"+preference+"newvalue: "+newValue);
        String value=(String)newValue;
        if(preference==mTextsize){
            ListPreference listPreference=(ListPreference) preference;
            int index=listPreference.findIndexOfValue(value);
            mTextsize.setSummary(index>=0?listPreference.getEntries()[index]:null);
        }
        return true;
    }
}