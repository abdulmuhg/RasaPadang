package personalproject.abdulmughniaf.rasapadang;

import java.io.Serializable;

public class ModelTransaksi implements Serializable {
    String namaProduk;
    int kodeProduk;
    int jumlahItem;
    double hargaJual;

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(int kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public int getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(int jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

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
