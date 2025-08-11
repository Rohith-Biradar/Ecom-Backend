package com.ecom.ecomproject.controller;

import com.ecom.ecomproject.model.Product;
import com.ecom.ecomproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://ecom-frontend-ten-beige.vercel.app")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?>addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product savedproduct= null;
        try {
            savedproduct = service.addProduct(product,imageFile);
            return new ResponseEntity<>(savedproduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product=null;
        try {
            product = service.getProductById(productId);
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId){
        Product product=service.getProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product updatedproduct= null;
        try {
            updatedproduct = service.addProduct(product,imageFile);
            return new ResponseEntity<>("updated",HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        Product deleteProduct=service.getProductById(productId);
        if(deleteProduct!=null){

            service.deleteProductById(productId);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product>products=service.searchProduct(keyword);
        System.out.println("searching with "+keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}


