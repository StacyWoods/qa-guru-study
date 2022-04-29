package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.matchers.ContainsExactText;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("demoqa-jenkins")
public class FilesParsingTest {

    ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    @Tag("wip")
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File textFile = $("#raw-url").download();
        try (InputStream is = new FileInputStream(textFile)) {
            byte[] fileContent = is.readAllBytes();
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
            org.assertj.core.api.Assertions.assertThat(strContent).contains("JUnit 5");
        }
    }

    @Test
    void pdfParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream( "pdf/junit-user-guide-5.8.2.pdf")) {
            PDF pdf;
            if (stream != null) {
                pdf = new PDF(stream);
                assertEquals(166, pdf.numberOfPages);
                org.hamcrest.MatcherAssert.assertThat(pdf, new ContainsExactText("Stack Overflow"));
            }
        }
    }

    @Test
    void xlsParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("xls/sample-xlsx-file.xlsx")) {
            XLS xls;
            if (stream != null) {
                xls = new XLS(stream);
                String stringCell2Value = xls.excel.getSheetAt(0).getRow(3).getCell(1).getStringCellValue();
                String stringCell3Value = xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue();
                org.assertj.core.api.Assertions.assertThat(stringCell2Value).contains("Stacy");
                org.assertj.core.api.Assertions.assertThat(stringCell3Value).contains("Woods");
            }
        }
    }

    @Test
    void csvParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("csv/teachers.csv")) {
             CSVReader reader;
            if (stream != null) {
                reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                List<String[]> content = reader.readAll();
                org.assertj.core.api.Assertions.assertThat(content).contains(
                        new String[]{"Name", "Surname"},
                        new String[]{"Stacy", "Woods"}
                );
            }
        }
    }

    @Test
    @Disabled
    void zipParsingTest() throws Exception {
        try (ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream("zip/sample-zip-file.zip")))) {
            ZipEntry entry;
            List<String> filenames = new ArrayList<>();
            while((entry = is.getNextEntry()) != null) {
                filenames.add(entry.getName());
            }
            org.assertj.core.api.Assertions.assertThat(filenames).contains("junit-user-guide-5.8.2.pdf");
            org.assertj.core.api.Assertions.assertThat(filenames).contains("sample-xlsx-file.xlsx");
        }
    }

    @Test
    void jsonParsingTest() throws Exception {
        Person stacyW = new Person("Stacy", "Woods");

        // create json from list of objects
        ObjectMapper mapperWrite = new ObjectMapper();
        File jsonFile = new File(BaseTests.getResourcesDir() + "jsons/sample.json");

        List<Person> listOfPersons = new ArrayList<>();
        listOfPersons.add(new Person("Dmitrii", "Tuchs"));
        listOfPersons.add(stacyW);
        listOfPersons.add(new Person("Phillip", "Morris"));

        JsonNode personsJson = mapperWrite.readTree(new Gson().toJson(listOfPersons)); //parse the String or do what you already are doing to deserialize the JSON
        ObjectNode preparedJson = mapperWrite.createObjectNode(); //the object with the "data" array
        preparedJson.putPOJO("data", personsJson);
        mapperWrite.writeValue(jsonFile, preparedJson);

        // assert that json contains concrete person
        ObjectMapper mapperRead = new ObjectMapper();
        try (InputStream stream = cl.getResourceAsStream("jsons/sample.json")) {
            if (stream != null) {
                JsonNode jsonNode = mapperRead.readTree(stream);
                JsonNode data = jsonNode.get("data");
                Person[] persons = mapperRead.readValue(data.toString(), Person[].class);
                Assertions.assertTrue(Arrays.asList(persons).contains(stacyW));
                Assertions.assertTrue(Arrays.stream(persons).anyMatch(p -> p.equals(stacyW)));
            }
        }
    }
}
