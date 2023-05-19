package com.netflix_clone.userservice.controller;

import com.netflix_clone.userservice.repository.dto.reference.ProfileDto;
import com.netflix_clone.userservice.repository.dto.request.ProfileRequest;
import com.netflix_clone.userservice.service.ProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@RestController
@RequestMapping(value = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;

    @GetMapping(value = "/")
    public ResponseEntity<List<ProfileDto>> profiles(@ModelAttribute ProfileRequest profileRequest){
        return new ResponseEntity(service.profiles(profileRequest), HttpStatus.OK);
    }
}
