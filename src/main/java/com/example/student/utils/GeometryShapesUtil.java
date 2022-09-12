package com.example.student.utils;

import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

@Service
public class GeometryShapesUtil {

    public static Geometry createLine(double x1, double y1, double x2, double y2){
        Coordinate[] coordinates = {new Coordinate(x1,y1), new Coordinate(x2,y2)};
        return GeometryFactoryUtil.getFactoryInstance().createLineString(coordinates);
    }

    public static Geometry createPolygon(double[] x, double[] y){
        if(x.length != y.length){
            throw new IllegalArgumentException("Arrays of the lengths (x) and width (y) must be the same size.");
        }
        Coordinate[] coordinates = new Coordinate[x.length];
        for(int i=0;i<x.length;i++){
            coordinates[i] = new Coordinate(x[i],y[i]);
        }
        return GeometryFactoryUtil.getFactoryInstance().createPolygon(coordinates);
    }

    public static boolean doesLineCrossPolygon(LineString line, Polygon polygon){
        return line.crosses(polygon);
    }

    public static boolean doesLineTraversePolygon(LineString line, Polygon polygon){
        return line.intersection(polygon.getBoundary()).getNumPoints() % 2 == 0;
    }
}
