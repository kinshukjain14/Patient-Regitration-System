package classes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.*;

public class PrintableDocument implements Printable {
  private final Component compent;
  
  public static void printComponent(Component c) {
    new PrintableDocument(c).print();
  }
  
  public PrintableDocument(Component compent) {
    this.compent = compent;
  }
  
  public void print() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(this);
    if(printJob.printDialog())
      try {
      printJob.print();
    }
    catch(PrinterException pe) {
      System.out.println("Error printing: " + pe);
    }
  }
  
   @Override
    public int print(Graphics g, PageFormat pf, int pageNumber)
                throws PrinterException {
            if (pageNumber > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            // Get the preferred size ofthe component...
            Dimension compSize = compent.getPreferredSize();
            // Make sure we size to the preferred size
            compent.setSize(compSize);
            // Get the the print size
            Dimension printSize = new Dimension();
            printSize.setSize(pf.getImageableWidth(), pf.getImageableHeight());

            // Calculate the scale factor
            double scaleFactor = getScaleFactorToFit(compSize, printSize);
            // Don't want to scale up, only want to scale down
            if (scaleFactor > 1d) {
                scaleFactor = 1d;
            }
            // Calcaulte the scaled size...
            double scaleWidth = compSize.width * scaleFactor;
            double scaleHeight = compSize.height * scaleFactor;

            // Create a clone of the graphics context.  This allows us to manipulate
            // the graphics context without begin worried about what effects
            // it might have once we're finished
            Graphics2D g2 = (Graphics2D) g.create();
            // Calculate the x/y position of the component, this will center
            // the result on the page if it can
            double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
            double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
            // Create a new AffineTransformation
            AffineTransform at = new AffineTransform();
            // Translate the offset to out "center" of page
            at.translate(x, y);
            // Set the scaling
            at.scale(scaleFactor, scaleFactor);
            // Apply the transformation
            g2.transform(at);
            // Print the component
            compent.printAll(g2);
            // Dispose of the graphics context, freeing up memory and discarding
            // our changes
            g2.dispose();

            compent.revalidate();
            return Printable.PAGE_EXISTS;
        }
       
    public static double getScaleFactorToFit(Dimension original, Dimension toFit) {

        double dScale = 1d;

        if (original != null && toFit != null) {

            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);

            dScale = Math.min(dScaleHeight, dScaleWidth);

        }

        return dScale;

    }

    public static double getScaleFactor(int iMasterSize, int iTargetSize) {

        double dScale = 1;
        if (iMasterSize > iTargetSize) {

            dScale = (double) iTargetSize / (double) iMasterSize;

        } else {

            dScale = (double) iTargetSize / (double) iMasterSize;

        }

        return dScale;

    }
         
    }
    
  
  
 /* @Override
  public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    if (pageIndex > 0) {
      return(NO_SUCH_PAGE);
    }
    else {
      Graphics2D graph = (Graphics2D)g;
      graph.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      disableBuffering(compent);
      compent.paint(graph);
      enableBuffering(compent);
      return(PAGE_EXISTS);
    }
  }
  
  public static void disableBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }
  
  public static void enableBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }*/
