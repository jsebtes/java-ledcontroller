package fr.jstessier.ledcontroller.services;

import fr.jstessier.ledcontroller.models.LedConfiguration;
import fr.jstessier.ledcontroller.models.LedRgb;
import fr.jstessier.ledcontroller.models.LedRgbGroup;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Validated
public interface LedControllerService {

    List<LedRgb> getLeds(@Size(min = 0, max = 20) @Min(0) @Max(20) Integer ...ids);

    Optional<LedRgb> getLed(@NotNull @Min(0) @Max(20) Integer id);

    LedRgb createLed(@NotNull @Valid LedRgb ledRgb) throws RFShowControlException;

    LedRgb updateLed(@NotNull @Valid LedRgb ledRgb) throws RFShowControlException;

    Optional<LedRgb> deleteLed(@NotNull @Min(0) @Max(20) Integer id) throws RFShowControlException;

    //

    List<LedRgbGroup> getGroups(@Size(min = 0, max = 20) @Min(0) @Max(20) Integer ...ids);

    Optional<LedRgbGroup> getGroup(@NotNull @Min(0) @Max(20) Integer id);

    LedRgbGroup createGroup(@NotNull @Valid LedRgbGroup ledRgbGroup) throws RFShowControlException;

    LedRgbGroup updateGroup(@NotNull @Valid LedRgbGroup ledRgbGroup) throws RFShowControlException;

    Optional<LedRgbGroup> deleteGroup(@NotNull @Min(0) @Max(20) Integer id) throws RFShowControlException;

    //

    void clearConfiguration() throws RFShowControlException;

    void loadLedConfiguration(@NotNull @Valid LedConfiguration ledConfiguration) throws RFShowControlException;

    void updateAllLedChannelValues() throws RFShowControlException;

}
