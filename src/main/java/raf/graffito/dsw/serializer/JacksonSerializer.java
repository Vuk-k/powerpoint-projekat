package raf.graffito.dsw.serializer;

import com.fasterxml.jackson.databind.*;
import raf.graffito.dsw.model.mts.Project;

import java.io.File;
import java.io.IOException;

public class JacksonSerializer implements Serializer{

    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        // Inicijalizacija Jackson ObjectMapper-a
        this.objectMapper = new ObjectMapper();
        // Opcionalno: formatiranje izlaza (lepši JSON)
        this.objectMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Project loadProject(File file) {
        try {
            // Deserijalizacija: čita JSON iz fajla i mapira ga u Project objekat
            return objectMapper.readValue(file, Project.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveProject(Project project, File file) {
        try {
            objectMapper.writeValue(file, project);
            System.out.println("Projekat je sačuvan u: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
