package fr.jstessier.ledcontroller.services;

import fr.jstessier.ledcontroller.models.LedRgb;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface LedControllerService {

    List<LedRgb> getLeds(@NotNull @Min(0) @Max(20) Integer...ids);

    LedRgb createLed(@NotNull @Valid LedRgb ledRgb) throws RFShowControlException;

    Optional<LedRgb> readLed(@NotNull Integer id);

    LedRgb updateLed(@NotNull @Valid LedRgb ledRgb) throws RFShowControlException;

    Optional<LedRgb> deleteLed(@NotNull Integer id) throws RFShowControlException;

    void clearLeds() throws RFShowControlException;

    void loadLeds(@NotNull List<LedRgb> ledRgbs) throws RFShowControlException;

    void updateAllLedChannelValues() throws RFShowControlException;

}
