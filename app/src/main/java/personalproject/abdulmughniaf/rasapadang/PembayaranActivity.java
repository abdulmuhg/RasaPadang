package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PembayaranActivity extends AppCompatActivity {
    private DBHelper db;
    private TextView tvTotalBayar, tvUangPembeli, tvUangKembalian, tvTotalUntung, tvKodeTrans;
    private Button btnSelesai, btnLihatLaba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        db = new DBHelper(this);
        
        inisialisasi();
        Double harga_total = getIntent().getDoubleExtra("harga_total", 0);
        Double uang_pembeli = getIntent().getDoubleExtra("uang_pembeli", 0);
        Double uang_kembalian = getIntent().getDoubleExtra("uang_kembalian", 0);
        Double total_keuntungan = getIntent().getDoubleExtra("total_keuntungan", 0);
        String kode_transaksi = getIntent().getStringExtra("kode_transaksi");

        tvKodeTrans.setText("Kode Transaksi : " + kode_transaksi);
        tvTotalBayar.setText("Total Harga : Rp " + harga_total);
        tvUangPembeli.setText("Uang Yang Dibayar : Rp " + uang_pembeli);
        tvUangKembalian.setText("Uang Kembalian : Rp " + uang_kembalian);
        tvTotalUntung.setText("Keuntungan : Rp " + total_keuntungan);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PembayaranActivity.this, MainActivity.class);
                clearTabelKeranjang();
                startActivity(i);
            }
        });

        btnLihatLaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PembayaranActivity.this, LabaActivity.class);
                startActivity(i);
            }
        });

    }
    private void clearTabelKeranjang(){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("keranjang", null, null);
    }

    private void inisialisasi() {
        tvTotalBayar = findViewById(R.id.tvtotalBayarP);
        tvUangPembeli = findViewById(R.id.tvUangPembeli);
        tvUangKembalian = findViewById(R.id.tvUangKembalian);
        tvTotalUntung = findViewById(R.id.tvTotalUntung);
        btnSelesai = findViewById(R.id.btnSelesai);
        btnLihatLaba = findViewById(R.id.btnLihatLaba);
        tvKodeTrans = findViewById(R.id.tvKodeTrans);
    }
}
