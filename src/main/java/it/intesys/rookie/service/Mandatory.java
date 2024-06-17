package it.intesys.rookie.service;

public class Mandatory extends RuntimeException {
    Class<?> cls;
    Long id;
    String what;

    public Mandatory(Class<?> cls, Long id, String what) {
        this.cls = cls;
        this.id = id;
        this.what = what;
    }

    @Override
    public String getMessage() {
        return String.format("Mandatory %s on %s[%d]", what, cls.getSimpleName(), id);
    }
}
