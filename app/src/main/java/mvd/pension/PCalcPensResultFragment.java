package mvd.pension;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

    private String[] mGroupsDenDov;
    private String[] mDenDovStruct;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PCalc pens;
    private ExpandableListView elvView;

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
        mGroupsDenDov = getResources().getStringArray(R.array.pcalc_result_data_zago);
        mGroupsDenDov = getResources().getStringArray(R.array.pcalc_result_data_den_dovi_struc);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result_calc, container, false);

        //ссылка как заполнять ExpandableListView http://developer.alexanderklimov.ru/android/views/expandablelistview.php

        // Находим наш list
        elvView = (ExpandableListView) v.findViewById(R.id.elvDenDov);

        UpdateTX();
        //изменение параметров вызывает изменение итогов
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
            Map<String, String> map;

            // коллекция для групп
            ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
            // заполняем коллекцию групп из массива с названиями групп
            for (String group : mGroupsDenDov) {
                // заполняем список атрибутов для каждой группы
                map = new HashMap<>();
                map.put("groupName", group); // время года
                groupDataList.add(map);
            }
            // список атрибутов групп для чтения
            String groupFrom[] = new String[] { "groupName" };
            // список ID view-элементов, в которые будет помещены атрибуты групп
            int groupTo[] = new int[] { android.R.id.text1 };

            // создаем общую коллекцию для коллекций элементов
            ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

            // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

            // создаем коллекцию элементов для первой группы
            ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
            // заполняем список атрибутов для каждого элемента
            for (String month : mDenDovStruct) {
                map = new HashMap<>();
                map.put("structName", month); // название месяца
                сhildDataItemList.add(map);
            }
            // добавляем в коллекцию коллекций
            сhildDataList.add(сhildDataItemList);


            // список атрибутов элементов для чтения
            String childFrom[] = new String[] { "structName" };
            // список ID view-элементов, в которые будет помещены атрибуты
            // элементов
            int childTo[] = new int[] { android.R.id.text1 };

            SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(getActivity(), groupDataList,
                    android.R.layout.simple_expandable_list_item_1, groupFrom,
                    groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                    childFrom, childTo);

            elvView.setAdapter(adapter);


            /*txRasmMinPensii.setText(String.format("%.2f",pens.getpMinPens()));
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
            txNadb.setText(pens.getpNadbavki_string());*/
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
