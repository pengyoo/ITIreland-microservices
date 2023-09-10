package works.itireland.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;

public class BeanCopyUtils {

    /**
     * Copies properties from one object to another
     * @destination
     * @return
     */
    public static void copyNonNullProperties(Object input, Object entity) {
        BeanUtils.copyProperties(input, entity, getNullPropertyNames(input));
    }


    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors()).map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null
                        || "0.0".equals(String.valueOf(wrapper.getPropertyValue(propertyName)))
                        || "".equals(String.valueOf(wrapper.getPropertyValue(propertyName))))
                .toArray(String[]::new);
    }
}