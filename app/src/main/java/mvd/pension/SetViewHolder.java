package mvd.pension;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Dmitry on 30.09.2016.
 */

public class SetViewHolder extends RecyclerView.ViewHolder {
    public TextView text_mess1;
    public TextView text_mess2;

    public SetViewHolder(View itemView) {
        super(itemView);
        text_mess1 = (TextView) itemView.findViewById(R.id.text_mess1);
        text_mess2 = (TextView) itemView.findViewById(R.id.text_mess2);
    }
}
