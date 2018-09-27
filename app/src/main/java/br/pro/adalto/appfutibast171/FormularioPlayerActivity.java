package br.pro.adalto.appfutibast171;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.pro.adalto.appfutibast171.model.Jogador;

public class FormularioPlayerActivity extends AppCompatActivity {

    private EditText etNome, etIdade;
    private Button btnSalvar;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_player);

        etNome = (EditText) findViewById(R.id.etNomePlayer);
        etIdade = (EditText) findViewById(R.id.etIdade);
        btnSalvar = (Button) findViewById(R.id.btnSalvarPlayer);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

    }

    private void salvar(){
        String nome = etNome.getText().toString();
        if ( ! nome.isEmpty() ){
            database = FirebaseDatabase.getInstance();
            reference = database.getReference();

            Jogador j = new Jogador();
            j.setNome( nome );

            String sIdade = etIdade.getText().toString();
            if ( sIdade.isEmpty() ){
                j.setIdade( 0 );
            }else {
                j.setIdade( Integer.valueOf( sIdade ) );
            }

            reference.child("jogadores").push().setValue( j );

            finish();


        }
    }

}



