package gods.work.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터 없는 생성자 생성
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "trainer_id", nullable = false, unique = true)
    private int trainerId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(int trainerId, String refreshToken) {
        this.trainerId = trainerId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }

}
