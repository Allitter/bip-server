package by.bip.site.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "removed=false")
public class Page implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(columnDefinition="Text")
    private String name;
    @Column(columnDefinition="Text")
    private String shortDesc;
    @Column(columnDefinition="Text")
    private String content;
    @ManyToOne
    private Section section;
    @UpdateTimestamp
    private LocalDateTime lastUpdateTime;
    @CreationTimestamp
    private LocalDateTime createTime;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private Boolean removed = false;

    @Override
    public String toString() {
        return new StringJoiner(", ", Page.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("content='" + content + "'")
                .add("section=" + section.getId())
                .add("lastUpdateTime=" + lastUpdateTime)
                .add("createTime=" + createTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page page = (Page) o;
        return Objects.equals(id, page.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
