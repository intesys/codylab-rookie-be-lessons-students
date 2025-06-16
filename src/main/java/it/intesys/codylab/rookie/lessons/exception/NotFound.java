package it.intesys.codylab.rookie.lessons.exception;

public class NotFound extends RuntimeException {
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
    public String getMessage() {
        return toString();
    }

    public Class<?> getNotFoundClass() {
        return cls;
    }

    @Override
    public String toString() {
        return "NotFound{" +
                "id=" + id +
                ", cls=" + cls.getSimpleName() +
                '}';
    }
}
