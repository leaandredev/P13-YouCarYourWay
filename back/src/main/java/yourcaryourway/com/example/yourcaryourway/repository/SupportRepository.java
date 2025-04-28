package yourcaryourway.com.example.yourcaryourway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yourcaryourway.com.example.yourcaryourway.models.Support;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

}
