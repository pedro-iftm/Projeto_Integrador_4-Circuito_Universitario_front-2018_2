package phs.com.circuitouniversitario;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Evento> listaEventos;
    View.OnClickListener onClickListener;
    private Button btn_descricao;
    private CallbackAdapter callbackAdapter;

    public Adapter(List<Evento> listaEventos, View.OnClickListener onClickListener, CallbackAdapter callbackAdapter) {
        this.listaEventos = listaEventos;
        this.onClickListener = onClickListener;
        this.callbackAdapter = callbackAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_nome_evento_adapter.setText(listaEventos.get(position).getNome());
        //holder.iv_card_adapter.setImageResource(listaEventos.get(position).getImagemEvento());
        holder.iv_card_adapter.setImageResource(R.drawable.festa1);
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nome_evento_adapter;
        ImageView iv_card_adapter;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_nome_evento_adapter = itemView.findViewById(R.id.tv_nome_evento);
            iv_card_adapter = itemView.findViewById(R.id.iv_card);

            //botao descricao
            btn_descricao = itemView.findViewById(R.id.btn_descricao);
            btn_descricao.setOnClickListener(onClickListener);
            callbackAdapter.setEvento(listaEventos.get(getAdapterPosition() + 1));
        }


        /*public String getEndereco () {
            String endereco_evento = listaEventos.get(getAdapterPosition()).getEndereco();
            return endereco_evento;
        }
        public String getData() {
            String data_evento = listaEventos.get(getAdapterPosition()).getData();
            return data_evento;
        }
        public String getDescricao () {
            String descricao_evento = listaEventos.get(getAdapterPosition()).getDescricao();
            return descricao_evento;
        }*/
    }
}
