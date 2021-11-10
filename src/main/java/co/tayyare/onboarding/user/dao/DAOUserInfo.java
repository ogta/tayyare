package co.tayyare.onboarding.user.dao;

import co.tayyare.onboarding.saas.dao.DAOSaasInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
public class DAOUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "user_id")
    private Long userId;


    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String deviceId;


    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saas_id", referencedColumnName = "saas_id")
    private DAOSaasInfo saasInfo;

    @Getter
    @Setter
    private String mailHash;

}
