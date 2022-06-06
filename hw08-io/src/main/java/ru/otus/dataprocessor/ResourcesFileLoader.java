package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.JsonStructure;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() {
        try (var inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            return Arrays.asList(mapper.readValue(inputStream, Measurement[].class));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
