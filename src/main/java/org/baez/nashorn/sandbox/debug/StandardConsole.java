package org.baez.nashorn.sandbox.debug;

import java.io.PrintStream;

/**
 * console标准实现
 *
 * @author baez
 * @date 2020-01-05 23:02
 */
public class StandardConsole implements Console {

    private final PrintStream LOGGER = System.out;

    @Override
    public void debug(Object... obj) {
        LOGGER.println(obj);
    }

    @Override
    public void debug(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.println(message);
    }

    @Override
    public void log(Object... obj) {
        LOGGER.println(obj);
    }

    @Override
    public void log(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.println(message);
    }

    @Override
    public void info(Object... obj) {
        LOGGER.println(obj);
    }

    @Override
    public void info(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.println(message);
    }

    @Override
    public void error(Object... obj) {
        LOGGER.println(obj);
    }

    @Override
    public void error(String msg, Object... obj) {
        String message = String.format(msg, obj);
        LOGGER.println(message);
    }
}
