package com.example.student.utils;

import org.locationtech.jts.geom.GeometryFactory;

public class GeometryFactoryUtil {
    private static GeometryFactory factory;

    private GeometryFactoryUtil() {}

    private static GeometryFactory instantiateFactory(){
        return new GeometryFactory();
    }

    public static GeometryFactory getFactoryInstance(){
        if(factory == null){
            factory = instantiateFactory();
        }
        return factory;
    }
}
