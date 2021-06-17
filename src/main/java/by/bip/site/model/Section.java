package by.bip.site.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "removed=false")
public class Section implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition="Text")
    private String title;
    @OneToMany(mappedBy = "section")
    private List<Page> pages;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private Boolean removed = false;

    public Section(Long id) {
        this.id = id;
    }

}

