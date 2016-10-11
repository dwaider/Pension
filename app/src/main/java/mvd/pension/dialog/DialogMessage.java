package mvd.pension.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import mvd.pension.R;

/**
 * Created by dementr on 11.10.2016.
 */

public class DialogMessage extends DialogFragment {
    private static final String EXTRA_POSITION = "mvd.pension.dialog.position";
    private static String EXTRA_TEXT_MESSAGE = "mvd.pension.dialog.text_message";
    private String mMessage;
    private int mPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMessage = (String) getArguments().getSerializable(EXTRA_TEXT_MESSAGE);
        mPosition = (int) getArguments().getSerializable(EXTRA_POSITION);
        return new AlertDialog.Builder(getActivity())
                .setTitle("Тест диалога" + mPosition + " " + mMessage)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    public static DialogMessage newInstance(int position,String textMessage) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_POSITION, position);
        args.putSerializable(EXTRA_TEXT_MESSAGE, textMessage);
        DialogMessage fragment = new DialogMessage();
        fragment.setArguments(args);
        return fragment;
    }
}
