package com.ngo.ducquang.appspa.base.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ngo.ducquang.appspa.base.StringUtilities;

/**
 * Created by ducqu on 9/23/2018.
 */

public class AddingArrayDialog extends DialogFragment {
    private static final String ARRAY = "array";
    private static final String TITLE = "title";

    private AddingArrayDialogListener addingArrayDialogListener = null;

    public static AddingArrayDialog initialize(String[] array, AddingArrayDialogListener listener)
    {
        return initialize(null, array, listener);
    }

    public static AddingArrayDialog initialize(String title, String[] array, AddingArrayDialogListener listener)
    {
        AddingArrayDialog addingArrayDialog = new AddingArrayDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ARRAY, array);
        bundle.putString(TITLE, title);
        addingArrayDialog.setArguments(bundle);
        addingArrayDialog.setAddingArrayDialogListener(listener);
        return addingArrayDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final String[] array = getArguments().getStringArray(ARRAY);
        final String title = getArguments().getString(TITLE, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!StringUtilities.isEmpty(title))
        {
            builder.setTitle(title);
        }

        if (array != null)
        {
            builder.setItems(array, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    // The 'which' argument contains the index position of the selected item
                    if (addingArrayDialogListener != null)
                    {
                        addingArrayDialogListener.onAddingArrayDialogClick(which, (String) array[which]);
                    }
                }
            });
        }

        return builder.create();
    }

    public void setAddingArrayDialogListener(AddingArrayDialogListener addingArrayDialogListener)
    {
        this.addingArrayDialogListener = addingArrayDialogListener;
    }
}
