package pe.com.mallgp.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stores_malls")
@Data
@NoArgsConstructor
public class StoreMall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name="mall_id")
    private Mall mall;

    private Integer capacity;

    @OneToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

}
