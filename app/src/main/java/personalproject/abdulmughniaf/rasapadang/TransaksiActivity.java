package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class TransaksiActivity extends AppCompatActivity {
    private RecyclerView listTrans;
    public ArrayList<ModelTransaksi> transaksiModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        transaksiModel = new ArrayList<>();
        transaksiModel.clear();
        transaksiModel = (ArrayList<ModelTransaksi>) getIntent().getSerializableExtra("transaksiModels");
        listTrans = findViewById(R.id.listTransaksi);

        listTrans.setLayoutManager(new LinearLayoutManager(this));

        AdapterTransaksi adapterTransaksi = new AdapterTransaksi(this, transaksiModel);
        listTrans.setAdapter(adapterTransaksi);
        listTrans.getAdapter().notifyDataSetChanged();

    }
}
