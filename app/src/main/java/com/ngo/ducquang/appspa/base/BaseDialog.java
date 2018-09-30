package com.ngo.ducquang.appspa.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.ngo.ducquang.appspa.R;

import butterknife.ButterKnife;

/**
 * Created by ducqu on 8/28/2018.
 */

public abstract class BaseDialog extends AppCompatDialogFragment
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, R.style.Theme_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        try
        {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }

        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_in));
        initView(view);
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag)
    {
        try
        {
            super.show(manager, tag);
        }
        catch (Exception e) {
            LogManager.tagDefault().error(e.toString());
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag)
    {
        try
        {
            return super.show(transaction, tag);
        }
        catch (Exception e)
        {
            LogManager.tagDefault().error(e.toString());
        }
        return -1;
    }

    public void addFragment(BaseFragment fragment, Bundle bundle, boolean addToBackStack)
    {
        ((BaseActivity) getActivity()).addFragment(fragment, bundle, addToBackStack);
    }

    public void replaceFragment(BaseFragment fragment, Bundle bundle, boolean addToBackStack)
    {
        ((BaseActivity) getActivity()).replaceFragment(fragment, bundle, addToBackStack);
    }

    protected abstract int getContentView();

    protected abstract void initView(View view);

    public interface OnPositiveClickListener {
        void onPositiveClick();
    }

    public interface OnNegativeClickListener {
        void onNegativeClick();
    }

    protected void showToast(String text, int type)
    {
        ((BaseActivity) getActivity()).showToast(text, type);
    }

    protected void showLoadingDialog()
    {
        ((BaseActivity) getActivity()).showLoadingDialog();
    }

    protected void hideLoadingDialog()
    {
        ((BaseActivity) getActivity()).hideLoadingDialog();
    }
}
