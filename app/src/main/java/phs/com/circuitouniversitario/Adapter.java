package phs.com.circuitouniversitario;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<evento> mData;
    View.OnClickListener onClickListener;
    private Button btn_descricao;

    public Adapter(Context mContext, List<evento> mData, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onClickListener = onClickListener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.tv_nome_evento_adapter.setText(mData.get(position).getNome());
        holder.iv_card_adapter.setImageResource(mData.get(position).getBackground());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nome_evento_adapter;
        ImageView iv_card_adapter;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_nome_evento_adapter = itemView.findViewById(R.id.tv_nome_evento);
            iv_card_adapter = itemView.findViewById(R.id.iv_card);

            btn_descricao = itemView.findViewById(R.id.btn_descricao);
            btn_descricao.setOnClickListener(onClickListener);
        }
    }
}
