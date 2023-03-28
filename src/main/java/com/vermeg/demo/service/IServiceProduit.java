package com.vermeg.demo.service;

import com.vermeg.demo.entities.Produit;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceProduit {
    void saveProduit(Produit p);
    List<Produit> getAllProduit();
    Page<Produit> getProduitBMC(String mc, Pageable pg);
    List <Produit> getProduitByCat(Integer idcat);
    Produit getProduit(Integer id);
    void deleteProduit (Integer id);


}
