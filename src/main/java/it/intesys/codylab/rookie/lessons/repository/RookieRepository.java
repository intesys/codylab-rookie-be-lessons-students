package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;

public interface RookieRepository<D> {
    /* tra <> non ho una variabile che rappresenta
     un metodo ma ne ho una che rappresenta una classe, così facendo l'interfaccia non rappresenta
     solo account ma adesso è generica */

    D save(D account);
}
