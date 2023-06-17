package com.bobe.urlshortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bobe.urlshortner.model.Source;
import com.bobe.urlshortner.model.Url;

public interface SourceRepository extends JpaRepository<Source,Long> {
    public Optional<Source> findByNameIgnoreCaseAndUrl(String name, Url url);
}
