package pe.com.mallgp.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.mallgp.backend.entities.Store;

import java.util.List;

public interface StoreRepository extends JpaRepository <Store, Long> {

    Store findByName(String name);
    List<Store> findByCategory(String category);
}
