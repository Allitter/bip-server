package by.bip.site.service;

import java.util.List;

public interface CrudService <T> {

    T add(T t);

    T update(T t);

    T remove(long id);

    T findById(long id);

    List<T> findAll();
}
