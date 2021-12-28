package org.techtown.chatbot1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class InoutPageAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public InoutPageAdapter(FragmentManager fm){
        super(fm);
    }

    public void additem(Fragment item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }
}
