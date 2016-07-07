package fr.jstessier.ledcontroller.services;

import fr.jstessier.ledcontroller.exceptions.GroupException;
import fr.jstessier.ledcontroller.exceptions.ResourceIdMustBeEmptyException;
import fr.jstessier.ledcontroller.exceptions.ResourceNotFoundException;
import fr.jstessier.ledcontroller.models.*;
import fr.jstessier.rfshowcontrol.RFShowControlController;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LedControllerServiceImpl implements LedControllerService {

    private final Map<Integer, LedRgb> leds;

    private final Map<Integer, LedRgbGroup> groups;

    private final AtomicInteger ledIdGenerator;

    private final AtomicInteger groupIdGenerator;

    private final RFShowControlController rfShowControlController;

    public LedControllerServiceImpl(RFShowControlController rfShowControlController) throws RFShowControlException {
        this.leds = new HashMap<>();
        this.groups = new HashMap<>();
        this.ledIdGenerator = new AtomicInteger(1);
        this.groupIdGenerator = new AtomicInteger(1);
        this.rfShowControlController = rfShowControlController;
        this.rfShowControlController.resetAndFlushChannelValues();
    }

    @Override
    synchronized public void clearConfiguration() throws RFShowControlException {
        leds.clear();
        groups.clear();
        ledIdGenerator.set(1);
        groupIdGenerator.set(1);
        rfShowControlController.resetAndFlushChannelValues();
        updateAllLedChannelValues();
    }

    void checkLedConfiguration(final LedConfiguration ledConfiguration) {

        final List<Integer> ledIds = ledConfiguration.getGroups().stream()
                .flatMap(group -> group.getLedRgbIds().stream())
                .collect(Collectors.toList());

        ledIds.stream().distinct().collect(Collectors.toList());

        // TODO
    }

    @Override
    synchronized public void loadLedConfiguration(final LedConfiguration ledConfiguration) throws RFShowControlException {
        checkLedConfiguration(ledConfiguration);
        try {
            leds.clear();
            groups.clear();
            leds.putAll(ledConfiguration.getLedRgbs().stream()
                    .collect(Collectors.toMap(ledRgb -> ledRgb.getId(), Function.identity())));
            groups.putAll(ledConfiguration.getGroups().stream()
                    .collect(Collectors.toMap(ledRgbGroup -> ledRgbGroup.getId(), Function.identity())));
        }
        finally {
            // Update ID generator with the next free id => max id value in the map + 1
            ledIdGenerator.set(leds.keySet().stream()
                    .max((id1, id2) -> id1.compareTo(id2))
                    .map(idMax -> idMax + 1)
                    .orElse(1));
            groupIdGenerator.set(leds.keySet().stream()
                    .max((id1, id2) -> id1.compareTo(id2))
                    .map(idMax -> idMax + 1)
                    .orElse(1));
            // Send all led values
            updateAllLedChannelValues();
        }
    }

    @Override
    public List<LedRgb> getLeds(final Integer ...ids) {
        if (ids == null || ids.length == 0) {
            return leds.values().stream()
                    .sorted((o1, o2) -> ObjectUtils.compare(o1.getId(), o2.getId()))
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
        final int id = ledIdGenerator.getAndIncrement();
        final LedRgb newLed = leds.put(id, LedRgbBuilder.aLedRgb().from(ledRgb).withId(id).build());
        updateOneLedChannelValues(newLed.getColorRgb(), newLed.getAddress());
        return newLed;
    }

    @Override
    public Optional<LedRgb> getLed(final Integer id) {
        return Optional.ofNullable(leds.get(id));
    }

    @Override
    public LedRgb updateLed(final LedRgb ledRgb) throws RFShowControlException {
        // TODO checkLedNotInAGroup(ledRgb.getId());
        return updateLed(ledRgb, true);
    }

    private void checkLedNotInAGroup(final Integer ledRgbId) {
        final Optional<Integer> idInAGroup = groups.values().stream()
                .flatMap(group -> group.getLedRgbIds().stream())
                .filter(id -> id.equals(ledRgbId))
                .findAny();
        if (idInAGroup.isPresent()) {
            throw new GroupException(String.format("Led id=%s is in a group"));
        }
    }

    private LedRgb updateLed(final LedRgb ledRgb, final boolean flushChannelValues) throws RFShowControlException {
        if (!leds.containsKey(ledRgb.getId())) {
            throw new ResourceNotFoundException(ledRgb.getId().toString(), LedRgb.class.getName());
        }
        final LedRgb before = leds.put(ledRgb.getId(), ledRgb);
        if (!Objects.equals(before.getAddress(), ledRgb.getAddress())) {
            updateOneLedChannelValues(new ColorRgb(0, 0, 0, 0), before.getAddress(), false);
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress(), flushChannelValues);
        }
        else if (!Objects.equals(before.getColorRgb(), ledRgb.getColorRgb())) {
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress(), flushChannelValues);
        }
        return before;
    }

    @Override
    public Optional<LedRgb> deleteLed(final Integer id) throws RFShowControlException {
        checkLedNotInAGroup(id);
        final LedRgb deletedLed = leds.remove(id);
        if (deletedLed != null) {
            updateOneLedChannelValues(null, deletedLed.getAddress());
        }
        return Optional.ofNullable(deletedLed);
    }

    private void updateOneLedChannelValues(ColorRgb colorRgb, Integer address) throws RFShowControlException {
        updateOneLedChannelValues(colorRgb, address, true);
    }

    private void updateOneLedChannelValues(ColorRgb colorRgb, Integer address, boolean flushChannelValues) throws RFShowControlException {
        rfShowControlController.updateChannelValues(
                Optional.ofNullable(colorRgb).orElse(new ColorRgb(0, 0, 0, 0)).toRgbArray(),
                address);
        if (flushChannelValues) {
            rfShowControlController.flushChannelValues();
        }
    }

    public void updateAllLedChannelValues() throws RFShowControlException {
        rfShowControlController.resetChannelValues();
        for (LedRgb ledRgb : leds.values()) {
            updateOneLedChannelValues(ledRgb.getColorRgb(), ledRgb.getAddress(), false);
        }
        rfShowControlController.flushChannelValues();
    }


    @Override
    public List<LedRgbGroup> getGroups(final Integer ...ids) {
        if (ids == null || ids.length == 0) {
            return groups.values().stream()
                    .sorted((o1, o2) -> ObjectUtils.compare(o1.getId(), o2.getId()))
                    .collect(Collectors.toList());
        }
        else {
            return groups.values().stream()
                    .filter(led -> Arrays.asList(ids).contains(led.getId()))
                    .sorted((o1,o2) -> ObjectUtils.compare(o1.getId(), o2.getId()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public LedRgbGroup createGroup(final LedRgbGroup ledRgbGroup) throws RFShowControlException {
        if (ledRgbGroup.getId() != null) {
            throw new ResourceIdMustBeEmptyException(ledRgbGroup.getId().toString(), LedRgb.class.getName());
        }
        final int id = groupIdGenerator.getAndIncrement();
        return groups.put(id, LedRgbGroupBuilder.aLedRgbGroup().from(ledRgbGroup).withId(id).build());
    }

    @Override
    public Optional<LedRgbGroup> getGroup(final Integer id) {
        return Optional.ofNullable(groups.get(id));
    }

    @Override
    public LedRgbGroup updateGroup(final LedRgbGroup ledRgbGroup) throws RFShowControlException {
        final LedRgbGroup before = groups.get(ledRgbGroup.getId());
        if (before == null) {
            throw new ResourceNotFoundException(ledRgbGroup.getId().toString(), LedRgbGroup.class.getName());
        }
        if (!Objects.equals(before.getLedRgbIds(), ledRgbGroup.getLedRgbIds())) {
            checkLedsExists(ledRgbGroup.getLedRgbIds());
            checkLedsNotTwoFold(ledRgbGroup.getLedRgbIds());
            checkLedsNotInOtherGroup(ledRgbGroup.getLedRgbIds());
        }
        if (!Objects.equals(before.getColorRgb(), ledRgbGroup.getColorRgb())
                || !Objects.equals(before.getLedRgbIds(), ledRgbGroup.getLedRgbIds())) {
            for (Integer ledId : ledRgbGroup.getLedRgbIds()) {
                final Optional<LedRgb> ledRgb = getLed(ledId);
                if (ledRgb.isPresent()) {
                    updateLed(LedRgbBuilder.aLedRgb().from(ledRgb.get()).but().withColorRgb(ledRgbGroup.getColorRgb()).build(), false);
                }
            }
            rfShowControlController.flushChannelValues();
        }
        groups.put(ledRgbGroup.getId(), ledRgbGroup);
        return before;
    }

    private void checkLedsExists(final List<Integer> ledRgbIds) {
        final long nbExistingLeds = Optional.ofNullable(ledRgbIds).orElse(Collections.emptyList()).stream()
                .filter(id -> leds.containsKey(id))
                .count();
        if (nbExistingLeds != Optional.ofNullable(ledRgbIds).map(List::size).orElse(0)) {
            throw new GroupException("Led id must exist");
        }
    }

    private void checkLedsNotTwoFold(final List<Integer> ledRgbIds) {
        final long nbDistinctValue = Optional.ofNullable(ledRgbIds).orElse(Collections.emptyList()).stream()
                .distinct()
                .count();
        if (nbDistinctValue != Optional.ofNullable(ledRgbIds).map(List::size).orElse(0)) {
            throw new GroupException("Led id cannot be two fold");
        }
    }

    private void checkLedsNotInOtherGroup(final List<Integer> ledRgbIds) {
        final List<Integer> idsInOtherGroup = groups.values().stream()
                .flatMap(group -> group.getLedRgbIds().stream())
                .filter(ledId -> Optional.ofNullable(ledRgbIds).orElse(Collections.emptyList()).contains(ledId))
                .collect(Collectors.toList());
        if (idsInOtherGroup.size() > 0) {
            throw new GroupException("Led id cannot be in an other group");
        }
    }

    @Override
    public Optional<LedRgbGroup> deleteGroup(final Integer id) throws RFShowControlException {
        return Optional.ofNullable(groups.remove(id));
    }

}
