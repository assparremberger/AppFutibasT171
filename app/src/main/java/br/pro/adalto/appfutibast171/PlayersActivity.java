package br.pro.adalto.appfutibast171;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import br.pro.adalto.appfutibast171.model.Jogador;

public class PlayersActivity extends AppCompatActivity {

    private ListView lvPlayers;
    private List<Jogador> jogadores;
    private ArrayAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Query queryRef;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jogadores = new ArrayList<>();
        lvPlayers = (ListView) findViewById(R.id.lvPlayers);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, jogadores );
        lvPlayers.setAdapter( adapter );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlayersActivity.this,
                        FormularioPlayerActivity.class);
                startActivity( i );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Sair");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.toString().equals("Sair") ){
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        queryRef = reference.child("jogadores").orderByChild("nome");

        jogadores.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Jogador j = new Jogador();
                j.setId( dataSnapshot.getKey() );
                j.setNome( dataSnapshot.child("nome").getValue(String.class) );
                j.setIdade( dataSnapshot.child("idade").getValue(Integer.class));
                jogadores.add( j );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        queryRef.addChildEventListener( childEventListener );
    }


    @Override
    protected void onStop() {
        super.onStop();
        queryRef.removeEventListener( childEventListener );
    }
}
