package com.example.a3logics.chatapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Rajeev on 30/11/17.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * following function is used to perform the fragment transaction
     */
    public void startTransaction(Context activity, Fragment fragment, int view){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(view, fragment);
        transaction.commit();
    }

    public void startTransaction(Context activity, Fragment fragment, int view, String mfragmentStr){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(view, fragment);
        transaction.addToBackStack(mfragmentStr);
        transaction.commit();
    }

    public void startTransactionAdd(Context activity, Fragment fragment, int view, String mfragmentStr){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
//        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.addToBackStack(mfragmentStr);
        transaction.add(view, fragment,null);
        transaction.commit();
    }

    public void startTransactionWithoutAnimation(Context activity, Fragment fragment, int view, String mfragmentStr){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(mfragmentStr);
        transaction.replace(view, fragment);
        transaction.commit();
    }
    public void startTransactionN(Context activity, Fragment fragment, int view, String mfragmentStr){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(view, fragment);
        transaction.commit();
    }

    public void startTransactionWithBundle(Context activity, Bundle bundle, Fragment fragment, int view, String mfragmentStr){
        FrameLayout frameLayout = ((BaseActivity)activity).findViewById(view);
        frameLayout.removeAllViews();
        FragmentTransaction transaction = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left,R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.addToBackStack(mfragmentStr);
        transaction.replace(view, fragment);
        transaction.commit();
    }

}
