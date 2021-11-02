package org.maxvas.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({SecurityConfiguration.class})
class BookControllerTest {

    private static final String REDIRECT_LOGIN = "http://localhost/login";
    private static final String BOOK_ID = "1";
    private static final String SECOND_BOOK_ID = "2";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getListOkTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    public void getListOkWithUserTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    public void getCreateTest() throws Exception {
        mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCreateCantAccessTest() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(redirectedUrl(REDIRECT_LOGIN));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void postCreateTest() throws Exception {
        mockMvc.perform(post("/create?id=")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("title=max1&author.name=max2&genre.name=max3"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void postCreateCantAccessTest() throws Exception {
        mockMvc.perform(post("/create?id=")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("title=title&author.name=authorname&genre.name=genrename"))
                .andExpect(redirectedUrl(REDIRECT_LOGIN));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getEditTest() throws Exception {
        mockMvc.perform(get("/edit?id=" + BOOK_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void getEditCantAccessTest() throws Exception {
        mockMvc.perform(get("/edit?id=" + BOOK_ID))
                .andExpect(redirectedUrl(REDIRECT_LOGIN));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void postEditTest() throws Exception {
        mockMvc.perform(post("/edit?id=" + BOOK_ID)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("id=" + BOOK_ID + "&title=title&author.name=authorname&genre.name=genrename"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void postEditCantAccessTest() throws Exception {
        mockMvc.perform(post("/edit?id=" + BOOK_ID)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("id=" + BOOK_ID + "&title=title&author.name=authorname&genre.name=genrename"))
                .andExpect(redirectedUrl(REDIRECT_LOGIN));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"ADMIN"})
    public void postDeleteTest() throws Exception {
        mockMvc.perform(post("/delete?id=" + SECOND_BOOK_ID))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void postDeleteCantAccessTest() throws Exception {
        mockMvc.perform(post("/delete?id=" + BOOK_ID))
                .andExpect(redirectedUrl(REDIRECT_LOGIN));
    }

}
