package gods.work.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Table(name = "trainer")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Trainer extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id", updatable = false)
    private int trainerId;

    @Column(name = "center_id")
    private int centerId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_ymd")
    private String birthYmd;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registration_ymd")
    private String registrationYmd;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부 반환
        return false;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠금 여부 반환
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 패스워드 만료 여부 반환
        return false;
    }

    @Override
    public boolean isEnabled() { // 계정 사용 기능 여부 반환
        return false;
    }
}