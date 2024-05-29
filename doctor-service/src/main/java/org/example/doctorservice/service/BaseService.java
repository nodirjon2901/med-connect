package org.example.doctorservice.service;

import java.util.List;
import java.util.UUID;

/**
 * @author Nodir Tojiahmedov
 * @param <CD> Create DTO
 * @param <RD> Response DTO
 * */
public interface BaseService <RD, CD>{

    void save(CD cd);

    void delete(Long id);

    RD update(CD cd, Long id);

    RD findById(Long id);

    List<RD> findAll();

}
