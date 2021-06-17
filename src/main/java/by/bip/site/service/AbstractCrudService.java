package by.bip.site.service;

import by.bip.site.exception.ResourceNotFoundException;
import by.bip.site.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends Model> implements CrudService<T> {
    private final CrudRepository<T, Long> repository;

    @Override
    public T findById(long id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public T add(T model) {
        model.setId(null);
        return repository.save(model);
    }

    @Override
    public T update(T model) {
        throwNotFoundExceptionIfEntityNotExists(model.getId());
        return repository.save(model);
    }

    @Override
    public T remove(long id) {
        T model = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        repository.delete(model);
        return model;
    }

    @Override
    public List<T> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private void throwNotFoundExceptionIfEntityNotExists(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
    }
}
