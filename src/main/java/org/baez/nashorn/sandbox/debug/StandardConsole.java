package org.baez.nashorn.sandbox.debug;

/**
 * console标准实现
 *
 * @author baez
 * @date 2020-01-05 23:02
 */
public class StandardConsole implements Console {

    @Override
    public void debug(Object... obj) {

    }

    @Override
    public void debug(String msg, Object... obj) {
        String message = String.format(msg, obj);
    }

    @Override
    public void log(Object... obj) {

    }

    @Override
    public void log(String msg, Object... obj) {

    }

    @Override
    public void info(Object... obj) {

    }

    @Override
    public void info(String msg, Object... obj) {

    }

    @Override
    public void error(Object... obj) {

    }

    @Override
    public void error(String msg, Object... obj) {

    }
}
