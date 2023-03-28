package com.vermeg.demo.controleurs;

import com.vermeg.demo.entities.Produit;
import com.vermeg.demo.service.IServiceCategorie;
import com.vermeg.demo.service.IServiceProduit;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ProduitControleur {

    private final IServiceProduit sp;
    private final IServiceCategorie sc;

    @GetMapping("/home")
    public String getProducts(Model m, @RequestParam(name="mc",defaultValue = "") String mc, @RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="size", defaultValue = "5") int size){
    	Page<Produit> produits = sp.getProduitBMC(mc, PageRequest.of(page-1, size));
        m.addAttribute("data", produits.getContent());
        m.addAttribute("current", produits.getNumber());
        m.addAttribute("pages", new int[produits.getTotalPages()]);
        m.addAttribute("mc",mc);
        return "produits";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id){
        sp.deleteProduit(id);
        return "redirect:/home";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model m){
    	m.addAttribute("produit",new Produit());
        m.addAttribute("categories",sc.getAllCategories());
        return "addProduct";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute Produit produit, BindingResult result, Model m){
    	if (result.hasErrors()) {
    		m.addAttribute("categories", sc.getAllCategories());
    		return "addProduct";
    	}
        sp.saveProduit(produit);
        return "redirect:/home";
    }
    
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model m){
    	Produit product = sp.getProduit(id);
        m.addAttribute("produit", product);
    	m.addAttribute("categories",sc.getAllCategories());
        return "addProduct";
    }
}
