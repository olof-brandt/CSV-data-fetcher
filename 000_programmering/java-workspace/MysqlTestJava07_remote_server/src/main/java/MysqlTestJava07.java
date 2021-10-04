package com.example.MysqlTestJava07;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;//Ellipse2D;

//Förklaring hur JDBC för mysql importeras: https://www.progress.com/blogs/jdbc-tutorial-connecting-to-your-database-using-jdbc
//Förklaring hur importera Java chart library: https://www.youtube.com/watch?v=AIld6zEeBJ8



public class MysqlTestJava07
{
      public static void main(String[] args) {
      // TODO Auto-generated method stub
      //System.out.println("Hej.");
      boolean printa = true;
      boolean toArray = true;
      boolean plotta = true;

      ArrayList df = new ArrayList();
      //ArrayList<Double> df = new ArrayList();


      try {
         //Class.forName("com.mysql.jdbc.Driver").newInstance();
         Connection conn = null;
         //Statement st = null;
         conn = DriverManager.getConnection("jdbc:mysql://mysql683.loopia.se/sololof_se_db_1","github@s266820", "gatsby1925");
         System.out.println("Database is connected !");
         Statement st = conn.createStatement();

         PreparedStatement preparedStatement=conn.prepareStatement("select timeDB, ApparentPower from Measurements where timeDB > '2020-11-05' and timeDB < '2020-11-06'");


         //Creating Java ResultSet object
         ResultSet resultSet=preparedStatement.executeQuery();

         if (toArray == true)
         {
            int i = 0;
            while(resultSet.next())
            {
               ArrayList row = new ArrayList();
               //row.add(resultSet.getInt(3));
               row.add(resultSet.getInt(2));
               df.add(row.get(0));

               i = i + 1;

            }
         }

         if (printa == true)
         {
            /*
            int i = 0;
            while(resultSet.next())
            {
               System.out.println(df.get(i));
               i = i + 1;
               //String time = resultSet.getString("timeDB");
               //String power=resultSet.getString("ApparentPower");
               //System.out.println(time + " " + power);//.substring(0,1));
            }
            */

            for(int i = 0; i < df.size(); i++)
            {
               System.out.println(df.get(i));
            }
         }


         conn.close();



         if(plotta == true)
         {

            int[] newCord = {1, 40, 23};

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PlotExample plotSet = new PlotExample();
            plotSet.scatter = false;
            //plotSet.cordArray = df.toArray(new Integer[0]);

            frame.add(plotSet);
            frame.setSize(800,600);
            frame.setLocation(200,200);
            frame.setVisible(true);

         }


      }
      catch(Exception e) {
         System.out.print("Do not connect to DB - Error:"+e);
      }

   }
   //Extends JPanel class
   public static class PlotExample extends JPanel
   {
      boolean scatter = false;
      //initialize coordinates
      //int[] cordArray = {10, 20, 40, 80, 120, 200};
      ArrayList<Double> cord = new ArrayList(Arrays.asList(10, 20, 40, 80, 120, 200, 400, 10, 30, 40, 67, 32, 43));


      //Convert arraylist to array
      Integer[] cordArray = cord.toArray(new Integer[0]);


      public static void setCord(int[] cordNew)
      {
         //this.cord = cordNew;
      }


      int marg = 60;

      protected void paintComponent(Graphics grf)
      {
         //create instance of the Graphics to use its methods
         super.paintComponent(grf);
         Graphics2D graph = (Graphics2D) grf;

         //Sets the value of a single preference for the rendering algorithms.
         graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

         // get width and height
         int width = getWidth();
         int height = getHeight();

         // draw graph
         graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
         graph.draw(new Line2D.Double(marg, height - marg, width - marg, height - marg));

         //find value of x and scale to plot points
         double x = (double) (width - 2 * marg) / (cord.size() - 1);
         double scale = (double) (height - 2 * marg) / getMax();

         //set color for points
         graph.setPaint(Color.RED);


         // set points to the graph

         if(scatter == true)
         {
            for(int i = 0; i < cord.size(); i++)
            {
               double x1 = marg + i * x;
               double y1 = height - marg - scale * cordArray[i];// * Double.valueOf(cord.get(i));
               graph.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
            }
         }
         else
         {

            int x1 = marg;//cordArray[0];
            int y1 = height - marg - (int) (scale * cordArray[0]);

            for(int i = 0; i < cord.size(); i++)
            {
               int x2 = marg + i * (int) x; //= cord.get(i);
               int y2 = height - marg - (int) (scale * cordArray[i]);
               //int //x1 = marg + i * x;
               //int //y1 = height - marg - scale * cordArray[i];// * Double.valueOf(cord.get(i));
               //graph.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
               graph.drawLine(x1, y1, x2, y2);

               x1 = x2;
               y1 = y2;
            }

         }

      }

      //create getMax() method to find maximum value
      private int getMax()
      {
         int max = -Integer.MAX_VALUE;
         for(int i = 0; i < cordArray.length; i++)
         {
            if(cordArray[i] > max)
               max = cordArray[i];

         }
         return max;
      }
   }

}
