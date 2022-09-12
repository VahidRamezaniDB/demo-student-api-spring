package com.example.student.service;

import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

@Service
public class GeometryShapesService {

    private final GeometryFactory factory;

    public GeometryShapesService(GeometryFactory factory) {
        this.factory = factory;
    }

    public Geometry createLine(double x1, double y1, double x2, double y2){
        Coordinate[] coordinates = {new Coordinate(x1,y1), new Coordinate(x2,y2)};
        return factory.createLineString(coordinates);
    }

    public Geometry createPolygon(double[] x, double[] y){
        if(x.length != y.length){
            throw new IllegalArgumentException("Arrays of the lengths (x) and width (y) must be the same size.");
        }
        Coordinate[] coordinates = new Coordinate[x.length];
        for(int i=0;i<x.length;i++){
            coordinates[i] = new Coordinate(x[i],y[i]);
        }
        return factory.createPolygon(coordinates);
    }

    public boolean doesLineCrossPolygon(LineString line, Polygon polygon){
        return line.crosses(polygon);
    }

    public boolean doesLineTraversePolygon(LineString line, Polygon polygon){
        return line.intersection(polygon.getBoundary()).getNumPoints() % 2 == 0;
    }
}
