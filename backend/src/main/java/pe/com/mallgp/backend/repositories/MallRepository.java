package pe.com.mallgp.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.mallgp.backend.entities.Mall;

import java.util.List;

public interface MallRepository extends JpaRepository <Mall, Long> {
    Mall findByName(String name);
    List<Mall> findByLocation(String location);

}
