package gods.work.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "center")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id", updatable = false)
    private int centerId;

    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "business_contact")
    private String businessContact;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "back_name")
    private String bankName;

    @Column(name = "account_holder")
    private String accountHolder;

    @Column(name = "authentication_code")
    private String authenticationCode;

    @Column(name = "is_active")
    private String isActive;



}