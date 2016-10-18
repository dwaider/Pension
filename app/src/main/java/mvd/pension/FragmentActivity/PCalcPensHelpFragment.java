package mvd.pension.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mvd.pension.R;

import static android.text.Html.*;

/**
 * Created by dementr on 18.10.2016.
 */

public class PCalcPensHelpFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pcalc_help, container, false);
        TextView txSpravochnik = (TextView) v.findViewById(R.id.txSpravochnikVisl);
        //Html.fromHtml(source)
        txSpravochnik.setText(fromHtml(getStringFromFile(getActivity())));
        return v;
    }

    private String getStringFromFile(FragmentActivity activity) {
        // TODO Auto-generated method stub
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.spr_visl_help);
        String myText = null;
        try {
            myText = convertToString(is);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return myText;
    }

    private String convertToString(InputStream is) throws IOException {
        // TODO Auto-generated method stub
        ByteArrayOutputStream fileRaw = new ByteArrayOutputStream();
        int i = is.read();
        while( i!= -1) {
            fileRaw.write(i);
            i =is.read();
        }
        return fileRaw.toString();
    }
}
