public class Citta {
    private Integer id;
    private Double x;
    private Double y;

    public Citta(Integer id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Citta{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
