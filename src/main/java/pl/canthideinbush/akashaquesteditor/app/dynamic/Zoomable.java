package pl.canthideinbush.akashaquesteditor.app.dynamic;

import java.awt.*;

public interface Zoomable {

   void setZoom(double zoom);

   double getZoom();

   default Point convert(Point point) {
      return new Point((int) (point.x / getZoom()), (int) (point.y / getZoom()));
   }

}
