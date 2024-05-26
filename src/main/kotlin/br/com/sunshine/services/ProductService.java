package br.com.sunshine.services;

import br.com.sunshine.dto.ProductDTO;
import br.com.sunshine.exception.causable.ErrDateTransfer;
import br.com.sunshine.model.Product;
import br.com.sunshine.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    
    private final ModelMapper mapper = new ModelMapper();
    
    public ProductDTO create(Product product){
        return mapper.map(repository.save(product), ProductDTO.class);
    }

    public ProductDTO update(Long id,ProductDTO productDTO){
        Optional<Product> product_ = repository.findById(id);

        if(product_.isPresent()){
            Product product = product_.get();
            product.setProductName(productDTO.getProductName());
            product.setCategory(productDTO.getCategory());
            product.setDescription(productDTO.getDescription());
            product.setAvailable(productDTO.getAvailable());
            product.setBasePrice(productDTO.getBasePrice());
            product.setPhotoUrl(productDTO.getPhotoUrl());
            //product.setAttributes(productDTO.getAttributes());

            return mapper.map(repository.save(product), ProductDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }
    
    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<ProductDTO> search(String s){
        return  repository.search(s).stream().map(product -> mapper.map(product, ProductDTO.class)).toList();
    }

    public ProductDTO getById(Long id){
        return mapper.map(repository.findById(id), ProductDTO.class);
    }

    public List<ProductDTO> getByOffSet(int offset){
        return repository.getByOffSet(offset).stream().map(product -> mapper.map(product, ProductDTO.class)).toList();
    }

    public int getSize(){
        return repository.size();
    }
}
