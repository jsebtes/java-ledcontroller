package fr.jstessier.ledcontroller.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.jstessier.patch.PatchProcessor;
import fr.jstessier.patch.exceptions.PatchException;
import fr.jstessier.patch.exceptions.PatchInvalidPathException;
import fr.jstessier.patch.exceptions.PatchInvalidValueException;
import fr.jstessier.patch.exceptions.PatchMissingNodeException;
import fr.jstessier.ledcontroller.models.LedRgb;
import fr.jstessier.patch.models.Patch;
import fr.jstessier.ledcontroller.services.LedControllerService;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/led-controller")
public class LedControllerRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LedControllerRest.class);

    @Autowired
    private LedControllerService ledControllerService;

    @Autowired
    private PatchProcessor patchProcessor;

    @RequestMapping(value = "/leds",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LedRgb>> getLeds() {
        return ResponseEntity.ok(ledControllerService.getLeds());
    }

    @RequestMapping(value = "/leds",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LedRgb> createLed(@RequestBody() @NotNull @Valid LedRgb ledRgb) throws RFShowControlException {
         return ResponseEntity.ok(ledControllerService.createLed(ledRgb));
    }

    @RequestMapping(value = "/leds/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LedRgb> getLed(@PathVariable("id") @NotNull @Min(1) @Max(Integer.MAX_VALUE) Integer id) {
        return ledControllerService.readLed(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/leds/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateLed(@PathVariable("id") @NotNull @Min(1) @Max(Integer.MAX_VALUE) Integer id,
            @RequestBody() @NotNull @Valid LedRgb ledRgb) throws RFShowControlException {
        if (id.intValue() == ledRgb.getId().intValue()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ledControllerService.updateLed(ledRgb);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/leds/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLed(@PathVariable("id") @NotNull @Min(1) @Max(Integer.MAX_VALUE) Integer id) throws RFShowControlException {
        return ledControllerService.deleteLed(id)
                .map(led -> new ResponseEntity(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/leds/{id}",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patchLed(@PathVariable("id") @NotNull @Min(1) @Max(Integer.MAX_VALUE) Integer id,
            @RequestBody @NotNull @Size(min = 1, max = 10) @Valid List<Patch> patches) throws RFShowControlException {
        final Optional<LedRgb> ledRgb = ledControllerService.readLed(id);
        if (!ledRgb.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        final LedRgb newLedRgb = patchProcessor.processPatches(ledRgb.get(), LedRgb.class, patches, true);
        ledControllerService.updateLed(newLedRgb);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
