package pb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mozil on 21.04.2017.
 */
public class Triangle {

    private Point vertex1;
    private Point vertex2;
    private Point vertex3;
    private float[] edges;
    private float perimeter;
    private float square;
    private String type;

    private Triangles tr = new Triangles();

    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        edges = findEdges();
        perimeter = calcPerimeter();
        square = calcSquare();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPerimeter() {
        return perimeter;
    }

    public float getSquare() {
        return square;
    }

    public float[] getEdges() {
        return edges;
    }

    private float[] findEdges() {
        float edge1 = (float) Math.sqrt(Math.pow((vertex1.getX() - vertex2.getX()), 2)
                + Math.pow((vertex1.getY() - vertex2.getY()), 2));

        float edge2 = (float) Math.sqrt(Math.pow((vertex2.getX() - vertex3.getX()), 2)
                + Math.pow((vertex2.getY() - vertex3.getY()), 2));

        float edge3 = (float) Math.sqrt(Math.pow((vertex1.getX() - vertex3.getX()), 2)
                + Math.pow((vertex1.getY() - vertex3.getY()), 2));

        float[] edges = new float[3];

        edges[0] = edge1;
        edges[1] = edge2;
        edges[2] = edge3;

        return edges;
    }

    public float calcPerimeter() {
        float sum = 0;
        for (float current : edges)
            sum += current;

        return sum;
    }

    public float calcSquare() {
        float p = calcPerimeter() / 2;
        return (float) Math.sqrt(p * (p - edges[0]) * (p - edges[1]) * (p - edges[2]));
    }

    private static Triangle maxPerimeter(List<Triangle> list) {
        Triangle max = list.get(0);
        for (Triangle t : list) {
            if (t.getPerimeter() > max.getPerimeter())
                max = t;
        }
        return max;
    }

    private static Triangle maxSquare(List<Triangle> list) {
        Triangle max = list.get(0);
        for (Triangle t : list) {
            if (t.getSquare() > max.getSquare())
                max = t;
        }
        return max;
    }


    private static Triangle minPerimeter(List<Triangle> list) {
        Triangle max = list.get(0);
        for (Triangle t : list) {
            if (t.getSquare() < max.getSquare())
                max = t;
        }
        return max;
    }


    private static Triangle minSquare(List<Triangle> list) {
        Triangle max = list.get(0);
        for (Triangle t : list) {
            if (t.getSquare() < max.getSquare())
                max = t;
        }
        return max;
    }

    @Override
    public String toString() {
        return "P: " + this.calcPerimeter() + " S: " + this.calcSquare() + " Type: " + tr.getTriangleType(this);
    }

    public static void main(String[] args) {

        List<Triangle> triangleList = new ArrayList<>();
        Triangles ts = new Triangles();
        //equilateral
        triangleList.add(new Triangle(new Point(0, 0), new Point(1, 0), new Point((0.5f), (float) (Math.sqrt(3) / 2))));
        triangleList.add(new Triangle(new Point(0, 0), new Point(10, 0), new Point(5, (float) Math.sqrt(60) / 2)));
        triangleList.add(new Triangle(new Point(0, 0), new Point(40, 0), new Point(20, (float) Math.sqrt(120) / 2)));

        //isosceles
        triangleList.add(new Triangle(new Point(70, 10), new Point(100, 10), new Point(85, 60)));
        triangleList.add(new Triangle(new Point(70, 10), new Point(100, 10), new Point(85, 80)));
        triangleList.add(new Triangle(new Point(70, 10), new Point(100, 10), new Point(85, 100)));

        //rectangular
        triangleList.add(new Triangle(new Point(10, 10), new Point(40, 10), new Point(10, 60)));
        triangleList.add(new Triangle(new Point(50, 10), new Point(60, 10), new Point(60, 40)));
        triangleList.add(new Triangle(new Point(10, 10), new Point(50, 10), new Point(10, 90)));

        //arbitrary
        triangleList.add(new Triangle(new Point(10, 10), new Point(40, 15), new Point(12, 54)));
        triangleList.add(new Triangle(new Point(15, 18), new Point(46, 27), new Point(10, 60)));
        triangleList.add(new Triangle(new Point(64, 31), new Point(8, 10), new Point(16, 84)));

        for (Triangle t : triangleList)
            t.setType(ts.getTriangleType(t));

        List<Triangle> equilateral = new ArrayList<>();
        List<Triangle> isosceles = new ArrayList<>();
        List<Triangle> rectangular = new ArrayList<>();
        List<Triangle> arbitrary = new ArrayList<>();

        for (Triangle t : triangleList) {
            if (t.getType().equals("Equilateral"))
                equilateral.add(t);
            else if (t.getType().equals("Isosceles"))
                isosceles.add(t);
            else if (t.getType().equals("Rectangular"))
                rectangular.add(t);
            else if (t.getType().equals("Arbitrary"))
                arbitrary.add(t);
        }

        System.out.println("Min P (eq): " + minPerimeter(equilateral));
        System.out.println("Max P (eq): " + maxPerimeter(equilateral));
        System.out.println("Min S (eq): " + minSquare(equilateral));
        System.out.println("Max S (eq): " + maxSquare(equilateral));

        System.out.println("Min P (is): " + minPerimeter(isosceles));
        System.out.println("Max P (is): " + maxPerimeter(isosceles));
        System.out.println("Min S (is): " + minSquare(isosceles));
        System.out.println("Max S (is): " + maxSquare(isosceles));

        System.out.println("Min P (re): " + minPerimeter(rectangular));
        System.out.println("Max P (re): " + maxPerimeter(rectangular));
        System.out.println("Min S (re): " + minSquare(rectangular));
        System.out.println("Max S (re): " + maxSquare(rectangular));

        System.out.println("Min P (ar): " + minPerimeter(arbitrary));
        System.out.println("Max P (ar): " + maxPerimeter(arbitrary));
        System.out.println("Min S (ar): " + minSquare(arbitrary));
        System.out.println("Max S (ar): " + maxSquare(arbitrary));

    }
}

