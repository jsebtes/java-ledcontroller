package fr.jstessier.ledcontroller;

import fr.jstessier.ledcontroller.mocks.RFShowControlControllerMockImpl;
import fr.jstessier.ledcontroller.models.ColorRgb;
import fr.jstessier.ledcontroller.models.LedRgb;
import fr.jstessier.ledcontroller.models.LedRgbBuilder;
import fr.jstessier.ledcontroller.services.LedControllerService;
import fr.jstessier.ledcontroller.services.LedControllerServiceImpl;
import fr.jstessier.ledcontroller.services.StorageService;
import fr.jstessier.ledcontroller.services.StorageServiceFileImpl;
import fr.jstessier.patch.PatchProcessor;
import fr.jstessier.rf24.exceptions.RF24Exception;
import fr.jstessier.rf24.hardware.RF24Hardware;
import fr.jstessier.rf24.hardware.RF24HardwarePi4j;
import fr.jstessier.rfshowcontrol.RFShowControlController;
import fr.jstessier.rfshowcontrol.RFShowControlControllerImpl;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import fr.jstessier.rfshowcontrol.RFShowControlRF24Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;
import java.io.IOException;

@SpringBootApplication
@PropertySource(value = "file:ledcontroller.properties")
public class Application {

    @Autowired
    Environment environment;

    /*@Bean
    RFShowControlController rFShowControlController() throws RFShowControlException {

        final byte spiChannel = environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.hardware.spiChannel", Byte.class);
        final byte gpioPinChipEnable = environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.hardware.gpioPinChipEnable", Byte.class);

        final byte rfChannel = environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.rf.rfChannel", Byte.class);
        final byte[] pipeAddress = hexToBytes(environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.rf.pipeAddress").split(","));
        final byte numberOfChannel = environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.rf.numberOfChannel", Byte.class));

        RF24Hardware rf24Hardware;
        try {
            rf24Hardware = new RF24HardwarePi4j(spiChannel, gpioPinChipEnable);
        } catch (RF24Exception e) {
            throw new RFShowControlException("An error occured during RF24 Hardware instanciation", e);
        }
        final RFShowControlController controller = new RFShowControlControllerImpl(rf24Hardware, numberOfChannel);
        controller.start(rfChannel, pipeAddress, RFShowControlRF24Adapter.Mode.TX);
        return controller;
    }*/

    @Bean
    RFShowControlController rfShowControlControllerMock() {
        return new RFShowControlControllerMockImpl(environment.getRequiredProperty("fr.jstessier.ledcontroller.configuration.rf.numberOfChannel", Byte.class));
    }

    @Bean
    StorageService storageService() {
        return new StorageServiceFileImpl("ledconfiguration.json");
    }

    @Bean
    @Autowired
    LedControllerService ledControllerService(RFShowControlController rfShowControlController,
            StorageService storageService) throws RFShowControlException, IOException {
        final LedControllerService ledControllerService = new LedControllerServiceImpl(rfShowControlController);
        ledControllerService.loadLeds(storageService.readLedConfiguration().getLedRgbs());
        return ledControllerService;
    }

    @Bean
    @Autowired
    PatchProcessor patchProcessor(Validator validator) {
        return new PatchProcessor(validator);
    }

    /**
     * A Validator for Bean Validation.
     *
     * @return
     */
    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Activate processor for Bean Validation for @Validated classes.
     *
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    private static byte[] hexToBytes(String[] strings) {
        byte[] result = new byte[strings.length];
        for (int index = 0; index < strings.length; index++) {
            result[index] = (byte) Integer.valueOf(strings[index].trim().substring(2), 16).intValue();
        }
        return result;
    }

}