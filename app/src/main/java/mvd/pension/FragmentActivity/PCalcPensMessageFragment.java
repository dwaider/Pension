package mvd.pension.FragmentActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mvd.pension.OnTapListener;
import mvd.pension.PCalcMessFireBase;
import mvd.pension.PCalcMessageSQLite;
import mvd.pension.R;
import mvd.pension.adapter.MessageAdapter;


/**
 * Created by dementr on 05.10.2016.
 */

public class PCalcPensMessageFragment extends Fragment {
    private RecyclerView recyclerView;
    private PCalcMessageSQLite pCalcMessageSQLite;
    private ArrayList<PCalcMessFireBase> arrayList = new ArrayList<PCalcMessFireBase>();
    private Cursor cursor;
    private MessageAdapter messageAdapter;
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
        pCalcMessageSQLite = new PCalcMessageSQLite(getActivity());
        try {
            pCalcMessageSQLite.checkAndCopyDatabase();
            pCalcMessageSQLite.openDataBase();
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
        try {
            cursor = pCalcMessageSQLite.QueryData("select * from message");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        PCalcMessFireBase item = new PCalcMessFireBase();
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
                Toast.makeText(getActivity(),"pos = " + position,Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
    }
}
