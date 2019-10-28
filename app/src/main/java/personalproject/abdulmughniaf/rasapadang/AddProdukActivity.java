package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProdukActivity extends AppCompatActivity {
    private DBHelper db;

    private Button btnBersihkan;
    private Button btnSimpan;
    private Button btnLihatData;
    private Button btnTransaksi;
    private EditText editNamaProduk;
    private EditText edithargaPokok;
    private EditText editHargaJual;
    private EditText editStok;
    private EditText editKodeProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);
        db = new DBHelper(this);

        inisialisasi();
        load_function();
    }
    private void load_function() {
        //fungsi untuk memberishkan isian EditText dengan Button Bersihkan
        btnBersihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKodeProduk.getText().clear();
                editNamaProduk.getText().clear();
                edithargaPokok.getText().clear();
                editHargaJual.getText().clear();
                editStok.getText().clear();
            }
        });

        //fungsi untuk membuka atau menjalankan Activity_ListProduk
        // dengan mengklik Button Lihat Data
        btnLihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddProdukActivity.this, MainActivity.class));
            }
        });

        //fungsi untuk memproses untuk menyimpan data
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method prosesSimpan()
                prosesSimpan();
            }
        });
    }
    private void prosesSimpan() {
        //mengecek apakah form masih ada yang kosong
        if(editKodeProduk.getText().toString().isEmpty() ||
                editNamaProduk.getText().toString().isEmpty() ||
                edithargaPokok.getText().toString().isEmpty() ||
                editHargaJual.getText().toString().isEmpty() ||
                editStok.getText().toString().isEmpty()){
            //Beritahukan jika ada form yang kosong dengan TOASTER
            Toast.makeText(AddProdukActivity.this,"Data masih belum lengkap.",Toast.LENGTH_SHORT).show();
        }else{
            //periksa apakah sudah ada data dengan kode yang sama
            String queryCek = "SELECT * FROM produk WHERE kode_produk = "+editKodeProduk.getText();
            if(db.customQuery(queryCek).getCount() > 0){
                //jika sudah ada
                Toast.makeText(AddProdukActivity.this,"Kode Produk sudah digunakan.",Toast.LENGTH_SHORT).show();
            }else{
                //jika belum ada
                //lakukan proses insert jika data sudah lengkap
                if(db.insertProduk(
                        //konversi nilai dari text ke tipe data sesuai dengan
                        //yang ada di parameter Method insertProduk pada class DBHelper
                        Integer.valueOf(editKodeProduk.getText().toString()), //parameter 1
                        editNamaProduk.getText().toString(), //parameter 2
                        Double.valueOf(editHargaJual.getText().toString()), //parameter 3
                        Double.valueOf(edithargaPokok.getText().toString()), //parameter 4
                        Integer.valueOf(editStok.getText().toString()) //parameter 5
                )){
                    //beritahukan jika input berhasil dengan TOASTER
                    Toast.makeText(AddProdukActivity.this,"Data berhasil ditambahkan.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void inisialisasi() {
        btnBersihkan = findViewById(R.id.btnBersihkan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnLihatData = findViewById(R.id.btnLihatData);
        btnTransaksi = findViewById(R.id.btnTransaksi);

        editKodeProduk = findViewById(R.id.editKodeProduk);
        editNamaProduk = findViewById(R.id.editNamaProduk);
        edithargaPokok = findViewById(R.id.editHargaPokok);
        editHargaJual = findViewById(R.id.editHargaJual);
        editStok = findViewById(R.id.editStokProduk);
    }
}
