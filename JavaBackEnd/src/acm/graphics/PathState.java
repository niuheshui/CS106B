package acm.graphics;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;

class PathState implements Serializable {
   double cx;
   double cy;
   double sx;
   double sy;
   Polygon region;
   Color fillColor;
}
