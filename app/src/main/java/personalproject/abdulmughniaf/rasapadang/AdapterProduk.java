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

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.ViewHolder> {
    private ArrayList<ModelProduk> produk;
    private Context context;

    public AdapterProduk(Context context, ArrayList<ModelProduk> produks) {
        this.produk = produks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //mengatur content_list_produk.xml sebagai template
        // item dari list produk yang akan ditampilkan
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_produk, null);
        return new AdapterProduk.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //untuk menampilkan data produk pada content_list_produk.xml
        holder.namaProduk.setText(produk.get(position).getNama_produk());
        holder.stokProduk.setText(produk.get(position).getStok()+" Items");
        holder.hargaJual.setText("Rp "+produk.get(position).getHarga_jual());

        //fungsi saat mengklik item list
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuka halaman/Activity Activity_DetailProduk
                //dan mengirim sebuah nilai dengan kata kunci "kode_produk,dkk"
                //dimana isinya merupakai kode_produk
                Intent intent = new Intent(context,DetailProdukActivity.class);
                intent.putExtra("kode_produk",produk.get(position).getKode_produk());
                intent.putExtra("nama_produk",produk.get(position).getNama_produk());
                intent.putExtra("harga_pokok",produk.get(position).getHarga_pokok());
                intent.putExtra("harga_jual",produk.get(position).getHarga_jual());
                intent.putExtra("stok",produk.get(position).getStok());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan komponen pada layout content_list_produk.xml
        TextView namaProduk,stokProduk,hargaJual;
        ViewHolder(View itemView){
            super(itemView);
            namaProduk = itemView.findViewById(R.id.txtNamaProduk);
            stokProduk = itemView.findViewById(R.id.txtStokProduk);
            hargaJual = itemView.findViewById(R.id.txtHargaJual);
        }
    }
}
