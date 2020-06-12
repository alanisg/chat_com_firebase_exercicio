package br.com.gabriel.chat_com_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class Chat_Activity extends AppCompatActivity {

    private RecyclerView mensagensRecyclerView;
    private ChatAdapter adapter;
    private List<Mensagem> mensagens;
    private EditText mensagemEditText;
    private FirebaseUser fireUser;
    private CollectionReference mensagensReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        mensagensRecyclerView = findViewById(R.id.mensagensRecyclerView);
        mensagens = new ArrayList<>();
        adapter = new ChatAdapter(mensagens, this);
        mensagensRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mensagensRecyclerView.setLayoutManager(linearLayoutManager);
        mensagemEditText = findViewById(R.id.mensagemEditText);

    }
    private void getRemoteMsgs(){
        mensagensReference.addSnapshotListener((snapshots, firebaseExcrption) ->{
            mensagens.clear();
            for(DocumentSnapshot doc: snapshots.getDocuments()){
                Mensagem msg = doc.toObject(Mensagem.class);
                mensagens.add(msg);
            }
            Collections.sort(mensagens);
            adapter.notifyDataSetChanged();
        });
    }

    private void setupFirebase(){
        fireUser =  FirebaseAuth.getInstance().getCurrentUser();
        mensagensReference = FirebaseFirestore.getInstance().collection("mensagens");
        getRemoteMsgs();
    }

    protected void onStart(){
        super.onStart();
        setupFirebase();
    }

    public void enviarMensagem (View v){
        String texto= mensagemEditText.getText().toString();
        Mensagem mensagem = new Mensagem(fireUser.getEmail(), new Date(), texto);
        mensagensReference.add(mensagem);
        mensagemEditText.getText().clear();

    }

    public void esconderTeclado (View v){
        InputMethodManager imm = ( InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}

class ChatViewHolder extends RecyclerView.ViewHolder{
    TextView dataNomeTextView;
    TextView mensagemTextView;

        ChatViewHolder(View raiz){
            super(raiz);
            this.dataNomeTextView = raiz.findViewById(R.id.dataNomeTextView);
            this.mensagemTextView = raiz.findViewById(R.id.mensagemTextView);
        }
}

class ChatAdapter extends RecyclerView.Adapter <ChatViewHolder>{
    private List<Mensagem> mensagens;
    private Context context;
    ChatAdapter (List<Mensagem> mensagens, Context context){

    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Mensagem m = mensagens.get(position);
        holder.dataNomeTextView.setText(context.getString(R.string.data_nome, DateHelper.format(m.getDate()), m.getUsuario()));
        holder.mensagemTextView.setText(m.getTexto());



    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.list_item, parent,false);
        return new ChatViewHolder(raiz);
    }

    @Override
    public int getItemCount() {
        return this.mensagens.size();
    }
}


