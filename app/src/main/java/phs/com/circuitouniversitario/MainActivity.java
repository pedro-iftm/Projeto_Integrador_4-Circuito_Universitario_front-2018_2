package phs.com.circuitouniversitario;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener, CallbackAdapter {

    public static final String BASE_URL = "http://10.0.2.2:7000/";
    private String data_evento, descricao_evento, info_event;
    private String endereco_maps, sufixo_maps, endereco_evento;
    private List<Evento> eventos;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private Button btnAbreGaleria, btnCadastar;
    private Adapter adapter;
    EditText et_cad_event_nome;
    EditText et_cad_event_data;
    EditText et_cad_event_descricao;
    EditText et_cad_event_local;
    Evento evento;
    View itemView;
    private List<Evento> eventoResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaProcessos();
        iniciaMenuLateral();
        iniciaCard();
    }

    public void iniciaProcessos() {
        et_cad_event_nome = findViewById(R.id.et_cad_event_nome);
        et_cad_event_descricao = findViewById(R.id.et_cad_event_descricao);
        et_cad_event_local = findViewById(R.id.et_cad_event_local);
        et_cad_event_data = findViewById(R.id.et_cad_event_data);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_list);
    }

    public void iniciaMenuLateral() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void iniciaCard() {
        getEventos();
        //Adapter adapter = new Adapter(eventos, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Menu Lateral
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_event) {
            setContentView(R.layout.activity_main);
            iniciaProcessos();
            iniciaMenuLateral();
            iniciaCard();
        } else if (id == R.id.menu_cad_event) {
            setContentView(R.layout.activity_nav_event);
            iniciaProcessos();
            iniciaMenuLateral();

            //botão chamar galeria
            btnAbreGaleria = findViewById(R.id.btn_cad_event_galeria);
            btnAbreGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                    //upload da imagem
                }

            });

            //botao cadastrar
            btnCadastar = findViewById(R.id.btn_cad_event_cadastrar);
            btnCadastar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postEvento(new Evento(null, et_cad_event_nome.getText().toString(),
                            et_cad_event_local.getText().toString(),
                            et_cad_event_data.getText().toString(),
                            et_cad_event_descricao.getText().toString()));
                    limpaTelaCad();
                }

            });


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deleteEvento(Integer id) {
        Retrofit retrofit = buildRetrofit();
        Request request = retrofit.create(Request.class);
        Call<Evento> call = request.deleteEvento(id);

        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {

                    Toast.makeText(MainActivity.this, "Evento deletado com sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    private Retrofit buildRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void postEvento(Evento evento) {
        Retrofit retrofit = buildRetrofit();

        Request request = retrofit.create(Request.class);
        Call<Void> call = request.postEvento(evento);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Evento cadastrado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void limpaTelaCad() {
        et_cad_event_nome.setText("");
        et_cad_event_descricao.setText("");
        et_cad_event_local.setText("");
        et_cad_event_data.setText("");
    }


    //Botão Descrição
    public void onClick(View v, final int position) {

        switch (v.getId()) {
            case R.id.btn_descricao:
                AlertDialog dialog_info_envent;
                AlertDialog.Builder dialog_builder = new AlertDialog.Builder(this);


                dialog_builder.setMessage("Descrição: " +
                        eventoResponse.get(position).getDescricao() +
                                "\n\n" +
                                "Endereço: " +
                        eventoResponse.get(position).getEndereco()+
                                "\n" +
                                "Data: " +
                        eventoResponse.get(position).getData());
                dialog_builder.setPositiveButton("VER NO MAPA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        sufixo_maps = "geo:0,0?q=";
                        endereco_maps = sufixo_maps + eventoResponse.get(position).getEndereco();
                        chamaMaps(endereco_maps);
                    }
                });
                dialog_builder.setNegativeButton("EXCLUIR EVENTO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                            deleteEvento(eventoResponse.get(position).getIdEvento());
                        //Toast.makeText(MainActivity.this, "Excluído", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog_info_envent = dialog_builder.create();
                dialog_info_envent.show();
                break;
        }
    }

    //Google Maps
    private void chamaMaps(String uri_maps) {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri_maps));
            startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(this, "Verifique se o Google Maps está instalado em seu dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventos() {
        Retrofit retrofit = buildRetrofit();
        Request request = retrofit.create(Request.class);
        Call<List<Evento>> call = request.getEventos();
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {

                eventoResponse = response.body();
                Adapter.ViewHolder teste;
                /*data_evento = teste.getData();
                endereco_evento = teste.getEndereco();
                descricao_evento = teste.getDescricao();*/

               /* data_evento = eventoResponse.get(0).getData();
                endereco_evento = eventoResponse.get(0).getEndereco();
                descricao_evento = eventoResponse.get(0).getDescricao();
                info_event = "Descrição: " +
                        eventoResponse.get(descricao_evento +
                        "\n\n" +
                        "Endereço: " +
                        endereco_evento +
                        "\n" +
                        "Data: " +
                        data_evento;*/
                adapter = new Adapter(eventoResponse, MainActivity.this, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro nas informações", Toast.LENGTH_SHORT).show();
                Log.d("Erro", t.getMessage());
            }
        });
    }

    @Override
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}



