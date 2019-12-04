package com.example.myapplication.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProdukWithRelations {
    @Embedded
    public Produk produk;

    @Relation(parentColumn = "id_kategori", entityColumn = "id", entity = Kategori.class)
    public List<Kategori> kategoris;

}
