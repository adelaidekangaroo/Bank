package org.sberbank.simonov.bank.web.reflect.mvc_controller;

import java.util.Map;

public interface ApplicationContext {

    Map<Class<?>, Object> getControllers();

    void initializeContext() throws Exception;
}
