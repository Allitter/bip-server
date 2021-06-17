package by.bip.site.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SliderElement implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String imageUrl;
    @Column(columnDefinition="Text")
    private String caption;
    private String url;
    private Integer position;
}
