package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class TransaksiActivity extends AppCompatActivity {
    TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        ArrayList<String> nama_produk = (ArrayList<String>) getIntent().getSerializableExtra("nama_produk");
        tvTest = findViewById(R.id.tvTestingIntent);
        tvTest.setText(nama_produk+"");
    }
}
