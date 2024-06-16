package vidar.websystem.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vidar.websystem.util.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-attributes-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-products-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-products-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/sql/create-attributes-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @DisplayName("[200] GET /perfume/1 - Get Perfumes")
//    public void getPerfumeById() throws Exception {
//        mockMvc.perform(get(PathConstants.PRODUCT + "/{perfumeId}", PERFUME_ID))
//                .andExpect(status().isOk())
//                .andExpect(view().name(Pages.PRODUCT))
//                .andExpect(model().attribute("perfume", hasProperty("id", is(PERFUME_ID))))
//                .andExpect(model().attribute("perfume", hasProperty("perfumeTitle", is(PERFUME_TITLE))))
//                .andExpect(model().attribute("perfume", hasProperty("perfumer", is(PERFUMER))))
//                .andExpect(model().attribute("perfume", hasProperty("year", is(YEAR))))
//                .andExpect(model().attribute("perfume", hasProperty("country", is(COUNTRY))))
//                .andExpect(model().attribute("perfume", hasProperty("perfumeGender", is(PERFUME_GENDER))))
//                .andExpect(model().attribute("perfume", hasProperty("fragranceTopNotes", is(FRAGRANCE_TOP_NOTES))))
//                .andExpect(model().attribute("perfume", hasProperty("fragranceMiddleNotes", is(FRAGRANCE_MIDDLE_NOTES))))
//                .andExpect(model().attribute("perfume", hasProperty("fragranceBaseNotes", is(FRAGRANCE_BASE_NOTES))))
//                .andExpect(model().attribute("perfume", hasProperty("filename", is(FILENAME))))
//                .andExpect(model().attribute("perfume", hasProperty("price", is(PRICE))))
//                .andExpect(model().attribute("perfume", hasProperty("volume", is(VOLUME))))
//                .andExpect(model().attribute("perfume", hasProperty("type", is(TYPE))));
//    }

    @Test
    @DisplayName("[404] GET /product/111 - Get Product By Id NotFound")
    public void getProductById_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.PRODUCT + "/{productId}", 111))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Test
    @DisplayName("[200] GET /product - Get Products By Filter Params")
    public void getPerfumesByFilterParams() throws Exception {
        mockMvc.perform(get(PathConstants.PRODUCT))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PRODUCTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(9))));
    }

    @Test
    @DisplayName("[200] GET /product - Get Products By Filter Params: colours")
    public void getPerfumesByFilterParams_Perfumers() throws Exception {
        // plank_id = 1 means colour 'Natural'
        mockMvc.perform(get(PathConstants.PRODUCT)
                        .param("colours", "Natural"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PRODUCTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /product - Get Products By Filter Params: colours, widths")
    public void getPerfumesByFilterParams_PerfumersAndGenders() throws Exception {
        mockMvc.perform(get(PathConstants.PRODUCT)
                        .param("colours", "Natural")
                        .param("widths", "6"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.PRODUCTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }
//
//    @Test
//    @DisplayName("[200] GET /perfume - Get Perfumes By Filter Params: perfumers, genders, price")
//    public void getPerfumesByFilterParams_PerfumersAndGendersAndPrice() throws Exception {
//        mockMvc.perform(get(PathConstants.PRODUCT)
//                        .param("perfumers", "Creed", "Dior")
//                        .param("genders", "male")
//                        .param("price", "60"))
//                .andExpect(status().isOk())
//                .andExpect(view().name(Pages.PRODUCTS))
//                .andExpect(model().attribute("page", hasProperty("content", hasSize(5))));
//    }
}
