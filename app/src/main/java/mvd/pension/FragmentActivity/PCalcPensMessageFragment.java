package mvd.pension.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mvd.pension.OnInsertListern;
import mvd.pension.OnTapListener;
import mvd.pension.PCalcMessFireBase;
import mvd.pension.PCalcMessageSQLite;
import mvd.pension.R;
import mvd.pension.adapter.MessageAdapter;
import mvd.pension.dialog.DialogMessage;


/**
 * Created by dementr on 05.10.2016.
 */

public class PCalcPensMessageFragment extends Fragment {
    private int REQUEST_DELETE = 0;

    private RecyclerView recyclerView;
    private PCalcMessageSQLite pCalcMessageSQLite;
    private ArrayList<PCalcMessFireBase> arrayList = new ArrayList<PCalcMessFireBase>();
    private Cursor cursor;
    private MessageAdapter messageAdapter;
    private int positionGlobal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
 //           mParam1 = getArguments().getString(ARG_PARAM1);
 //           mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_pcalc_message_firebase, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        loadDataBase();
        return v;
    }

    public void loadDataBase() {
        pCalcMessageSQLite = PCalcMessageSQLite.get(getActivity());


        try {
            cursor = pCalcMessageSQLite.QueryData("select * from message");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        PCalcMessFireBase item = new PCalcMessFireBase();
                        item.setId(cursor.getInt(0));
                        item.setMess1(cursor.getString(1));
                        item.setMess2(cursor.getString(2));
                        arrayList.add(item);
                    } while (cursor.moveToNext());
                }
            }
        }
        catch(SQLiteException e)
        {
           e.printStackTrace();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        messageAdapter = new MessageAdapter(getActivity(),arrayList);
        messageAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
                try {
                    positionGlobal = position;
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    cursor = pCalcMessageSQLite.QueryData("select * from message where _id ="+arrayList.get(position).getId());
                    cursor.moveToFirst();
                    DialogMessage dialogMessage = DialogMessage.newInstance(arrayList.get(position).getId(),cursor.getString(2),cursor.getString(1));
                    dialogMessage.setTargetFragment(PCalcPensMessageFragment.this, REQUEST_DELETE);
                    dialogMessage.show(fm, "dialog");
                }
                catch(SQLiteException e)
                {
                    e.printStackTrace();
                }
            }
        });

        pCalcMessageSQLite.setOnInsertListern(new OnInsertListern() {
            @Override
            public void OnInsertData(Cursor cursor) {
                if (arrayList != null) {
                    PCalcMessFireBase item = new PCalcMessFireBase();
                    item.setId(cursor.getInt(0));
                    item.setMess1(cursor.getString(1));
                    item.setMess2(cursor.getString(2));
                    arrayList.add(item);
                    messageAdapter.notifyItemInserted(arrayList.size());
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);//linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK) return;
        if (requestCode == REQUEST_DELETE) {
            Boolean aBoolean = false;
            aBoolean = (Boolean) data.getSerializableExtra(DialogMessage.EXTRA_DELETE);
            if (aBoolean) {
                //messageAdapter.notifyItemRemoved(positionGlobal);
                arrayList.remove(positionGlobal);
                messageAdapter.notifyDataSetChanged();
            }
        }
    }
}
