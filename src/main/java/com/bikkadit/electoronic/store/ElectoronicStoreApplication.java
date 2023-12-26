package com.bikkadit.electoronic.store;

import com.bikkadit.electoronic.store.model.Role;
import com.bikkadit.electoronic.store.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class ElectoronicStoreApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${normal.role.id}")
    private String role_normal_id;

    @Value("${admin.role.id}")
    private String role_admin_id;

    public static void main(String[] args) {
        SpringApplication.run(ElectoronicStoreApplication.class, args);

        System.out.println("Application Started Successfully");

    }

    // Command Line Runner Mainly use for if we want to run something before main method that time we use it
    // Here We Encode PassWord IN bycreptedForm
    @Override
    public void run(String... args) throws Exception {

        System.out.println(passwordEncoder.encode("4587"));


        try {


            Role role_Admin = Role.builder()
                    .roleId(role_admin_id)
                    .roleName("ROLE_ADMIN")
                    .build();

            Role role_Normal = Role.builder()
                    .roleId(role_normal_id)
                    .roleName("ROLE_NORMAL")
                    .build();

            this.roleRepository.save(role_Admin);
            this.roleRepository.save(role_Normal);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
