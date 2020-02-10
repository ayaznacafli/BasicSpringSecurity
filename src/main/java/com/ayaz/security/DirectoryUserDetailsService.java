package com.ayaz.security;

import com.ayaz.domain.Person;
import com.ayaz.repository.PersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DirectoryUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;

    public DirectoryUserDetailsService(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final Person person = this.personRepository.findByEmailIgnoreCase(username);
            if(person!=null){
                PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                String password = encoder.encode(person.getPassword());
                return User.withUsername(person.getName())
                            .accountLocked(!person.isEnabled())
                            .password(password)
                            .roles(person.getRole())
                            .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }
}
