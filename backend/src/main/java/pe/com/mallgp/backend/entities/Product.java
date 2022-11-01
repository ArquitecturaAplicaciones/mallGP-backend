package pe.com.mallgp.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String category;

    @OneToMany(mappedBy = "product")
    private List<ProductStore>productStores;
    public Product(String name){this.name=name;}

    @OneToMany(mappedBy = "product")
    private List<Offer>offers;

    public Product(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
