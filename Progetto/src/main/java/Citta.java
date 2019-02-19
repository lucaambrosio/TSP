public class Citta {
    private Integer id;
    private Double coordx;
    private Double coordy;

    public Citta(Integer id, Double coordx, Double coordy) {
        this.id = id;
        this.coordx = coordx;
        this.coordy = coordy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCoordx() {
        return coordx;
    }

    public void setCoordx(Double coordx) {
        this.coordx = coordx;
    }

    public Double getCoordy() {
        return coordy;
    }

    public void setCoordy(Double coordy) {
        this.coordy = coordy;
    }
}
