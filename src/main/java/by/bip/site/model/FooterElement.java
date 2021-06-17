package by.bip.site.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FooterElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(columnDefinition="Text")
    private String imageUrl;
    private Integer position;
    private String url;
}
