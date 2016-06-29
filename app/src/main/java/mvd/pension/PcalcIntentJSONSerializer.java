package mvd.pension;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
public class PcalcIntentJSONSerializer {
	private Context mContext;
	private String mFilename;
	private static PcalcIntentJSONSerializer pJSONSerializer;
	
	private PcalcIntentJSONSerializer(Context context) {
		this.mContext = context;

	}
	public static PcalcIntentJSONSerializer get(Context context){
		if (pJSONSerializer == null) {
			pJSONSerializer = new PcalcIntentJSONSerializer(context);
		}
		return pJSONSerializer;
	}

	public void savePensCalc(PCalc pens,String fileName)
		throws JSONException, IOException {
		mFilename = fileName;
		JSONArray array = new JSONArray();
	    array.put(pens.toJSON());
	    Writer writer = null;
	    try {
	    	OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
	    	writer = new OutputStreamWriter(out);
	    	writer.write(array.toString());
	    } finally {
	    	if (writer != null)
	    		writer.close();
	    }
	}
	
	public PCalc loadPcalc(PCalc pens,String fileName) throws IOException, JSONException {
		mFilename = fileName;
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			.nextValue();
			for (int i = 0; i < array.length(); i++) {
				pens.loadPCalc(array.getJSONObject(i));
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (reader != null)
				reader.close();
		}
		return pens;
	}

}
