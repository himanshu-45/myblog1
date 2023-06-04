package com.myblog1.service;

import com.myblog1.payload.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Page<ProductDTO> getAllProducts(Pageable pageable);
    public ProductDTO getProductById(Long id);
    public ProductDTO saveProduct(ProductDTO productDTO);
    public ProductDTO updateProduct(ProductDTO productDTO, Long id);
    public void deleteProduct(Long id);
    public List<ProductDTO> searchProductsByName(String name);
}
