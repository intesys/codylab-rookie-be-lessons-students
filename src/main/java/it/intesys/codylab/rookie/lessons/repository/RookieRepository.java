package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;

public abstract class RookieRepository<D> {
    /* tra <> non ho una variabile che rappresenta
     un metodo ma ne ho una che rappresenta una classe, così facendo l'interfaccia non rappresenta
     solo account ma adesso è generica */

    public abstract D save(D account);

    protected void pageQuery(int page, int size, String sort, StringBuilder buf) {
        String[] sortTokens = sort.split(",");
        buf.append("order by ").append(sortTokens[0].trim()).append(' ');
        if(sortTokens.length == 2)
            buf.append(sortTokens[1].trim()).append(' ');

        buf.append("limit").append(' ').append(size).append(' ');
        buf.append("offset").append(' ').append(page * size).append(' ');
    }
}
