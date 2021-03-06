import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(255);
        pixelObj.setRed(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorHor()
  {
      Pixel[][] pixels = this.getPixels2D();
      
      
      for(int row = 0; row < pixels.length/2; row++)
      {
          for(int col = 0; col <pixels[row].length;col++)
          {
              
              pixels[-row - 1+ pixels.length][col].setColor(pixels[row][col].getColor());
            }
        }
      
    }
    
  public void dia_effect()
  {
    Pixel[][] pixels = getPixels2D();
    
    Pixel first = null;
    Pixel second = null;
    for(int row = 0; row < pixels.length;row++)
    {
        for(int col = 0; col < pixels[row].length/2;col++)
        {
            int mirrorcol = -1-col+pixels[row].length;
            int mirrorow = -1-row+pixels.length;
            second = pixels[row][col];
            first = pixels[mirrorow][mirrorcol];
            pixels[mirrorow][mirrorcol].setColor(second.getColor());
            pixels[row][col].setColor(first.getColor());
            
            
            
        }
    }
  }
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  public void fix_temple()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(int row = 0; row <= 70; row++)
      {
          for(int col = 0; col <= 190; col++)
          {
              
              pixels[30+row][col+300].setColor(pixels[30+row][-col+250].getColor());
            }
        }
    }
  
  public void cropAndCopy( Picture sourcePicture, int startSourceRow, int endSourceRow, int startSourceCol, int endSourceCol,
         int startDestRow, int startDestCol )
  {
      Pixel[][] source_pixels = sourcePicture.getPixels2D();
      Pixel[][] pixels = this.getPixels2D();
      int row = 0;
      int col = 0;
      
      for(int souRow = startSourceRow; souRow <= endSourceRow; souRow++)
      {
          
          for(int souCol = startSourceCol; souCol <= endSourceCol;souCol++)
          {
              
              pixels[startDestRow+row][startDestCol+col].setColor(source_pixels[souRow][souCol].getColor());
              
              col++;
         
            }
            row++;
            col = 0;
        }
      
      
  }
  
  public void filter1()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(190);
        pixelObj.setGreen(190);
      }
    }
  }
  
  public void gray()
  {
    //Gray!!
    Pixel[][] pixels = this.getPixels2D();
      for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int blu = pixelObj.getBlue();
        int gre = pixelObj.getGreen();
        int red = pixelObj.getRed();
        
        int ave = blu + gre + red;
        ave /= 3;
        pixelObj.setBlue(ave);
        pixelObj.setGreen(ave);
        pixelObj.setRed(ave);
        
        
      }
    }
    }
  
    public void darker()
  {
    // Making whole image looks like purple!
    Pixel[][] pixels = this.getPixels2D();
      for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int blu = pixelObj.getBlue();
        int gre = pixelObj.getGreen();
        int red = pixelObj.getRed();
        
        blu += 150;
        gre -= 100;
        red += 70;
        pixelObj.setBlue(blu);
        pixelObj.setGreen(gre);
        pixelObj.setRed(red);
        
        
      }
    }
    }
  public void filter_last()
  {
    // island2 filter, change the trees' color
    Pixel[][] pixels = this.getPixels2D();
      for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int blu = pixelObj.getBlue();
        int gre = pixelObj.getGreen();
        int red = pixelObj.getRed();
        if(gre > (blu + 20) && gre > (red + 20))
        {
            pixelObj.setRed(red+50);
            pixelObj.setGreen(0);
            pixelObj.setBlue(blu-40);
            
        }
        
      }
    }
    }
    
  public static void collage()
  {
      //Collage main method
      Picture base = new Picture(562,1000);
      Picture island = new Picture("coo_island_mod.jpg");
      base.cropAndCopy(island,0,280,0,499,0,0);

      Picture island2 = new Picture("coo_island_mod.jpg");
      island2.mirrorVertical();
      base.cropAndCopy(island2,0,280,0,499,0,500);
      
      //################################################
      base.mirrorHor(); // NEED !!!!!!!!!
      //################################################
      
      island2.filter_last();
      base.cropAndCopy(island2,0,280,0,499,0,500);
      
      
      Picture island3 = new Picture("coo_island_mod.jpg");
      
      island3.cropAndCopy(base,280,560,0,499,0,0);
      island3.gray();
      base.cropAndCopy(island3,0,280,0,499,281,0);
      
      
      Picture island4 = new Picture("coo_island_mod.jpg");
      island4.cropAndCopy(base,281,560,500,999,0,0);
      island4.darker();
      base.cropAndCopy(island4,0,280,0,499,281,500);
      
      base.explore();
   }
   
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
      

    Picture beach = new Picture("temple.jpg");
    Picture earth = new Picture("earth.jpg");
    beach.mirrorHor();
    //beach.mirror_effect();
    earth.dia_effect();
    beach.cropAndCopy(earth,100,200,200,300,100,200);
    beach.fix_temple();
    beach.explore();
    beach.zeroBlue();
    beach.explore();

  }
  
} // this } is the end of class Picture, put all new methods before this
