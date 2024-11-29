package Backend;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author WINDOWS 11
 */

public class Buku {

    private int idBuku;
    private Kategori kategori;
    private String judul;
    private String penerbit;
    private String penulis;

    // Constructor
    public Buku() {
        this.kategori = new Kategori();
    }

    public Buku(Kategori kategori, String judul, String penerbit, String penulis) {
        this.kategori = kategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    // Getter dan Setter
    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    // Metode untuk mendapatkan data berdasarkan ID
    public Buku getById(int id) {
        Buku buku = new Buku();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM buku b "
                + "LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                + "WHERE b.idbuku = " + id);
        try {
            if (rs.next()) {
                buku.setIdBuku(rs.getInt("idbuku"));
                buku.getKategori().setIdKategori(rs.getInt("idkategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buku;
    }

    // Metode untuk mendapatkan semua data
    public ArrayList<Buku> getAll() {
        ArrayList<Buku> listBuku = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM buku b "
                + "LEFT JOIN kategori k ON b.idkategori = k.idkategori");
        try {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("idbuku"));
                buku.getKategori().setIdKategori(rs.getInt("idkategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
                listBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBuku;
    }

    // Metode untuk mencari data
    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> listBuku = new ArrayList<>();
        String sql = "SELECT b.idbuku AS idbuku, b.judul AS judul, b.penerbit AS penerbit, b.penulis AS penulis, "
                + "k.idkategori AS idkategori, k.nama AS nama, k.keterangan AS keterangan "
                + "FROM buku b LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                + "WHERE b.judul LIKE '%" + keyword + "%' "
                + "OR b.penerbit LIKE '%" + keyword + "%' "
                + "OR b.penulis LIKE '%" + keyword + "%'";
        ResultSet rs = DBHelper.selectQuery(sql);
        try {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("idbuku"));
                buku.getKategori().setIdKategori(rs.getInt("idkategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
                listBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBuku;
    }

    // Metode untuk menyimpan data
    public void save() {
        if (getById(idBuku).getIdBuku() == 0) {
            String sql = "INSERT INTO buku (idkategori, judul, penerbit, penulis) VALUES ("
                    + "'" + this.getKategori().getIdKategori() + "', "
                    + "'" + this.judul + "', "
                    + "'" + this.penerbit + "', "
                    + "'" + this.penulis + "')";
            this.idBuku = DBHelper.insertQueryGetId(sql);
        } else {
            String sql = "UPDATE buku SET "
                    + "idkategori = '" + this.getKategori().getIdKategori() + "', "
                    + "judul = '" + this.judul + "', "
                    + "penerbit = '" + this.penerbit + "', "
                    + "penulis = '" + this.penulis + "' "
                    + "WHERE idbuku = '" + this.idBuku + "'";
            DBHelper.executeQuery(sql);
        }
    }

    // Metode untuk menghapus data
    public void delete() {
        String sql = "DELETE FROM buku WHERE idbuku = '" + this.idBuku + "'";
        DBHelper.executeQuery(sql);
    }
}

