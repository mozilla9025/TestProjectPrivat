package pb;

/**
 * Created by mozil on 21.04.2017.
 */
public class Triangles {

    private float[] edges;

    public String getTriangleType(Triangle tr) {
        edges = tr.getEdges();
        String result = null;
        if (isTriangleExist()) {
            if (isEquilateral())
                result = "Equilateral";
            else if (isRectangular())
                result = "Rectangular";
            else if (isIsosceles())
                result = "Isosceles";
            else
                result = "Arbitrary";
        }
        return new String(result);
    }


    private boolean isEquilateral() {
        if (edges[0] == edges[1] && edges[1] == edges[2])
            return true;
        return false;
    }

    private boolean isIsosceles() {
        if (edges[0] == edges[1] || edges[0] == edges[2] || edges[1] == edges[2])
            return true;
        return false;
    }

    private boolean isRectangular() {
        return ((edges[0] * edges[0] == edges[1] * edges[1] + edges[2] * edges[2])
                || (edges[1] * edges[1] == edges[0] * edges[0] + edges[2] * edges[2])
                || (edges[2] * edges[2] == edges[1] * edges[1] + edges[0] * edges[0]));

    }

    private boolean isTriangleExist() {
        double sumOfMin = 0;
        double maxEdge = edges[0];

        for (double edge : edges) {
            sumOfMin += edge;
            if (edge > maxEdge)
                maxEdge = edge;
        }

        sumOfMin = sumOfMin - maxEdge;

        if (sumOfMin > maxEdge)
            return true;

        return false;
    }
}
