package pe.com.mallgp.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="offers")
@Data
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String date_on;
    private String date_of;

    @ManyToOne /*(fetch = FetchType.LAZY, optional = false)*/
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne /*(fetch = FetchType.LAZY, optional = false)*/
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public Offer(String name, String date_on, String date_of, Store store, Product product) {
        this.name = name;
        this.date_on = date_on;
        this.date_of = date_of;
        this.store = store;
        this.product = product;
    }
}
