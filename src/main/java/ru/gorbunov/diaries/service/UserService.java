/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gorbunov.diaries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorbunov.diaries.domain.User;

import ru.gorbunov.diaries.repository.RoleRepository;
import ru.gorbunov.diaries.repository.UserRepository;
import ru.gorbunov.diaries.security.SecurityUtils;

/**
 *
 * @author Gorbunov.ia
 */
@Service
@Transactional
public class UserService {
    
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }    
    
    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        return userRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public User getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    }    
    
}
