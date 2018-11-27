package phs.com.circuitouniversitario;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends  Activity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button btn_descricao_view;
    private String data_evento, distancia_evento, descricao_evento, info_event;
    private List<evento> mlist;
    private RecyclerView recyclerView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaProcessos();
        iniciaMenuLateral();
        iniciaBotaoFlutuante();
        iniciaCard();
    }

    public void iniciaProcessos() {
        configuraInfoEnvent();
        btn_descricao_view = findViewById(R.id.btn_descricao);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.rv_list);
    }

    public void iniciaBotaoFlutuante () {
        FloatingActionButton fb_filter = findViewById(R.id.fb_filter);
        fb_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog_filter;
                AlertDialog.Builder dialog_builder = new AlertDialog.Builder(MainActivity.this);

                dialog_builder.setMessage("Teste Botão Flutuante");
                dialog_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });

                dialog_filter = dialog_builder.create();
                dialog_filter.show();
            }
        });
    }

    public void configuraInfoEnvent() {
        data_evento = "02/08/18";
        distancia_evento = "2km";
        descricao_evento = "top";

        info_event =    "Descrição: " +
                        descricao_evento +
                        "\n\n" +
                        "Distância: " +
                        distancia_evento +
                        "\n" +
                        "Data: " +
                        data_evento;
    }

    public void iniciaMenuLateral () {
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void iniciaCard () {
        preencheCard();
        Adapter adapter = new Adapter(this, mlist, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void preencheCard () {
        mlist = new ArrayList<>();
        mlist.add(new evento("Vem pra UFU", R.drawable.festa1));
        mlist.add(new evento("Vem pra IFTM", R.drawable.festa2));
        mlist.add(new evento("Happy Hour", R.drawable.festa3));
        mlist.add(new evento("CC", R.drawable.festa4));
        mlist.add(new evento("Baladinha", R.drawable.festa5));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_event) {
            setContentView(R.layout.activity_main);
            iniciaProcessos();
            iniciaMenuLateral();
            iniciaCard();
        } else if (id == R.id.menu_user) {
            setContentView(R.layout.activity_nav_user);
            iniciaProcessos();
            iniciaMenuLateral();
        } else if (id == R.id.menu_cad_event) {
            setContentView(R.layout.activity_nav_event);
            iniciaProcessos();
            iniciaMenuLateral();
        };

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_descricao:
                AlertDialog dialog_info_envent;
                AlertDialog.Builder dialog_builder = new AlertDialog.Builder(this);

                dialog_builder.setMessage(info_event);
                dialog_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });

                dialog_info_envent = dialog_builder.create();
                dialog_info_envent.show();
                break;
        }
    }
}

