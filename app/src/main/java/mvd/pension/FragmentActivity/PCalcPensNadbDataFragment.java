package mvd.pension.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import mvd.pension.adapter.MySppinerAdapterForProcentPens;
import mvd.pension.Core.PCalc;
import mvd.pension.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PCalcPensNadbDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PCalcPensNadbDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PCalcPensNadbDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PCalc pens;

    private OnFragmentInteractionListener mListener;

    public PCalcPensNadbDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PCalcPensNadbDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PCalcPensNadbDataFragment newInstance(String param1, String param2) {
        PCalcPensNadbDataFragment fragment = new PCalcPensNadbDataFragment();
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
        View v = inflater.inflate(R.layout.fragment_nadb_calc, container, false);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getpRayonKoef());
        Spinner spNadbRaionKoeff = (Spinner) v.findViewById(R.id.spNadbRaionKoeff);
        spNadbRaionKoeff.setAdapter(adapter);
        //загрузка сохраненных данных
        spNadbRaionKoeff.setSelection(adapter.getPosition(String.valueOf((int)pens.getpRaionKoeffRas())));
        spNadbRaionKoeff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                pens.setpRayonKoeff(position);
                //if (position==1) bayPay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        CheckBox chVetBoevDest = (CheckBox) v.findViewById(R.id.chVetBoevDest);
        if (!pens.ispBay_save_and_nadbav()) {chVetBoevDest.setEnabled(false);}//если куплено
        else chVetBoevDest.setEnabled(true);
        chVetBoevDest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                pens.setpVetBoevDeist(isChecked);
            }
        });
        chVetBoevDest.setChecked(pens.ispVetBoevDeist());

        MySppinerAdapterForProcentPens adapter1 = new MySppinerAdapterForProcentPens(getActivity(),pens.getpProcentForPensii());
        Spinner spProcentForPensi = (Spinner) v.findViewById(R.id.spProcentForPensi);
        spProcentForPensi.setAdapter(adapter1);
        spProcentForPensi.setSelection(adapter1.getPosition(String.valueOf(pens.getProcentForPensii())));
        spProcentForPensi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                pens.setpProcentForPensii(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getDataKolIgdevency());
        Spinner spKolIgdev = (Spinner) v.findViewById(R.id.spIgdevency);
        if (!pens.ispBay_save_and_nadbav()) {spKolIgdev.setEnabled(false);}//если куплено
        else spKolIgdev.setEnabled(true);
        spKolIgdev.setAdapter(adapter2);
        spKolIgdev.setSelection(adapter2.getPosition(pens.getKolIgdevencev()));
        spKolIgdev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //попробуем здесь сделать биллинг на рассчет пенсии с учетом
                pens.setpIgdevency(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
