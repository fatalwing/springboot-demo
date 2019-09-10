package com.townmc.boot.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class ObjectUtil {

    /**
     * 获取空属性==>数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static boolean contain(Object[] arr, Object obj) {
        for (Object o : arr) {
            if (o == obj) return true;
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 属性拷贝
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyProperties(S source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return target;
    }
}
