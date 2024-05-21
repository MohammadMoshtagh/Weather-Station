package edu.sharif.webproject.security;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static edu.sharif.webproject.enduser.EndUserRoleEnum.USER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.sharif.webproject.PostgresqlTestContainersExtension;
import edu.sharif.webproject.enduser.EndUserEntity;
import edu.sharif.webproject.enduser.EndUserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresqlTestContainersExtension.class)
public class AuthorizationTest {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        PostgresqlTestContainersExtension.registerJdbcProperties(registry);
    }

    @BeforeEach
    public void createUsers() {
        EndUserEntity mazziEntity = new EndUserEntity();
        mazziEntity.setUsername("mazzi");
        mazziEntity.setPassword(passwordEncoder.encode("1234"));
        mazziEntity.setEnable(false);
        mazziEntity.setRole(USER);
        endUserRepository.save(mazziEntity);

        EndUserEntity alizEntity = new EndUserEntity();
        alizEntity.setUsername("aliz");
        alizEntity.setPassword(passwordEncoder.encode("1234"));
        alizEntity.setEnable(false);
        alizEntity.setRole(USER);
        endUserRepository.save(alizEntity);
    }

    @AfterEach
    void tearDown() {
        List<EndUserEntity> endUserEntities = endUserRepository.findAll();
        for (EndUserEntity endUserEntity : endUserEntities) {
            if (!endUserEntity.getUsername().equals("admin")) {
                endUserRepository.delete(endUserEntity);
            }
        }
    }

    @Test
    public void testAdminPresence() {
        Optional<EndUserEntity> endUser = endUserRepository.findEndUserEntityByUsername("admin");
        Assertions.assertTrue(endUser.isPresent());
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        this.mockMvc.perform(get("/countries"))
                .andDo(print())
                .andExpect(status().is(UNAUTHORIZED.value()));
    }

    @Test
    public void testDisableUserAccess() throws Exception {
        this.mockMvc.perform(post("/users/login").contentType(APPLICATION_JSON)
                        .content("{\"username\": \"mazzi\", \"password\": \"1234\"}"))
                .andDo(print())
                .andExpect(status().is(UNAUTHORIZED.value()));
    }

    @Test
    public void testAdminLoginAndChangeUserStatus() throws Exception {
        var mvcResult = this.mockMvc.perform(post("/users/login").contentType(APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"admin\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String authToken = mvcResult.getResponse().getHeader("Authorization");
        this.mockMvc.perform(put("/admin/users")
                        .param("username", "mazzi")
                        .param("active", "true")
                        .header(AUTHORIZATION_HEADER, authToken))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/users/login").contentType(APPLICATION_JSON)
                        .content("{\"username\": \"mazzi\", \"password\": \"1234\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
