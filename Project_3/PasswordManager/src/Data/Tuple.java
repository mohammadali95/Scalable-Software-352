package Data;

public class Tuple<E> {

    private E label,password;

    public Tuple(E label, E password) {
        this.label = label;
        this.password = password;
    }

    public E getLabel() {
        return this.label;
    }

    public E getPassword() {
        return this.password;
    }

    public void setLabel(E label) {
        this.label = label;
    }

    public void setPassword(E password) {
        this.password = password;
    }
    
    public String toString() {
    	return password.toString() + ", " + label.toString();
    }
}
