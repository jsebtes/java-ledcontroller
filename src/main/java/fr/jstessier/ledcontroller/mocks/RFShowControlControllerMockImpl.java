package fr.jstessier.ledcontroller.mocks;

import fr.jstessier.rf24.hardware.RF24Hardware;
import fr.jstessier.rfshowcontrol.RFShowControlController;
import fr.jstessier.rfshowcontrol.RFShowControlException;
import fr.jstessier.rfshowcontrol.RFShowControlRF24Adapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RFShowControlControllerMockImpl implements RFShowControlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RFShowControlControllerMockImpl.class);

    private final byte[] channelValues;

    public RFShowControlControllerMockImpl(final int numberOfChannel) {
        channelValues = new byte[numberOfChannel];
    }

    @Override
    public RFShowControlController start(byte rfChannel, byte[] pipeAddress, RFShowControlRF24Adapter.Mode mode) throws RFShowControlException {
        return this;
    }

    @Override
    public RFShowControlController resetChannelValues() throws RFShowControlException {
        LOGGER.info("call resetChannelValues : channelValues={}", Arrays.toString(channelValues));
        Arrays.fill(channelValues, (byte) 0);
        return this;
    }

    @Override
    public RFShowControlController resetAndFlushChannelValues() throws RFShowControlException {
        return resetChannelValues().flushChannelValues();
    }

    @Override
    public byte[] getChannelValues() {
        LOGGER.info("call getChannelValues : channelValues={}", Arrays.toString(channelValues));
        return channelValues;
    }

    @Override
    public RFShowControlController flushChannelValues() throws RFShowControlException {
        LOGGER.info("call flushChannelValues : channelValues={}", Arrays.toString(channelValues));
        return this;
    }

    @Override
    public RFShowControlController updateChannelValue(byte newChannelValue, int channelNumber) {
        if (channelNumber < 1 || channelNumber > channelValues.length) {
            throw new IllegalArgumentException("channelNumber must be in range [1-" + channelValues.length + "]");
        }
        channelValues[channelNumber - 1] = newChannelValue;
        LOGGER.info("call updateChannelValue : updateChannelValue={}", Arrays.toString(channelValues));
        return this;
    }

    @Override
    public RFShowControlController updateChannelValues(byte[] newChannelValues, int startChannelNumber) {
        if (newChannelValues == null || newChannelValues.length == 0) {
            return this;
        }
        else if (startChannelNumber < 1 || startChannelNumber > channelValues.length) {
            throw new IllegalArgumentException("startChannelNumber must be in range [1-" + channelValues.length + "]");
        }
        else if ((startChannelNumber - 1) > (channelValues.length - newChannelValues.length)) {
            throw new IllegalArgumentException("startChannelNumber + newChannelValues.length must not exceed " + channelValues.length);
        }
        System.arraycopy(newChannelValues, 0, channelValues, startChannelNumber - 1, newChannelValues.length);
        LOGGER.info("call updateChannelValues : updateChannelValues={}", Arrays.toString(channelValues));
        return this;
    }

    @Override
    public RFShowControlController updateChannelValues(byte[] newChannelValues) {
        if (newChannelValues.length != channelValues.length) {
            throw new IllegalArgumentException("newChannelValues length must be equal to " + channelValues.length);
        }
        System.arraycopy(newChannelValues, 0, channelValues, 0, channelValues.length);
        LOGGER.info("call updateChannelValues : updateChannelValues={}", Arrays.toString(channelValues));
        return this;
    }

    @Override
    public RFShowControlController updateAndFlushChannelValues(byte[] newChannelValues) throws RFShowControlException {
        return null;
    }

}
