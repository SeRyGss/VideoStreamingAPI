package org.api.repository;

import org.api.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Optional<List<Video>> findByIsDelistedFalse();
    Optional<List<Video>> findByDirectorAndIsDelistedFalse(String director);
    Optional<List<Video>> findByTitle(String title);
}
