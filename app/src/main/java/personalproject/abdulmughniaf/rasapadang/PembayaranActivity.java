package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PembayaranActivity extends AppCompatActivity {
    private TextView tvTotalBayar, tvUangPembeli, tvUangKembalian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        Double harga_total = getIntent().getDoubleExtra("harga_total", 0);
        Double uang_pembeli = getIntent().getDoubleExtra("uang_pembeli", 0);
        Double uang_kembalian = getIntent().getDoubleExtra("uang_kembalian", 0);
        tvTotalBayar = findViewById(R.id.tvtotalBayarP);
        tvTotalBayar.setText("Rp " + harga_total);

    }
}
