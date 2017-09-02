package vu.travelapp.managers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import vu.travelapp.R;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

/**
 * Created by ADMIN on 8/6/2017.
 */

public class ScreenManager {
    public static void openFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);//tu rep sua thanh add
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.setCustomAnimations(R.anim.anim_left, R.anim.anim_right);
        fragmentTransaction.replace(layoutID, fragment);
        Log.d(TAG, "replaceFragment: " + fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    public static void replaceFragment2(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    public static void replaceFragmentRtoL(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_right, R.anim.anim_left);
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        Log.d(TAG, "replaceFragment: " + fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    public static  void fadeAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static  void backFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate();
    }
}
