package personalproject.abdulmughniaf.rasapadang;


import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProdukUser extends RecyclerView.Adapter<AdapterProdukUser.ViewHolder> {
    private ArrayList<ModelProduk> produk;
    public ArrayList<ModelTransaksi> transaksi;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.namaProduk.setText(produk.get(position).getNama_produk());
        holder.hargaJual.setText("Rp "+produk.get(position).getHarga_jual());
        holder.jmlhItem.setText("0");



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
        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = holder.jmlhItem.getText().toString();
                int items = Integer.parseInt(item);
                items = items+1;

                holder.jmlhItem.setText(items+"");

                Intent intent = new Intent("custom-message");
                intent.putExtra("kode_produk", produk.get(position).getKode_produk());
                intent.putExtra("nama_produk",produk.get(position).getNama_produk());
                intent.putExtra("harga_produk",produk.get(position).getHarga_jual());
                intent.putExtra("harga_pokok_produk",produk.get(position).getHarga_pokok());
                intent.putExtra("jumlah_item", items);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = holder.jmlhItem.getText().toString();
                int items = Integer.parseInt(item);
                items = items-1;

                Intent intent = new Intent("custom-message");
                intent.putExtra("kode_produk", produk.get(position).getKode_produk());
                intent.putExtra("nama_produk",produk.get(position).getNama_produk());
                intent.putExtra("harga_produk",produk.get(position).getHarga_jual());
                intent.putExtra("harga_pokok_produk",produk.get(position).getHarga_pokok());
                if(items < 0){
                    holder.jmlhItem.setText(item+"");
                    intent.putExtra("jumlah_item", items);
                }else{
                    holder.jmlhItem.setText(items+"");
                    intent.putExtra("jumlah_item", items);
                }

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaProduk,hargaJual,jmlhItem;
        ImageButton addItem, removeItem;
        ViewHolder(View itemView){
            super(itemView);
            namaProduk = itemView.findViewById(R.id.tvUser_NamaProduk);
            hargaJual = itemView.findViewById(R.id.tvUser_HargaJual);
            addItem = itemView.findViewById(R.id.btnAdd);
            removeItem = itemView.findViewById(R.id.btnRemove);
            jmlhItem = itemView.findViewById(R.id.tvJmlItem);
        }
    }
}
