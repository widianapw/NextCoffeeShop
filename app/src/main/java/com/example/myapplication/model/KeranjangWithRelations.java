package com.example.myapplication.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class KeranjangWithRelations {
    @Embedded
    public Keranjang keranjang;

    @Relation(parentColumn = "id_produk", entityColumn = "id", entity = Produk.class)
    public List<Produk> produks;
}
