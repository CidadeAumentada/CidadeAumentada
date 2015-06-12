package com.example.adm.webservice_cidade_aumentada;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    TextView txt_id;
    TextView txt_titulo;
    TextView txt_long;
    TextView txt_lat;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_id = (TextView)findViewById(R.id.txt_id);
        txt_titulo = (TextView)findViewById(R.id.txt_titulo);
        txt_lat = (TextView)findViewById(R.id.txt_lat);
        txt_long = (TextView)findViewById(R.id.txt_long);
    }


  public void proximo (View v){

      new GET().execute("http://cidadeaumentada.esy.es/scriptcase/app/blank_ws_json/blank_ws_json.php?lon=-46&lat=-23&intervalo=5");
      index++;

  }
    private class GET extends AsyncTask<String,Void ,String > {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            InputStream content = null;
            String cota = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                // executa a requisição GET
                HttpResponse response = httpclient.execute(get);
                // obtém o conteúdo retornado pela requisição GET
                content = response.getEntity().getContent();
                // content está declarado no slide anterior!
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                StringBuilder str = new StringBuilder();
                // lê linha a linha a partir do InputStream
                String line = null;
                while((line = reader.readLine()) != null)
                    str.append(line + "\n");
                // retorno contém todo o conteúdo lido
                cota = str.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return cota;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                JSONArray j = new JSONArray(s);
                JSONObject json = new JSONObject();
                if(index >= j.length()){
                    index = 0;
                }
                json = j.getJSONObject(index);
                String str = null;
                str = json.getString("conteudo_titulo");
                txt_titulo.setText(str);

                str = json.getString("conteudo_id");
                txt_id.setText(str);

                str = json.getString("latitude");
                txt_lat.setText(str);

                str = json.getString("longitude");
                txt_long.setText(str);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*JSONObject o = null;
            try {
                o = new JSONObject(s);
                String cota_bovespa;
                String varia_bovespa;
                String cota_dolar;
                String varia_dolar;
                String cota_euro;
                String varia_euro;
                String data;

                cota_bovespa = (String)o.getJSONObject("bovespa").get("cotacao");
                txt_cota_bovespa.setText(cota_bovespa);
                varia_bovespa = (String)o.getJSONObject("bovespa").get("variacao");
                txt_varia_bovespa.setText(varia_bovespa);

                cota_dolar = (String)o.getJSONObject("dolar").get("cotacao");
                txt_cota_dolar.setText(cota_dolar);
                varia_dolar = (String)o.getJSONObject("dolar").get("variacao");
                txt_varia_dolar.setText(varia_dolar);

                cota_euro = (String)o.getJSONObject("euro").get("cotacao");
                txt_cota_euro.setText(cota_euro);
                varia_euro = (String)o.getJSONObject("euro").get("variacao");
                txt_varia_euro.setText(varia_euro);

                data = o.getString("atualizacao");
                txt_atual.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }




*/
        }
    }


}
