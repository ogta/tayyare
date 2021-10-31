package co.tayyare.onboarding.saas.dao;

import co.tayyare.onboarding.user.dao.DAOUserInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "saas")
public class DAOSaasInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "saas_id")
    private long saasID;


    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer status;

    @Getter
    @Setter
    private String pack;

    @Getter
    @Setter
    @Column(name = "saas_token")
    private String saasToken;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private DAOUserInfo ownerInfo;

    @Getter
    @Setter
    @Column(name = "unique_value")
    private String uniqueValue;

}
