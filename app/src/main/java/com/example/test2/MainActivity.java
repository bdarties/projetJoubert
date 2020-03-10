package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    Button valider;
    EditText debut;
    Spinner choix;
    EditText record;
    public int best = 0;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        debut = findViewById(R.id.debut); // variable qui affiche la partie va commencer
        record = findViewById(R.id.record);
        setRecord(best); // affiche le record actuel
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
                // on met ici ce qu’il faut faire lorsqu’aucun item n’est selectionne
                System.out.println("aucun item n’est selectionné");
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
        if (test == "Francais") {
            Intent intent = new Intent(this, francais.class);
            startActivity(intent);
            System.out.println(text);

        }
        if (test == "Anglais") {
            Intent intent = new Intent(this, anglais.class);
            startActivity(intent);
        } if(text != "Francais" && text != "Anglais") debut.setText("Selectionnez un item avant de commencer");
    }

    public void setRecord(int best) {
        record.setText("Votre record actuel est : " + best);
    }

}
