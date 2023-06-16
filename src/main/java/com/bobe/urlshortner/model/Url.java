package com.bobe.urlshortner.model;

import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "URL", uniqueConstraints = @UniqueConstraint(columnNames = { "URL_SHORTENED" }))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "URL")
    private String url;
    @Column(name = "URL_SHORTENED")
    private String urlShortened;
    @Column(name = "REQUEST_NUMS")
    private Integer requestNums;
    @Column(name="SOURCE")
    private HashMap<String,Integer> source;
    @Column(name="LAST_ACCESS")
    private String lastAccess;
    @Column(name="REGISTER_DATE")
    private String registerDate;
}
