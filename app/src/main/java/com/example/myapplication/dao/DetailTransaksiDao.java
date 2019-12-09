package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.DetailTransaksi;

import java.util.List;

@Dao
public interface DetailTransaksiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertDetailTransaksi(DetailTransaksi detailTransaksi);

    @Query("SELECT nama_produk, SUM(qty) as total FROM tb_detail_transaksi " +
            "inner join tb_transaksi on tb_detail_transaksi.id_transaksi = tb_transaksi.id " +
            "inner join tb_produk on tb_detail_transaksi.id_produk = tb_produk.id "+
            "group by id_produk order by total desc")
    List<Terlaris> readDataTerlaris();

    @Query("select case strftime('%m',tanggal) when '01' then 'January' when '02' then 'Febuary' when '03' then 'March' when '04' then 'April' when '05' then 'May' when '06' then 'June' when '07' then 'July' when '08' then 'August' when '09' then 'September' when '10' then 'October' when '11' then 'November' when '12' then 'December' else '' end as bulan, strftime('%m',tanggal) as bln, SUM(qty * harga) as total from tb_detail_transaksi inner join tb_transaksi on tb_detail_transaksi.id_transaksi = tb_transaksi.id inner join tb_produk on tb_detail_transaksi.id_produk = tb_produk.id where strftime('%Y',tanggal) = strftime('%Y','now') group by bln order by bln asc")
    List<ReportTahun> reportTahun();

    class Terlaris {
        int total;
        String nama_produk;

        public Terlaris(int total, String nama_produk){
            this.total = total;
            this.nama_produk = nama_produk;
        }
        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getNama_produk() {
            return nama_produk;
        }

        public void setNama_produk(String nama_produk) {
            this.nama_produk = nama_produk;
        }
    }

    class ReportTahun{
        String bulan;
        int total;

        public String getBulan() {
            return bulan;
        }

        public void setBulan(String bulan) {
            this.bulan = bulan;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
