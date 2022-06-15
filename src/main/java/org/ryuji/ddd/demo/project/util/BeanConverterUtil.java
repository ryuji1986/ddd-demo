package org.ryuji.ddd.demo.project.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;


@Log4j2
public class BeanConverterUtil {

    /**
     * Bean转换
     *
     * @param source 源对象
     * @param clz    目标对象类型
     * @param <T>    T
     * @return 转换后的对象
     */
    public static <T> T convert(Object source, Class<T> clz) {
        BeanCopier copier = BeanCopier.create(source.getClass(), clz, false);
        try {
            T target = clz.newInstance();
            copier.copy(source, target, null);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("对象转换异常:{}", e);
        }
        return null;
    }

    public static <T> List<T> batchConvert(List<?> sources, Class<T> clz) {
        List<T> targets = new ArrayList<>();
        for (Object source : sources) {
            T target = convert(source, clz);
            targets.add(target);
        }
        return targets;
    }
}
