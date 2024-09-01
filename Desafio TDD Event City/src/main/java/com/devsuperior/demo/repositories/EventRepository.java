package com.devsuperior.demo.repositories;


import com.devsuperior.demo.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

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

public interface EventRepository extends JpaRepository<Event,Long> {
}
