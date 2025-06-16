package it.intesys.codylab.rookie.lessons.exception;

import it.intesys.codylab.rookie.lessons.domain.Account;
public class NotFound extends RuntimeException {
    private Long id;
    private Class<?> cls;
    public NotFound(Long id, Class<?> cls) {
        this.id = id;
        this.cls = cls;
    }

    public Long getId() {
        return id;
    }

    public Class<?> getNotFoundClass() {
        return cls;
    }

    @Override
    public String toString() {
        return "NotFound{" +
                "id=" + id +
                ", cls=" + cls.getSimpleName()+
                '}';
    }

    @Override
    public String getMessage(){
        return toString();
    }


}
