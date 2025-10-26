package ru.innotech.discountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innotech.discountapi.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
