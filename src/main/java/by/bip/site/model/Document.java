package by.bip.site.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    private String originName;
    private String descriptiveImage;
    @CreationTimestamp
    private LocalDateTime createTime;

    public Document(String name, String originName, String descriptiveImage) {
        this.name = name;
        this.originName = originName;
        this.descriptiveImage = descriptiveImage;
    }
}
