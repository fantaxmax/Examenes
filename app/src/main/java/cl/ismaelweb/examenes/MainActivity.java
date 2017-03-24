package cl.ismaelweb.examenes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private EditText prese;
    private EditText pond;
    private TextView salida;
    private Button calc;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5762271824675009/4589787808");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        prese = (EditText) findViewById(R.id.prese);
        pond = (EditText) findViewById(R.id.pond);
        salida = (TextView) findViewById(R.id.salida);
    }

    public void calcula(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        double presed = Double.parseDouble(prese.getText().toString());
        double pondd = Double.parseDouble(pond.getText().toString());
        if(presed>70|presed<10) salida.setText("Solo notas de 1.0 a 7.0");
        else {
            double prepond = presed * (pondd / 100);
            double ex = (40 - prepond) / ((100 - pondd) / 100);
            if (ex < 10)
                salida.setText("Si la asistencia no es obligatoria, no es necesario presentarte :) necesitas menos de un 1.0");
            else if (ex > 70)
                salida.setText("\"Houston, Tenemos un Problema\" necesitas mas de un 70 para aprobar, algo imposible por el momento :/");
            else
                salida.setText(String.format("Necesitaras obtener un %s para aprobar", round(ex / 10, 1)));
        }
        if(mInterstitialAd.isLoaded()) mInterstitialAd.show();
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
