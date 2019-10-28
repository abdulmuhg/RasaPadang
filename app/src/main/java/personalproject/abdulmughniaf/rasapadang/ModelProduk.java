package personalproject.abdulmughniaf.rasapadang;

public class ModelProduk {
    int kode_produk;
    String nama_produk;
    Double harga_pokok;
    Double harga_jual;
    int stok;

    public int getKode_produk() {
        return kode_produk;
    }

    public void setKode_produk(int kode_produk) {
        this.kode_produk = kode_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Double getHarga_pokok() {
        return harga_pokok;
    }

    public void setHarga_pokok(Double harga_pokok) {
        this.harga_pokok = harga_pokok;
    }

    public Double getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(Double harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
