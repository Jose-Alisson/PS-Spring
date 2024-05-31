package br.com.sapucaians.services;

import br.com.sapucaians.dto.AddressDTO;
import br.com.sapucaians.exception.causable.ErrDateTransfer;
import br.com.sapucaians.model.Account;
import br.com.sapucaians.model.Address;
import br.com.sapucaians.repository.AccountRepository;
import br.com.sapucaians.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private MapsService mapsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    private final ModelMapper mapper = new ModelMapper();

    public AddressDTO create(Address address) {
        return mapper.map(repository.save(address), AddressDTO.class);
    }

    public AddressDTO update(Long id, AddressDTO addressDTO) {
        Optional<Address> address_ = repository.findById(id);

        if (address_.isPresent()) {
            Address address = address_.get();
            address.setStreet(addressDTO.getStreet());
            address.setHouseNumber(addressDTO.getHouseNumber());
            address.setComplement(addressDTO.getComplement());
            address.setPointReference(addressDTO.getPointReference());
            address.setZipCode(addressDTO.getZipCode());
            address.setLat(addressDTO.getLat());
            address.setLog(addressDTO.getLog());

            return mapper.map(repository.save(address), AddressDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public AddressDTO getById(Long id) {
        var result = repository.findById(id).map(add -> mapper.map(add, AddressDTO.class)).orElse(null);

        if (result == null) {
            throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public List<AddressDTO> getByAccount(Long id) {
        var result = accountRepository.findById(id).map(account -> account.getAddresses().stream().map(address -> mapper.map(address, AddressDTO.class)).toList()).orElse(null);

        if (result == null) {
            throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public Map<String, Object> isDelivery(Address address) {
        var result = new HashMap<String, Object>();

        Address address1 = Address.builder().street("Av. Sapucaia, cohab").build();

        var value = mapsService.getInformationDelivery(address1, address);

        result.put("information", value);
        result.put("is_delivery", ((double) value.get("valueNotFormatted")) >= 0);

        return result;
    }

    public List<AddressDTO> getByLogged() {

        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (email != null) {

            return repository.findAllByUserEmail((String) email).stream().map(address -> mapper.map(address, AddressDTO.class)).toList();
        }

        return null;
    }

    public List<AddressDTO> createByLogged(Address address) {

        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (email != null) {
            Optional<Account> opt = accountRepository.findByEmail((String) email);

            if (opt.isPresent()) {
                Account account = opt.get();
                account.getAddresses().add(address);
                return accountRepository.save(account).getAddresses().stream().map(address1 -> mapper.map(address1, AddressDTO.class)).toList();
            }
        }
        return null;
    }
}
