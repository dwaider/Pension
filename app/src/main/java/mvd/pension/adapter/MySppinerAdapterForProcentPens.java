package mvd.pension.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import mvd.pension.R;

public class MySppinerAdapterForProcentPens extends ArrayAdapter<String> implements SpinnerAdapter {
	private final Activity context;
	private final String[] values;

	public MySppinerAdapterForProcentPens(Activity cont, String[] values) {
		super(cont, R.layout.rowspinner,R.id.txValSpinner, values);
		// TODO Auto-generated constructor stub
		this.context = cont;
		this.values = values;
	}
	
	static class ViewHolder {
		public TextView textView;
	}
	

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowspinner, parent, false);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.txValSpinner);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		String tStr = values[position].substring(values[position].indexOf(";")+1);
		String tProcent = tStr.substring(0, tStr.indexOf(";"));
		holder.textView.setText(tProcent);
		return rowView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowspinner, parent, false);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.txValSpinner);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		String tStr = values[position].substring(values[position].indexOf(";")+1);
		String tProcent = tStr.substring(0, tStr.indexOf(";"));
		holder.textView.setText(tProcent);
		return rowView;
	}
	
}
