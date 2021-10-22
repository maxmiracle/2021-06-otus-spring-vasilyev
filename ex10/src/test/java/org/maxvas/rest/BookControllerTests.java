package org.maxvas.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        List<JsonElement> actualJson = toJsonList(mvcResult.getResponse().getContentAsString());
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "json/getAllBooks.json");
        List<JsonElement> expectedJson = toJsonList(new String(Files.readAllBytes(file.toPath())));
        // id values generated, so test equality for logical data only
        assertResultEqual(expectedJson, actualJson,
                jsonElement -> jsonElement.getAsJsonObject().get("title").getAsString(),
                jsonElement -> jsonElement.getAsJsonObject().getAsJsonObject("author").get("name").getAsString(),
                jsonElement -> jsonElement.getAsJsonObject().getAsJsonObject("genre").get("name").getAsString()
        );
    }

    private List<JsonElement> toJsonList(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(jsonString);
        Stream<JsonElement> jsonElementStream = StreamSupport.stream(je.getAsJsonArray().spliterator(), false);
        List<JsonElement> jsonElementList = jsonElementStream.sorted(new Comparator<JsonElement>() {
            @Override
            public int compare(JsonElement o1, JsonElement o2) {
                String title1 = o1.getAsJsonObject().get("title").getAsString();
                String title2 = o2.getAsJsonObject().get("title").getAsString();
                return StringUtils.compare(title1, title2);
            }
        }).collect(Collectors.toList());
        return jsonElementList;
    }

    /**
     * Test eqality for all results of lambdas applied to expected and actual objects.
     *
     * @param expected      expected JsonElement
     * @param actual        actual JsonElement
     * @param testFunctions testFunction
     */
    private void assertResultEqual(List<JsonElement> expected, List<JsonElement> actual, Function<JsonElement, String>... testFunctions) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int index = 0; index < expected.size(); index++) {
            JsonElement actualElement = actual.get(index);
            JsonElement expectedElement = expected.get(index);
            for (Function<JsonElement, String> func :
                    testFunctions) {
                String expectedResult = func.apply(expectedElement);
                String actualResult = func.apply(actualElement);
                assertEquals(expectedResult, actualResult);
            }
        }
    }


}
