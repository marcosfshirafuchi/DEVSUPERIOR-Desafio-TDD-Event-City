package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceBadRequestException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1> DEVSUPERIOR - Java Spring Expert - Capítulo: Testes automatizados</h1>
 * DESAFIO: DESAFIO TDD Event City
 * <p>
 * <b>Note:</b> Desenvolvido na linguagem Java.
 *
 * @author  Marcos Ferreira Shirafuchi
 * @version 1.0
 * @since   31/08/2024
 */

@Service
public class CityService {
    @Autowired
    private CityRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> list = repository.findAll(Sort.by("name"));
        return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    @Transactional(readOnly = true)
    public void delete(Long id) {
        // Verifica se a cidade existe
        City city = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com id " + id));

        // Verifica se a cidade tem eventos associados
        if (!city.getEvents().isEmpty()) {
            throw new ResourceBadRequestException("A cidade não pode ser excluída porque possui eventos associados!");
        }
        try {
            // Exclui a cidade
            repository.delete(city);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

}
