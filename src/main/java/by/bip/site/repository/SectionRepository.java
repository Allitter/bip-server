package by.bip.site.repository;

import by.bip.site.model.Page;
import by.bip.site.model.Section;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

    Optional<Section> findFirstByTitle(String title);

    @Query(value = "SELECT * FROM section s WHERE s.removed=1", nativeQuery = true)
    List<Page> recycleBin();

    @Query("update Section s set s.removed=true where s.id=?1")
    @Modifying
    void softDelete(long id);
}
