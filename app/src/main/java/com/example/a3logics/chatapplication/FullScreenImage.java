package com.example.a3logics.chatapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by a3logics on 22/3/18.
 */

public class FullScreenImage extends DialogFragment implements View.OnClickListener {

    private ImageView mBackButtonImg,mFullScreenImg;
    private TextView  mDialogTitle;
    private String    mTitle,mImageUrl;
    ProgressBar mImgLoadProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_full_screen_layout, container, false);
        getDialog().setTitle("Select Vendor Type");
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mTitle = bundle.getString(getString(R.string.dialog_title));
            mImageUrl = bundle.getString(getString(R.string.dialog_img_url));
        }
        initview(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }

        // set the animations to use on showing and hiding the dialog
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }

    void initview(View v){
        mBackButtonImg      = v.findViewById(R.id.bt_back);
        mFullScreenImg      = v.findViewById(R.id.img_full_screen);
        mDialogTitle        = v.findViewById(R.id.titleDialog);
        mImgLoadProgressBar = v.findViewById(R.id.progress);

        mDialogTitle.setText(mTitle);
        mBackButtonImg.setOnClickListener(this);
        Glide.with(getActivity())
                .load(Uri.parse(mImageUrl))
                .listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mImgLoadProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mImgLoadProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(mFullScreenImg);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                 dismiss();
                 break;
        }

    }
}
