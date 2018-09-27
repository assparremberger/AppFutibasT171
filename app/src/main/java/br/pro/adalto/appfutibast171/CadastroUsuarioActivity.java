package br.pro.adalto.appfutibast171;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etConfirma;
    private Button btnSalvar;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String erro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        etNome = (EditText) findViewById(R.id.etNomeUsuario);
        etEmail = (EditText) findViewById(R.id.etEmailUsuario);
        etSenha = (EditText) findViewById(R.id.etSenhaUsuario);
        etConfirma = (EditText) findViewById(R.id.etConfirmaSenha);
        btnSalvar = (Button) findViewById(R.id.btnSalvarUsuario);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

    }

    private void cadastrar(){
        final String nome = etNome.getText().toString();
        final String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        String confirma = etConfirma.getText().toString();



        if (nome.isEmpty()){
            erro = "Campo nome é obrigatório!";
        }else {
            if ( email.isEmpty() ){
                erro = "Campo e-mail é obrigatório!";
            }else {
                if ( senha.isEmpty() || !senha.equals(confirma) ){
                    erro = "Campos de senha são obrigatórios e " +
                            "não podem ter valores diferentes";
                }else {
                    final FirebaseAuth autenticacao = FirebaseAuth.getInstance();
                    autenticacao.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if ( task.isSuccessful() ) {

                                        database = FirebaseDatabase.getInstance();
                                        String idUsuario = autenticacao.getCurrentUser().getUid();
                                        reference = database.getReference("usuarios").child(idUsuario);
                                        reference.child("nome").setValue(nome);
                                        reference.child("email").setValue(email);

                                        finish();

                                    }else{
                                        erro = "Não foi possível criar o usuário!";
                                    }

                                }
                            });

                }
            }
        }

        if ( ! erro.isEmpty() ){
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
        }

    }

}
