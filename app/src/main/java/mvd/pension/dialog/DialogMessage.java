package mvd.pension.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import mvd.pension.PCalcMessageSQLite;
import mvd.pension.R;

/**
 * Created by dementr on 11.10.2016.
 */

public class DialogMessage extends DialogFragment {
    private static final String EXTRA_POSITION = "mvd.pension.dialog.position";
    private static final String EXTRA_TEXT_MESSAGE = "mvd.pension.dialog.text_message";
    private static final String EXTRA_TEXT_MESSAGE_DATA = "mvd.pension.dialog.text_message_data";
    public static final String EXTRA_DELETE = "mvd.pension.dialog.delete";


    private String mMessage = null;
    private String mMessage_data = null;
    private int mPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMessage = (String) getArguments().getSerializable(EXTRA_TEXT_MESSAGE);
        mMessage_data = (String) getArguments().getSerializable(EXTRA_TEXT_MESSAGE_DATA);
        mPosition = (int) getArguments().getSerializable(EXTRA_POSITION);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(mMessage_data + " " + mMessage)
                .setNegativeButton(R.string.dialog_button_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.dialog_button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PCalcMessageSQLite v=  new PCalcMessageSQLite(getActivity());
                        v.openDataBase();
                        if (v.deleteId(mPosition) == 1 ) {//количество удаленных строк
                            sendResult(getActivity().RESULT_OK,mPosition);
                        }
                        dialog.cancel();
                    }
                })
                .create();
         return alertDialog;
    }

    private void sendResult(int resultCode,int position) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DELETE, true);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    public static DialogMessage newInstance(int position,String textMessage, String textMessageData) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_POSITION, position);
        args.putSerializable(EXTRA_TEXT_MESSAGE, textMessage);
        args.putSerializable(EXTRA_TEXT_MESSAGE_DATA, textMessageData);
        DialogMessage fragment = new DialogMessage();
        fragment.setArguments(args);
        return fragment;
    }
}
