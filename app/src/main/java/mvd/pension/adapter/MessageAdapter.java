package mvd.pension.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import mvd.pension.OnTapListener;
import mvd.pension.PCalcMessFireBase;
import mvd.pension.R;
import mvd.pension.SetViewHolder;

/**
 * Created by Dmitry on 03.10.2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<SetViewHolder> {
    private Activity activity;
    List<PCalcMessFireBase> items = Collections.emptyList();
    private OnTapListener OnTapListener;

    public MessageAdapter(Activity activity,List<PCalcMessFireBase> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, final int position) {
        holder.text_mess1.setText(items.get(position).getMess1());
        holder.text_mess2.setText(items.get(position).getMess2());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnTapListener != null) {
                    OnTapListener.OnTapView(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnTapListener(OnTapListener OnTapListner) {
        this.OnTapListener = OnTapListner;
    }
}
