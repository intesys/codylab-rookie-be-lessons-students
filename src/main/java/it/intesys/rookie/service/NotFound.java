package it.intesys.rookie.service;


public class NotFound extends RuntimeException{
    private final Class<?> cls;
    private final Long id;

    public NotFound(Class<?> cls, Long id){
        this.cls = cls;
        this.id = id;
    }

    @Override
    public String getMessage(){
        return String.format("%s [%d] not found", cls.getSimpleName(), id);
    }
}
