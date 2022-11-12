package pe.com.mallgp.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="malls")
@Data
@NoArgsConstructor
public class Mall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String name;
    public String location;

    @OneToMany(mappedBy = "mall")
    private List<StoreMall>storeMalls;

    @OneToMany(mappedBy = "mall")
    private List<New>news;


    public Mall(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
