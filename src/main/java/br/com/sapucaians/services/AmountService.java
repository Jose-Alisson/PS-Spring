package br.com.sapucaians.services;

import br.com.sapucaians.dto.AmountDTO;
import br.com.sapucaians.enums.AmountStatus;
import br.com.sapucaians.exception.causable.ErrDateTransfer;
import br.com.sapucaians.model.Account;
import br.com.sapucaians.model.Amount;
import br.com.sapucaians.model.Attribute;
import br.com.sapucaians.repository.AccountRepository;
import br.com.sapucaians.repository.AmountRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AmountService {

    @Autowired
    private AmountRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    private final ModelMapper mapper = new ModelMapper();

    AmountService(){
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Transactional()
    public AmountDTO create(Amount amount){
        amount.setStatus(AmountStatus.DESANEXADO);

        if(amount.getAccount() == null){
            amount.setAccount(Account.builder().id(accountRepository.getIdByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())).build());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return mapper.map(repository.save(amount), AmountDTO.class);
    }

    public AmountDTO update(AmountDTO amountDTO) {
        Optional<Amount> amount_ = repository.findById(amountDTO.getId());
        if(amount_.isPresent()){
            Amount amount = amount_.get();
            amount.setProduct(amount.getProduct());
            amount.setQuantity(amountDTO.getQuantity());
            amount.setSelectedAttribute(amountDTO.getSelectedAttribute().stream().map(attributeDTO -> mapper.map(attributeDTO, Attribute.class)).toList());
            return mapper.map(repository.save(amount), AmountDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<AmountDTO> getAllByUserId(Long id){
        return repository.findAllByUserId(id).stream().map(amount -> mapper.map(amount, AmountDTO.class)).toList();
    }

    public List<AmountDTO> getAllByLogged(){
        var email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getAllByUserId(accountRepository.getIdByEmail(email));
    }

    public int decrement(String id){
        return repository.decrement(id, 1);
    }

    public int increment(String id){
        return repository.increment(id, 1);
    }
}
