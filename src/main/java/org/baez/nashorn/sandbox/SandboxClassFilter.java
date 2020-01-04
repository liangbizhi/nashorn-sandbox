package org.baez.nashorn.sandbox;

import jdk.nashorn.api.scripting.ClassFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * 沙箱中Java类访问过滤器
 *
 * @author baez
 * @date 2020-01-04 21:03
 */
public class SandboxClassFilter implements ClassFilter {

    private final Set<Class<?>> allowedClassSet;

    private final Set<String> allowedClassNameSet;

    public boolean exposeToScripts(String className) {
        return allowedClassNameSet.contains(className);
    }

    /**
     * 添加允许访问的Java类
     * @param clazz
     */
    public void add(final Class<?> clazz) {
        allowedClassSet.add(clazz);
        allowedClassNameSet.add(clazz.getName());
    }

    public SandboxClassFilter() {
        allowedClassSet = new HashSet<Class<?>>();
        allowedClassNameSet = new HashSet<String>();
    }
}
