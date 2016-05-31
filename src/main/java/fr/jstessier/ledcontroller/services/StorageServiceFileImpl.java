package fr.jstessier.ledcontroller.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.jstessier.ledcontroller.models.LedConfiguration;

import java.io.File;
import java.io.IOException;

public class StorageServiceFileImpl implements StorageService {

    private final ObjectMapper mapper;

    private final String filePath;

    public StorageServiceFileImpl(final String filePath) {
        this(filePath, new ObjectMapper());
    }

    public StorageServiceFileImpl(final String filePath, final ObjectMapper mapper) {
        this.mapper = mapper;
        this.filePath = filePath;
    }

    @Override
    public void storeLedConfiguration(final LedConfiguration ledConfiguration) throws IOException {
        mapper.writeValue(new File(filePath), ledConfiguration);
    }

    @Override
    public LedConfiguration readLedConfiguration() throws IOException {
        return mapper.readValue(new File(filePath), LedConfiguration.class);
    }

}
