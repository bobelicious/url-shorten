package com.bobe.urlshortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bobe.urlshortner.model.Url;

public interface UrlRepository extends JpaRepository<Url,Long> {
    public Optional<Url> findByUrlShortened(String urlShortened);
}
