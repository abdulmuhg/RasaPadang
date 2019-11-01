package personalproject.abdulmughniaf.rasapadang;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLaba extends RecyclerView.Adapter<AdapterLaba.ViewHolder> {
    private ArrayList<ModelLaba> produk;
    private Context context;

    public AdapterLaba(Context context, ArrayList<ModelLaba> produks) {
        this.produk = produks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //mengatur content_list_produk_editor.xmltor.xml sebagai template
        // item dari list produk yang akan ditampilkan
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_laba, null);
        return new AdapterLaba.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //untuk menampilkan data produk pada content_list_produk_editor_editor.xml
        holder.tvcodeTrans.setText(produk.get(position).getKode_trans() + "");
        holder.tvLaba.setText(produk.get(position).getLaba() + "");
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvcodeTrans, tvLaba;
        ViewHolder(View itemView){
            super(itemView);
            tvcodeTrans = itemView.findViewById(R.id.tvcodeTrans);
            tvLaba = itemView.findViewById(R.id.tvLaba);
        }
    }
}
