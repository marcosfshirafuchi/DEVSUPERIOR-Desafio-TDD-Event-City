package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class EventService {
    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {

        // Verifica se a cidade associada existe
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada com id " + dto.getCityId()));
        try {
            Event entity = repository.getReferenceById(id);
            // Atualiza o evento com os dados do DTO
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setUrl(dto.getUrl());
            entity.setCity(city);

            // Salva o evento atualizado no banco de dados
            entity = repository.save(entity);

            // Retorna o DTO atualizado
            return new EventDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Evento não encontrado com id " + id);
        }
    }

//    @Transactional
//    public EventDTO update(Long id, EventDTO dto) {
//        try {
//            Event entity = repository.getReferenceById(id);
//            entity.setName(dto.getName());
//            entity.setDate(dto.getDate());
//            entity.setUrl(dto.getUrl());
//            //entity.setCity(dto.getCityId());
//            //copyDTOToEntity(dto, entity,id);
//            entity = repository.save(entity);
//            return new EventDTO(entity);
//        }
//        catch (EntityNotFoundException e){
//            throw new ResourceNotFoundException("Id not found " + id);
//        }
//    }

//    private void copyDTOToEntity(EventDTO dto, Event entity,Long id) {
//
//        entity.setCity(dto.getCityId().compareTo(id));
//    }
}
