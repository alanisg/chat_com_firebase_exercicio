package br.com.gabriel.chat_com_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriasActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
    }
    public void irParaChatCinema(View view){
        Intent intent = new Intent(this, Chat_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("chat","cinema");
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void irParaChatNovidades(View view){
        Intent intent = new Intent(this, Chat_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("chat","novidades");
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void irParaChatTecnologia(View view){
        Intent intent = new Intent(this, Chat_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("chat","tecnologia");
        intent.putExtras(extras);
        startActivity(intent);
    }
    public void irParaChatEconomia(View view){
        Intent intent = new Intent(this, Chat_Activity.class);
        Bundle extras = new Bundle();
        extras.putString("chat","economia");
        intent.putExtras(extras);
        startActivity(intent);
    }
}