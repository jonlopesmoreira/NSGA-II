package entidades;

class Wrapper {
    private final P p;
    public Wrapper(P p) {
        this.p = p;
    }
    public P unwrap() {
        return p;
    }
    public boolean equals(Object other) {
        if (other instanceof Wrapper) {
            return ((Wrapper) other).p.getId()==(p.getId());
        } else {
            return false;
        }
    }
    public int hashCode() {
        return p.hashCode();
    }
}