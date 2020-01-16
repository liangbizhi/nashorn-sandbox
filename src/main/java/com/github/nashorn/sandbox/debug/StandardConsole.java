package com.github.nashorn.sandbox.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * console标准实现
 *
 * @author baez
 * @date 2020-01-05 23:02
 */
public class StandardConsole implements Console {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardConsole.class);

    @Override
    public void debug(Object... obj) {
        LOGGER.debug("{}", obj);
    }

    @Override
    public void debug(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.debug(message);
    }

    @Override
    public void log(Object... obj) {
        info(obj);
    }

    @Override
    public void log(String msg, Object... obj) {
        info(msg, obj);
    }

    @Override
    public void info(Object... obj) {
        LOGGER.info("{}", obj);
    }

    @Override
    public void info(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.info(message);
    }

    @Override
    public void error(Object... obj) {
        LOGGER.error("{}", obj);
    }

    @Override
    public void error(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.error(message);
    }
}
