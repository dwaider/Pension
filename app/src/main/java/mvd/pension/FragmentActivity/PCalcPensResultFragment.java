package mvd.pension.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mvd.pension.Core.PCalc;
import mvd.pension.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PCalcPensResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PCalcPensResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PCalcPensResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView txRaionKoeff;
    private TextView txDolOklad;
    private TextView txZvanOklad;
    private TextView txProcNadb;
    private TextView txProcNadbName;
    private TextView txSumDenDov;
    private TextView txRazmPensVProcent;
    private TextView txRaionKoeffName;
    private TextView txDenDovForIschislPensii;
    private TextView txDenDovForIschName;
    private TextView txRasmPensii;
    private TextView txRasmPensiiUchetRaionKoeff;
    private TextView txRasmMinPensii;
    private TextView txNadb;
    private TextView txItog;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PCalc pens;
    private OnFragmentInteractionListener mListener;

    public PCalcPensResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PCalcPensResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PCalcPensResultFragment newInstance(String param1, String param2) {
        PCalcPensResultFragment fragment = new PCalcPensResultFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result_calc, parent, false);
        txDolOklad = (TextView)v.findViewById(R.id.txDolOklad);
        txZvanOklad = (TextView)v.findViewById(R.id.txZvanOklad);
        txProcNadb = (TextView)v.findViewById(R.id.txVislProcNadb);
        txRaionKoeff = (TextView)v.findViewById(R.id.txRaionKoeff);
        txSumDenDov = (TextView)v.findViewById(R.id.txSumDov);
        txRazmPensVProcent = (TextView)v.findViewById(R.id.txRazmVProcent);
        txProcNadbName = (TextView)v.findViewById(R.id.txNameProcNadb);
        txRaionKoeffName = (TextView)v.findViewById(R.id.txNameRaionKoeff);
        txDenDovForIschislPensii = (TextView)v.findViewById(R.id.txDenDovForIschislPensii);
        txDenDovForIschName = (TextView)v.findViewById(R.id.txDenDovForIsch);
        txRasmPensii = (TextView)v.findViewById(R.id.txRasmPensii);
        txRasmPensiiUchetRaionKoeff = (TextView)v.findViewById(R.id.txRazmUchetRaionKoeff);
        txRasmMinPensii = (TextView)v.findViewById(R.id.txMinPensii);
        txItog = (TextView)v.findViewById(R.id.txItog);
        //	lstNadbavki = (ListView)v.findViewById(R.id.lstNadbvka);
        //layNadbavka = (LinearLayout)v.findViewById(R.id.layNadbvka1);
        //lstNadbavki.se
        txNadb = (TextView)v.findViewById(R.id.txNadbavka);
        UpdateTX();
        pens.setChangeParam(new PCalc.ChangeParam() {
            @Override
            public void onChangeParam() {
                // TODO Auto-generated method stub
                UpdateTX();
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
    public void UpdateTX(){
        try {
            txRasmMinPensii.setText(String.format("%.2f",pens.getpMinPens()));
            txSumDenDov.setText(String.format("%.2f",pens.getpSumDenDov()));
            txDolOklad.setText(String.format("%.2f",pens.getpOkladDolg()));
            txZvanOklad.setText(String.format("%.2f",pens.getpOkladZvani()));
            txProcNadb.setText(String.format("%.2f",pens.getpVislLet()));
            txProcNadbName.setText(String.valueOf(
                    String.format(getResources().getString(R.string.pcalc_nadb_visl_for_pensi),
                            String.format("%.0f",pens.getPsVislLet()))));
            txRaionKoeffName.setText(String.valueOf(
                    String.format(getResources().getString(R.string.pcalc_nadb_raion_koeff),
                            String.format("%.0f",pens.getpRaionKoeffRas()))));
            txDenDovForIschislPensii.setText(String.format("%.2f",pens.getpSumDenDovForRashen()));
            txRazmPensVProcent.setText(String.format("%.0f%%",pens.getpRasmPensiiVProcentah()));
            txRaionKoeff.setText(String.format("%.2f",pens.getRaionKoeffSum()));
            txDenDovForIschName.setText(String.valueOf(
                    String.format(getResources().getString(R.string.pcalc_sum_den_dov_for_isch),
                            String.format("%.2f",pens.getProcentForPensii()))));
            txRasmPensii.setText(String.format("%.2f",pens.getpRasmPensii()));
            txRasmPensiiUchetRaionKoeff.setText(String.format("%.2f",pens.getpRasmPensiiRaionKoeff()));
            //txItog.setText(String.format("%.2f",pens.getpItogSum()));
            txItog.setText(String.valueOf(
                    String.format(getResources().getString(R.string.pcalc_result_text),
                            String.format("%.2f",pens.getpItogSum())))+" руб. "+pens.getpDataRashet());
            txNadb.setText(pens.getpNadbavki_string());
        } catch (Exception e) {
            // TODO: handle exception
            //Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

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
