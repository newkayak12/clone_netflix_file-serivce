package com.netflix_clone.userservice.repository.domains.compositionKey;

import com.netflix_clone.userservice.repository.domains.Profile;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@Embeddable
@Data
public class ProfileId implements Serializable {
    @OneToOne
    @JoinColumn(name = "profileNo")
    private Profile profile;
}
