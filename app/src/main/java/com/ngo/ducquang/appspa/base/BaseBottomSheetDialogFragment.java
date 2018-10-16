package com.ngo.ducquang.appspa.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.ngo.ducquang.appspa.R;
import com.ngo.ducquang.appspa.base.font.FontChangeCrawler;

import butterknife.ButterKnife;

/**
 * Created by ducqu on 9/29/2018.
 */

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style)
    {
        super.setupDialog(dialog, style);

        View view = View.inflate(getContext(), getContentView(), null);
        ButterKnife.bind(this, view);
        initView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), GlobalVariables.FONT_BASE);
        fontChanger.replaceFonts((ViewGroup) view);

        dialog.setContentView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected abstract int getContentView();

    protected abstract void initView(View view);

    protected void showToast(String text, int type)
    {
        ((BaseActivity) getActivity()).showToast(text, type);
    }

    public void startActivity(Class<?> activity, Bundle bundle, boolean clearTask)
    {
        if (getActivity() instanceof BaseActivity)
        {
            ((BaseActivity) getActivity()).startActivity(activity, bundle, clearTask);
        }
    }

    public void addFragment(BaseFragment fragment, Bundle bundle, boolean addToBackStack)
    {
//        ((BaseActivity) getActivity()).addFragment(fragment, bundle, addToBackStack);
        if (bundle != null)
        {
            fragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
        fragmentTransaction.add(android.R.id.content, fragment);
        if (addToBackStack)
        {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    public void showLoadingDialog()
    {
        ((BaseActivity) getActivity()).showLoadingDialog();
    }

    public void hideLoadingDialog()
    {
        ((BaseActivity) getActivity()).hideLoadingDialog();
    }
}
