package by.bip.site.repository;

import by.bip.site.model.Page;
import by.bip.site.model.Section;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends CrudRepository<Page, Long> {

    List<Page> findBySection(Section section);

    @Query(value = "SELECT * FROM page p WHERE p.removed=1", nativeQuery = true)
    List<Page> recycleBin();

    @Query("update Page p set p.removed=true where p.id=?1")
    @Modifying
    void softDelete(long id);
}
