package br.com.sunshine.services;

import br.com.sunshine.dto.AttributeDTO;
import br.com.sunshine.exception.causable.ErrDateTransfer;
import br.com.sunshine.model.Attribute;
import br.com.sunshine.repository.AttributeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeService {
    @Autowired
    private AttributeRepository repository;

    private final ModelMapper mapper = new ModelMapper();

    public AttributeDTO create(Attribute attribute){
        return mapper.map(repository.save(attribute), AttributeDTO.class);
    }

    public AttributeDTO update(AttributeDTO attributeDTO){
        Optional<Attribute> attribute_ = repository.findById(attributeDTO.getId());

        if(attribute_.isPresent()){
            Attribute attribute = attribute_.get();
            attribute.setPhotoUrl(attributeDTO.getPhotoUrl());
            attribute.setAttributeName(attributeDTO.getAttributeName());
            attribute.setAttributePrice(attributeDTO.getAttributePrice());
            attribute.setAvailable(attributeDTO.getAvailable());

            return mapper.map(repository.save(attribute), AttributeDTO.class);
        }
        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }

    public void delete(Long id){
        repository.rrp(id);
        repository.deleteById(id);
    }

    public AttributeDTO getById(Long id){
        var result = repository.findById(id).map(attr -> mapper.map(attr, AttributeDTO.class)).orElse(null);

        if(result == null){
            throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public List<AttributeDTO> getAllByProduct(Long id){
        return repository.findAllByProductId(id).stream().map(attr -> mapper.map(attr, AttributeDTO.class)).toList();
    }
}
