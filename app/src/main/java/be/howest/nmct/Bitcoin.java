package be.howest.nmct;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Bitcoin extends Activity implements ChangeFragment.OnFragmentChangeListener, BitcoinRateFragment.OnFragmentBitcoinListener {


   private float recenteWisselKoers = 100.0f;

    public static final String PREFS_CURRENT_RATE = "MyPrefsFile";


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_CURRENT_RATE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("rate", recenteWisselKoers);


        editor.commit();
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);
        if (savedInstanceState == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            ChangeFragment fragment1 = ChangeFragment.newInstance(recenteWisselKoers);

            fragmentTransaction.add(R.id.container, fragment1, "ChangeFragment");
            fragmentTransaction.commit();

            setTitle("ChangeFragment");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bitcoin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showChangeFragment(float newKoers){
    FragmentManager fragmentManager = getFragmentManager();
       fragmentManager.popBackStack();
        ChangeFragment fragment1 = (ChangeFragment) getFragmentManager().findFragmentByTag("ChangeFragment");
        fragment1.SetKoers(newKoers);

        setTitle("ChangeFragment");
    }


    private void showBitcoinRateFragment(float newKoers){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BitcoinRateFragment fragment2 = BitcoinRateFragment.newInstance(newKoers);
        fragmentTransaction.replace(R.id.container, fragment2);

        //Add this transaction to the back stack. This means that the transaction will be remembered
        //after it is committed, and will reverse its operation when later popped off the stack.
        //name: An optional name for this back stack state, or null.
        fragmentTransaction.addToBackStack("showBitcoinRateFragment");
        fragmentTransaction.commit();

        setTitle("BitcoinRateFragment");

    }


    public void onNewKoers(float newKoers){
        this.recenteWisselKoers = newKoers;
        showChangeFragment(newKoers);
    }

    public void onSetKoers(float koers){
        this.recenteWisselKoers = koers;
        showBitcoinRateFragment(koers);
    }
}
