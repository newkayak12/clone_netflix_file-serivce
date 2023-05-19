package com.netflix_clone.userservice.repository.domains.compositionKey;

import com.netflix_clone.userservice.repository.domains.Profile;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@Embeddable
@Data
public class ProfileId {
    @OneToOne
    @JoinColumn(name = "profileNo")
    private Profile profile;
}
