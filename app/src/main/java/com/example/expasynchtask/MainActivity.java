package com.example.expasynchtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt1;
    ProgressBar PB;
    NumberPicker NP;
    Button btnAppliquer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PB=(ProgressBar) findViewById(R.id.PB) ;
        NP=(NumberPicker) findViewById(R.id.NP);
        txt1=findViewById(R.id.txt);
        btnAppliquer=(Button)findViewById(R.id.btnAppliquer) ;
        NP.setMinValue(1);
        NP.setMaxValue(20);
        btnAppliquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB.setMax(NP.getValue());
                MyAsynTask MA = new MyAsynTask();
                MA.execute(NP.getValue());
            }
        });
    }
    public class MyAsynTask
            extends AsyncTask<Integer,Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PB.setProgress(0);
            txt1.setText("Début");
        }
        @Override
        protected void onPostExecute(String ch) {
            super.onPostExecute(ch);
            txt1.setText(ch);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //valeur récupérée depuis publishProgress
            //ici elle représente l'avancement
            PB.setProgress(values[0]);
            txt1.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... integers) {
            //un traitement de fond
            for (int i=1;i<=integers[0];i++)
            {
                try {
                    //Simuler le retard
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //méthode qui existe déjà et qu'on peut appeler au besoin
                publishProgress(i);


            }
            return "Fin de tâche de fond";

        }
    }
}