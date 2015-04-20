package be.howest.nmct;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


public class ChangeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private float currentRateBitcoinInEuro;
    static final String CURRENT_RATE = "be.howest.nmct.CURRENT_RATE";

    private EditText TxtBedragEuroInvoer;
    private EditText TxtBedragBitcoinInvoer;
    private TextView TxtBitcoinKoers;


    private Button btnBitcoin;
    private Button btnEuro;
    private Button btnKoersWijzigen;

    //private OnFragmentInteractionListener mListener;
    private OnFragmentChangeListener onFragmentChangeListener;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeFragment newInstance(String param1, String param2) {
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentChangeListener {
        public void onSetKoers(float koers);
    }


    public ChangeFragment() {
        // Required empty public constructor
    }

    public static ChangeFragment newInstance(float koers) {
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();
        args.putFloat(CURRENT_RATE, koers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences settings = getActivity().getSharedPreferences(Bitcoin.PREFS_CURRENT_RATE, 0);
        currentRateBitcoinInEuro = settings.getFloat("rate", 0.0f);

        /*
        if (getArguments() != null) {

                currentRateBitcoinInEuro = getArguments().getFloat(BITCOIN_RATE);

        }

        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_change, container, false);

        this.TxtBedragBitcoinInvoer = (EditText) v.findViewById(R.id.TxtBedragBitcoinInvoer);
        this.TxtBedragEuroInvoer = (EditText) v.findViewById(R.id.TxtBedragEuroInvoer);
        this.TxtBitcoinKoers = (TextView)v.findViewById(R.id.TxtBitcoinKoers);
        this.btnBitcoin = (Button) v.findViewById(R.id.btnBitcoin);
        this.btnEuro = (Button) v.findViewById(R.id.btnEuro);
        this.btnKoersWijzigen = (Button) v.findViewById(R.id.btnKoersWijzigen);

        toonWisselkoers();

        this.btnKoersWijzigen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                VerstuurDataBitcoinFragment();
            }
        });

        this.btnEuro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeToEuro();
            }
        });

        this.btnBitcoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                changeToBitcoin();
            }
        });
        return v;
    }

    public void VerstuurDataBitcoinFragment(){
        float koers = currentRateBitcoinInEuro;
        if (onFragmentChangeListener != null){
            onFragmentChangeListener.onSetKoers(koers);
        }

    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    public void changeToBitcoin(){
    float Euro =  Float.parseFloat(this.TxtBedragEuroInvoer.getText().toString());
    String Result = String.valueOf(Euro / currentRateBitcoinInEuro);

     TxtBedragBitcoinInvoer.setText(Result);

    }

    public void changeToEuro(){
        float Bitcoin =  Float.parseFloat(this.TxtBedragBitcoinInvoer.getText().toString());
        String Result = String.valueOf(Bitcoin * currentRateBitcoinInEuro);

        TxtBedragEuroInvoer.setText(Result);
    }


    private void toonWisselkoers(){
               if(Float.toString(currentRateBitcoinInEuro) != null){
                      TxtBitcoinKoers.setText("1 bitcoin = " + currentRateBitcoinInEuro);
                    }
          }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onFragmentChangeListener = (OnFragmentChangeListener) activity;
        } catch (ClassCastException e) {

        }
    }

    public void SetKoers(float currentRateBitcoinInEuro) {
            this.currentRateBitcoinInEuro = currentRateBitcoinInEuro;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentChangeListener = null;
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
    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
*/
}
