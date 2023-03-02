package com.playapp.starter.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.playapp.starter.data.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {
    Page<Event> findAll(Pageable pageable);
    Optional<Event> findByIntegerId(Integer uniqueId);
}
