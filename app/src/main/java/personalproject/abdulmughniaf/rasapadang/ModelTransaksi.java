package personalproject.abdulmughniaf.rasapadang;

import java.io.Serializable;

public class ModelTransaksi implements Serializable {
    String namaProduk;


    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

//    public double totalHarga() {
//        double total = hargaProduk * jumlah;
//        return total;
//    }
//
//    public double uangKembalian() {
//        double kembalian = uangPembeli - totalHarga;
//        return kembalian;
//    }
}
