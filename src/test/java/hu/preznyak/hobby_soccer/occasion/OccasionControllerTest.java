package hu.preznyak.hobby_soccer.occasion;

import hu.preznyak.hobby_soccer.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
class OccasionControllerTest extends BaseIT {

    @Test
    void initCreationFormWithUser() throws Exception {
        mockMvc.perform(get("/occasions/add").with(httpBasic("spring", "training")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("occasion/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("occasion"));
    }

}