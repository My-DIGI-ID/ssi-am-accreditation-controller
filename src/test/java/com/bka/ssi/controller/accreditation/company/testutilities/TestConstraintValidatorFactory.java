package com.bka.ssi.controller.accreditation.company.testutilities;

import com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

public class TestConstraintValidatorFactory implements ConstraintValidatorFactory {

    public final static String i18n_ERROR_MESSAGE = "message";

    private I18nMessageBuilder i18nMessageBuilder;

    public void init() {
        i18nMessageBuilder = Mockito.mock(I18nMessageBuilder.class);
        Mockito.when(i18nMessageBuilder.create(Mockito.anyString())).thenReturn(i18n_ERROR_MESSAGE);
    }

    public void init(I18nMessageBuilder builder) {
        i18nMessageBuilder = builder;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> aClass) {
        Class[] type =
            {com.bka.ssi.controller.accreditation.company.aop.utilities.I18nMessageBuilder.class};
        Object[] obj = {i18nMessageBuilder};

        Constructor<T> cons = null;
        try {
            cons = aClass.getConstructor(type);
        } catch (NoSuchMethodException e) {
            try {
                cons = aClass.getConstructor();
                return cons.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
                Assertions.fail();
            }
        }

        try {
            return cons.newInstance(obj);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        return null;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {

    }
}
