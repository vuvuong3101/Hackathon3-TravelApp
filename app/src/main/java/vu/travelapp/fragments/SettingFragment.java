package vu.travelapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vu.travelapp.R;

/**
 * Created by mac-vuongvu on 8/11/17.
 */

public class SettingFragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_setting, container, false);
        return view;
    }
}
