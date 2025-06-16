package it.intesys.codylab.rookie.lessons.service.exception;

public class NotFound extends Throwable {
    private final Long id;
    private final Class<?> cls;

    public NotFound(Long id, Class<?> cls) {
        this.id = id;
        this.cls = cls;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getMessage(){
        return toString();
    }

    public Class<?> getCls() {
        return cls;
    }

    @Override
    public String toString() {
        return "NotFound{" +
                "id=" + id +
                ", cls=" + cls +
                '}';
    }
}
