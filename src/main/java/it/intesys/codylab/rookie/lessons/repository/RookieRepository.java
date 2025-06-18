package it.intesys.codylab.rookie.lessons.repository;

public abstract class RookieRepository<D> {
    public abstract D save (D account);

    protected void pageQuery(Integer page, Integer size, String sort, StringBuilder buf) {
        String[] sortTokens = sort.split(",");
        buf.append("order by ").append(sortTokens[0].trim()).append(' ');
        if (sortTokens.length == 2)
            buf.append(sortTokens[1].trim()).append(' ');

        buf.append("limit").append(' ').append(size).append(' ');
        buf.append("offset").append(' ').append(page * size).append(' ');
    }
}
