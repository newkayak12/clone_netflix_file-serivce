package com.netflix_clone.userservice.service;

import com.netflix_clone.userservice.configure.feign.FileFeign;
import com.netflix_clone.userservice.enums.FileType;
import com.netflix_clone.userservice.repository.dto.reference.FileDto;
import com.netflix_clone.userservice.repository.dto.reference.MobileDeviceInfoDto;
import com.netflix_clone.userservice.repository.dto.reference.ProfileDto;
import com.netflix_clone.userservice.repository.dto.request.ProfileRequest;
import com.netflix_clone.userservice.repository.profileRepository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProfileService {
    private final ProfileRepository repository;
    private final FileFeign feign;

    private Boolean isDeviceInfoChanged(Long userNo, MobileDeviceInfoDto mobileDeviceInfoDto){

        return false;
    }
    private void changeDeviceInfo(Long userNo, MobileDeviceInfoDto mobileDeviceInfoDto){

    }


    public List<ProfileDto> profiles(ProfileRequest profileRequest) {
        List<ProfileDto> profiles = repository.profiles(profileRequest);

        List<FileDto> fileDtos = feign.files(profiles.stream().map(ProfileDto::getProfileNo).collect(Collectors.toList()), FileType.PROFILE).getBody();
        profiles.forEach( profile -> {
                                    FileDto file = fileDtos.stream()
                                                           .filter(fileDto -> fileDto.getTableNo().equals(profile.getProfileNo()))
                                                           .findAny()
                                                           .orElseGet(() -> null);
                                    profile.setProfileImage(file);
                        });

        return profiles;
    }
}
