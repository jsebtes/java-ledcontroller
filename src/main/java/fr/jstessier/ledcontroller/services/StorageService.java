package fr.jstessier.ledcontroller.services;

import fr.jstessier.ledcontroller.models.LedConfiguration;

import java.io.IOException;

public interface StorageService {

    void storeLedConfiguration(LedConfiguration ledConfiguration) throws IOException;

    LedConfiguration readLedConfiguration() throws IOException;

}
