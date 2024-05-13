package gods.work.backend.repository;

import gods.work.backend.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findByEmail(String email);

    Optional<Trainer> findByTrainerLoginId(String trainerLoginId);

    @Modifying
    @Query(value = "update Trainer t set t.password=:password where t.trainerId=:trainerId")
    void updatePasswordByTrainerId(int trainerId, String password);
}
