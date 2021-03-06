package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button valider;
    TextView debut;
    Spinner choix;
    TextView record;
    int nbrecord = 0;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debut = findViewById(R.id.debut); // variable qui affiche la partie va commencer
        record = findViewById(R.id.record);
        setRecord(); // affiche le record actuel
        choix = (Spinner) findViewById(R.id.choix); // pour choisir la langue
        ArrayAdapter<CharSequence> monAdapter = ArrayAdapter.createFromResource(this, R.array.tab_choix, android.R.layout.simple_spinner_item);
        monAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choix.setAdapter(monAdapter);
        choix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // rien n'est fait ici
            }
        });

        valider = (Button) findViewById(R.id.valider); // bouton pour lancer la partie
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debut.setText("La partie va commencer");
                visualiser_la_suite();

            }
        });

    }


    private void visualiser_la_suite() {
        String test=text;
        if (test.equals("Francais")){
            Intent intent = new Intent(this, francais.class);
            startActivity(intent);

            System.out.println(text);

        }
        if (test.equals("Anglais")) {
            Intent intent = new Intent(this, anglais.class);
            startActivity(intent);
        }    }

    public void setRecord() {
        SharedPreferences sp = getSharedPreferences("your_prefs", anglais.MODE_PRIVATE);
        nbrecord = sp.getInt("nbrecord", 0);
        record.setText("Votre record actuel est : " + nbrecord);
    }

}
