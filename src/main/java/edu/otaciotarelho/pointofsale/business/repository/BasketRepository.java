package edu.otaciotarelho.pointofsale.business.repository;

import edu.otaciotarelho.pointofsale.domain.checkout.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {

}
