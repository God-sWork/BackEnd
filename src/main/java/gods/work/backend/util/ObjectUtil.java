package gods.work.backend.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class ObjectUtil {

    public static void updateObjectProperty(Object updateObject, Object existingObject) {
        BeanUtils.copyProperties(updateObject, existingObject, getNullAndEmptyPropertyNames(updateObject));
    }

    public static  String[] getNullAndEmptyPropertyNames(Object source) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
        return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    Object propertyValue = beanWrapper.getPropertyValue(propertyName);
                    return propertyValue == null || (propertyValue instanceof String && StringUtils.isEmpty((String) propertyValue));
                })
                .toArray(String[]::new);
    }
}
