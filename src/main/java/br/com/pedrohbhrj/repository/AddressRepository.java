package br.com.pedrohbhrj.repository;

import br.com.pedrohbhrj.models.Address;
import br.com.pedrohbhrj.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(Long userId);
}
