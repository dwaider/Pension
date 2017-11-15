package mvd.pension.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import mvd.pension.Core.PCalc;
import mvd.pension.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PCalcPensDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PCalcPensDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PCalcPensDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner spOkladZvan;
    private EditText pOkladDolg;
    private EditText pProcentNadbv;
    private EditText pKalendVisl;
    private CheckBox chSmeshPens;
    private EditText pObsheTrudVisl;

    private PCalc pens;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PcalcPensDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PCalcPensDataFragment newInstance(String param1, String param2) {
        PCalcPensDataFragment fragment = new PCalcPensDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        pens = PCalc.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pens_calc, container, false);
        pOkladDolg = (EditText)v.findViewById(R.id.edPOklad_dolg);
        pProcentNadbv = (EditText)v.findViewById(R.id.edPProcent_nadb);
        pKalendVisl = (EditText)v.findViewById(R.id.edKalendVisl);
        pObsheTrudVisl = (EditText)v.findViewById(R.id.edObsheTrud);
        pObsheTrudVisl.setEnabled(pens.ispSmeshPens());
        //расчет по смешанному стажу
        chSmeshPens = (CheckBox)v.findViewById(R.id.chSmeshPens);
        chSmeshPens.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                          pens.setpSmeshPens(isChecked);
                                                          pObsheTrudVisl.setEnabled(pens.ispSmeshPens());
                                                   }
                                               });

        chSmeshPens.setChecked(pens.ispSmeshPens());
        spOkladZvan = (Spinner)v.findViewById(R.id.spPOklad_zvan);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getpZvanOklad());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOkladZvan.setAdapter(adapter);
        //загрузка сохраненных данных
        spOkladZvan.setSelection(adapter.getPosition(pens.getpOkladZvanString()));
        spOkladZvan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                pens.setpZvanOklad(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        if (pens.getpOkladDolg() != 0) pOkladDolg.setText(String.valueOf((int)pens.getpOkladDolg()));
        if (pens.getVislLetPoln() != 0) pProcentNadbv.setText(String.valueOf((int)pens.getVislLetPoln()));
        if (pens.getpKlandVisl() != 0) pKalendVisl.setText(String.valueOf((int)pens.getpKlandVisl()));
        if (pens.getpObsheTrudVisl() != 0) pObsheTrudVisl.setText(String.valueOf((int)pens.getpObsheTrudVisl()));

        pOkladDolg.addTextChangedListener(new GenericTextWatcher(pOkladDolg));
        pProcentNadbv.addTextChangedListener(new GenericTextWatcher(pProcentNadbv));
        pKalendVisl.addTextChangedListener(new GenericTextWatcher(pKalendVisl));
        pObsheTrudVisl.addTextChangedListener(new GenericTextWatcher(pObsheTrudVisl));
        pKalendVisl.setOnFocusChangeListener(new GenFocus());

        return v;
    }

    private class GenFocus implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if (!hasFocus) {
                switch(v.getId()){
                    case R.id.edKalendVisl:
                        if (pens.getpKlandVisl()< 20) {
                            ((EditText)v).setError("Право на пенсию не наступило.");
                        }
                        break;
                }
            }
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class GenericTextWatcher implements TextWatcher {
        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence ch, int i, int i1, int i2) {
            try {
                Integer TextToFloat = Integer.parseInt(ch.toString());
                switch(view.getId()){
                    case R.id.edPOklad_dolg:
                        pens.setpOkladDolg(TextToFloat);
                        break;
                    case R.id.edPProcent_nadb:
                        pens.setpVislLet(TextToFloat);
                        break;
                    case R.id.edKalendVisl:
                        pens.setpKlandVisl(TextToFloat);
                        break;
                    case R.id.edObsheTrud:
                        pens.setpObsheTrudVisl(TextToFloat);
                        break;
                }
            } catch (Exception e) {
                // TODO: handle exception
                //Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
