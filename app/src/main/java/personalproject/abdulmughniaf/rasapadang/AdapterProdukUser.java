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

public class AdapterProdukUser extends RecyclerView.Adapter<AdapterProdukUser.ViewHolder> {
    private ArrayList<ModelProduk> produk;
    private Context context;

    public AdapterProdukUser(Context context, ArrayList<ModelProduk> produks) {
        this.produk = produks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //mengatur content_list_produk_editor.xmltor.xml sebagai template
        // item dari list produk yang akan ditampilkan
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_produk_user, null);
        return new AdapterProdukUser.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //untuk menampilkan data produk pada content_list_produk_editor_editor.xml
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
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //mendeklarasikan komponen pada layout content_list_produk_editor_editor.xml
        TextView namaProduk,stokProduk,hargaJual;
        ViewHolder(View itemView){
            super(itemView);
            namaProduk = itemView.findViewById(R.id.txtNamaProduk);
            stokProduk = itemView.findViewById(R.id.txtStokProduk);
            hargaJual = itemView.findViewById(R.id.txtHargaJual);
        }
    }
}
