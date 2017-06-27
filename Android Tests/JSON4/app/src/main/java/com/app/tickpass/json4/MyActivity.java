package com.app.tickpass.json4;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.app.tickpass.json4.utilidades.JSONParser;
import com.app.tickpass.json4.Objetos.Cartel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity
{

    //TEXTVIEWS (SOLO PARA ESTA APP)
    TextView NUMERO;
    TextView UNOUNO;
    TextView DOSUNO;
    TextView UNODOS;
    TextView DOSDOS;

    //campos
    int numero_TOTAL = 0;
    String numero_total;
    String unounoS;
    String dosunoS;
    String unodosS;
    String dosdosS;

    ArrayList Carteles = new ArrayList();
    String prueba;


    //---------------URL DE ARRAYS DE JSON-----------------
    //JSON urls
    private static String urlnumero = "http://85.53.180.73/XYZnumero.php";
    private static String urleventos = "http://85.53.180.73/XYZeventos.php";

    //----------------JSON NOMBRE DE NODOS-----------------
    //JSON urlnumero
    private static final String TAG_CANTIDAD = "Cantidad";
    JSONArray cantidad = null;
    //JSON urleventos
    private static final String TAG_CARTELES = "Carteles";
    JSONArray carteles = null;



    //-------------------JSON ELEMENTOS--------------------
    //JSON urlnumero
    private static final String TAG_NUMERO = "numero";
    //JSON urleventos
    private static final String TAG_PRIMERA = "caract1";
    private static final String TAG_SEGUNDA = "caract2";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setTitle("JSON porfi, ya");

        NUMERO = (TextView) findViewById(R.id.NUMERO);
        UNOUNO = (TextView) findViewById(R.id.UNOUNO);
        DOSUNO = (TextView) findViewById(R.id.DOSUNO);
        UNODOS = (TextView) findViewById(R.id.DOSUNO);
        DOSDOS = (TextView) findViewById(R.id.DOSDOS);

        //____CHECK CONNECTION____
        if(isConnected())
        {
            Toast.makeText(getBaseContext(), "Conectado", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), "No conectado", Toast.LENGTH_LONG).show();
        }

        //Aquí hacemos que el código de parseo de JSON se ejecute para sacar el número de eventos
        new JSONParse().execute();
        //a estas alturas, en numero_TOTAL tengo el número de evento
        new JSONParseDOS().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyActivity.this);
            pDialog.setMessage("Obteniendo datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(urlnumero);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                //JSON numero
                cantidad = json.getJSONArray(TAG_CANTIDAD);
                JSONObject can = cantidad.getJSONObject(0);
                numero_total = can.getString(TAG_NUMERO);
                numero_TOTAL = Integer.parseInt(numero_total);
                NUMERO.setText(numero_total);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
    //-----------------------OJOOOOO-------------------------
    private class JSONParseDOS extends AsyncTask<String, String, JSONObject>
    {

        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyActivity.this);
            pDialog.setMessage("Obteniendo datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(urleventos);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            try
            {

                for (int i=0; i<numero_TOTAL; i++)
                {
                    Cartel temp = new Cartel();

                    carteles = json.getJSONArray(TAG_CARTELES);
                    JSONObject eventotemporal = carteles.getJSONObject(i);

                    temp.setValorcampo1(eventotemporal.getString(TAG_PRIMERA));
                    temp.setValorcampo2(eventotemporal.getString(TAG_SEGUNDA));

                    Carteles.add(i, temp);
                }

                Cartel cartel = (Cartel) Carteles.get(0);
                Cartel cartel2 = (Cartel) Carteles.get(1);

                UNOUNO.setText(cartel.getValorcampo1());
                DOSUNO.setText(cartel.getValorcampo2());
                UNODOS.setText(cartel2.getValorcampo1());
                DOSDOS.setText(cartel2.getValorcampo2());

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
