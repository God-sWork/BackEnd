package gods.work.backend.repository;

import gods.work.backend.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findByTrainerLoginId(String trainerLoginId);
}
