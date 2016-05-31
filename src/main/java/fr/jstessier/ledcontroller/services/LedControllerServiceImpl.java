package fr.jstessier.ledcontroller.services;

import fr.jstessier.ledcontroller.exceptions.ResourceIdMustBeEmptyException;
import fr.jstessier.ledcontroller.exceptions.ResourceNotFoundException;
import fr.jstessier.ledcontroller.models.ColorRgb;
import fr.jstessier.ledcontroller.models.LedRgb;
import fr.jstessier.ledcontroller.models.LedRgbBuilder;
import fr.jstessier.rfshowcontrol.RFShowControlController;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LedControllerServiceImpl implements LedControllerService {

    private final Map<Integer, LedRgb> leds;

    private final AtomicInteger idGenerator;

    private final RFShowControlController rfShowControlController;

    public LedControllerServiceImpl(RFShowControlController rfShowControlController) throws RFShowControlException {
        this.leds = new HashMap<>();
        this.idGenerator = new AtomicInteger(1);
        this.rfShowControlController = rfShowControlController;
        this.rfShowControlController.resetChannelValues();
    }

    synchronized public void clearLeds() throws RFShowControlException {
        leds.clear();
        idGenerator.set(1);
        rfShowControlController.resetChannelValues();
        updateAllLedChannelValues();
    }

    synchronized public void loadLeds(final List<LedRgb> ledRgbs) throws RFShowControlException {
        try {
            leds.clear();
            leds.putAll(ledRgbs.stream().collect(Collectors.toMap(ledRgb -> ledRgb.getId(), Function.identity())));
        }
        finally {
            // Update ID generator with the next free id => max id value in the map + 1
            idGenerator.set(leds.keySet().stream()
                    .max((id1, id2) -> id1.compareTo(id2))
                    .map(idMax -> idMax + 1)
                    .orElse(1));
            // Send all led values
            rfShowControlController.resetChannelValues();
            updateAllLedChannelValues();
        }
    }

    @Override
    public List<LedRgb> getLeds(final Integer ...ids) {
        if (ids == null || ids.length == 0) {
            return leds.values().stream()
                    .sorted((o1,o2) -> ObjectUtils.compare(o1.getId(), o2.getId()))
                    .collect(Collectors.toList());
        }
        else {
            return leds.values().stream()
                    .filter(led -> Arrays.asList(ids).contains(led.getId()))
                    .sorted((o1,o2) -> ObjectUtils.compare(o1.getId(), o2.getId()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public LedRgb createLed(final LedRgb ledRgb) throws RFShowControlException {
        if (ledRgb.getId() != null) {
            throw new ResourceIdMustBeEmptyException(ledRgb.getId().toString(), LedRgb.class.getName());
        }
        int id = idGenerator.getAndIncrement();
        final LedRgb newLed = leds.put(id, LedRgbBuilder.aLedRgb().from(ledRgb).withId(id).build());
        updateOneLedChannelValues(newLed.getColorRgb(), newLed.getAddress());
        return newLed;
    }

    @Override
    public Optional<LedRgb> readLed(final Integer id) {
        return Optional.ofNullable(leds.get(id));
    }

    @Override
    public LedRgb updateLed(final LedRgb ledRgb) throws RFShowControlException {
        if (!leds.containsKey(ledRgb.getId())) {
            throw new ResourceNotFoundException(ledRgb.getId().toString(), LedRgb.class.getName());
        }
        final LedRgb before = leds.put(ledRgb.getId(), ledRgb);
        if (!Objects.equals(before.getAddress(), ledRgb.getAddress())) {
            updateOneLedChannelValues(null, before.getAddress(), false);
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress());
        }
        else if (!Objects.equals(before.getColorRgb(), ledRgb.getColorRgb())) {
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress());
        }
        return before;
    }

    @Override
    public Optional<LedRgb> deleteLed(final Integer id) throws RFShowControlException {
        final LedRgb deletedLed = leds.remove(id);
        if (deletedLed != null) {
            updateOneLedChannelValues(null, deletedLed.getAddress());
        }
        return Optional.ofNullable(deletedLed);
    }

    private void updateOneLedChannelValues(ColorRgb colorRgb, Integer address) throws RFShowControlException {
        updateOneLedChannelValues(colorRgb, address, true);
    }

    private void updateOneLedChannelValues(ColorRgb colorRgb, Integer address, boolean sendchannelValues) throws RFShowControlException {
        rfShowControlController.updateChannelValues(
                Optional.ofNullable(colorRgb).orElse(new ColorRgb(0, 0, 0)).toRgbArray(),
                address);
        if (sendchannelValues) {
            rfShowControlController.sendChannelValues();
        }
    }

    public void updateAllLedChannelValues() throws RFShowControlException {
        for (LedRgb ledRgb : leds.values()) {
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress(), false);
        }
        rfShowControlController.sendChannelValues();
    }

}
