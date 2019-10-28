package personalproject.abdulmughniaf.rasapadang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailProdukActivity extends AppCompatActivity {
    private DBHelper db;

    private Button btnHapus;
    private Button btnSimpan;
    private EditText editNamaProduk;
    private EditText edithargaPokok;
    private EditText editHargaJual;
    private EditText editStok;
    private EditText editKodeProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        db = new DBHelper(this);

        inisialisasi();
        load_function();
        tampilkan_data();
    }

    private void tampilkan_data() {
        //mendapatkan data kode_produk yang dikirimkan
        //saat mengklik list produk
        Intent intent = getIntent();
        int kode_produk = intent.getIntExtra("kode_produk", 0);
        String nama_produk = intent.getStringExtra("nama_produk");
        Double harga_pokok = intent.getDoubleExtra("harga_pokok", 0);
        Double harga_jual = intent.getDoubleExtra("harga_jual", 0);
        int stok = intent.getIntExtra("stok", 0);

        editKodeProduk.setText(String.valueOf(kode_produk));
        editNamaProduk.setText(nama_produk);
        edithargaPokok.setText(String.valueOf(harga_pokok));
        editHargaJual.setText(String.valueOf(harga_jual));
        editStok.setText(String.valueOf(stok));
    }

    private void load_function() {
        //fungsi ketika klik Button Hapus
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "DELETE FROM produk WHERE kode_produk = " + editKodeProduk.getText();
                db.customQuery(query);
                //membuka halaman awal setelah query di proses
                startActivity(new Intent(DetailProdukActivity.this, MainActivity.class));
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan fungsi prosesSimpan
                prosesSimpan();
            }
        });
    }

    private void prosesSimpan() {
        //mengecek apakah form masih ada yang kosong
        if (editKodeProduk.getText().toString().isEmpty() ||
                editNamaProduk.getText().toString().isEmpty() ||
                edithargaPokok.getText().toString().isEmpty() ||
                editHargaJual.getText().toString().isEmpty() ||
                editStok.getText().toString().isEmpty()) {
            //Beritahukan jika ada form yang kosong dengan TOASTER
            Toast.makeText(DetailProdukActivity.this, "Data masih belum lengkap.", Toast.LENGTH_SHORT).show();
        } else {
            //lakukan proses update jika data sudah lengkap
            String query = "UPDATE produk SET " +
                    "nama_produk = '" + editNamaProduk.getText() + "'," +
                    "harga_pokok = " + edithargaPokok.getText() + "," +
                    "harga_jual = " + editHargaJual.getText() + "," +
                    "stok = " + editStok.getText() + " " +
                    "WHERE kode_produk = " + editKodeProduk.getText();

            db.customQuery(query);
            Toast.makeText(DetailProdukActivity.this, "Data berhasil diubah.", Toast.LENGTH_SHORT).show();
        }
    }

    private void inisialisasi() {
        btnHapus = findViewById(R.id.btnHapus);
        btnSimpan = findViewById(R.id.btnUbah);

        editKodeProduk = findViewById(R.id.detailKodeProduk);
        editNamaProduk = findViewById(R.id.detailNamaProduk);
        edithargaPokok = findViewById(R.id.detailHargaPokok);
        editHargaJual = findViewById(R.id.detailHargaJual);
        editStok = findViewById(R.id.detailStokProduk);
    }
}
