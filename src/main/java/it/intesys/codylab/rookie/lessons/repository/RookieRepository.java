package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;

public interface RookieRepository<D> {
    D save (D account);
}
