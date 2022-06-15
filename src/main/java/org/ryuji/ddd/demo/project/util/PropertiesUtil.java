package org.ryuji.ddd.demo.project.util;

import java.util.*;

public class PropertiesUtil {
    public static Properties mapToProperties(Map<String, Object> map) {
        Properties p = new Properties();
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            p.put(entry.getKey(), entry.getValue());
        }
        return p;
    }


    public static Map<String, Object> propertiesToMap(Properties props) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        Enumeration<Object> e = props.keys();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            hm.put(s, props.getProperty(s));
        }
        return hm;
    }
}
