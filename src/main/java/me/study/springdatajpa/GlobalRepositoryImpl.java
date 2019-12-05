package me.study.springdatajpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class GlobalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GlobalRepository<T, ID>{

    private EntityManager entityManager;

    public GlobalRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(Object entity) {
        return entityManager.contains(entity);
    }
}
