package be.howest.nmct;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BitcoinRateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BitcoinRateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BitcoinRateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private float rate1BitcoinInEuros;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText TxtBitCoinKoersIngave;
    private Button btnWijzigen;

    private OnFragmentBitcoinListener onFragmentBitcoinListener;

    static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";

    // TODO: Rename and change types and number of parameters
    public static BitcoinRateFragment newInstance(float bitcoinrate) {
        BitcoinRateFragment fragment = new BitcoinRateFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE,bitcoinrate);
        fragment.setArguments(args);
        return fragment;
    }

    public BitcoinRateFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentBitcoinListener {
        public void onNewKoers(float rate1BitcoinInEuros);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences(Bitcoin.PREFS_CURRENT_RATE, 0);
        rate1BitcoinInEuros = settings.getFloat("rate", 0.0f);

        /*
        if (getArguments() != null) {
        rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE);
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        this.TxtBitCoinKoersIngave = (EditText) v.findViewById(R.id.TxtBitcoinKoersIngave);
        this.btnWijzigen = (Button) v.findViewById(R.id.btnWijzigen);

        this.btnWijzigen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WisselkoersAanpassen();
            }
        });

        saveNieuweWisselkoers();

        return v;
    }

    public void WisselkoersAanpassen(){
        float Ingave =  Float.parseFloat(this.TxtBitCoinKoersIngave.getText().toString());
        rate1BitcoinInEuros = Ingave;
        if (onFragmentBitcoinListener != null)
            onFragmentBitcoinListener.onNewKoers(rate1BitcoinInEuros);

    }

    private void saveNieuweWisselkoers(){
             rate1BitcoinInEuros = Float.parseFloat(TxtBitCoinKoersIngave.getText().toString());
           }


    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onFragmentBitcoinListener = (OnFragmentBitcoinListener) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

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
        public void onFragmentInteraction(Uri uri);
    }

}
