package gods.work.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "member")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private int memberId;

    @Column(name = "center_id")
    private int centerId;

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

    @Column(name = "status")
    private String status;

    @Column(name = "registration_ymd")
    private String registrationYmd;

    @Column(name = "is_active")
    private String isActive;

}