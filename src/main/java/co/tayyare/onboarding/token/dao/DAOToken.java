package co.tayyare.onboarding.token.dao;

import co.tayyare.onboarding.user.dao.DAOUserInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="token")
public class DAOToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "token_id")
    private Long tokenId;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private DAOUserInfo userInfo;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private Date expireDate;

    @Getter
    @Setter
    private int tryCount;

    @Getter
    @Setter
    private int isValidToken;

}
