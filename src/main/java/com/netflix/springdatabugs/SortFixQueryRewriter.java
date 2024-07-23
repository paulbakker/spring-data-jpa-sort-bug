package com.netflix.springdatabugs;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;

public class SortFixQueryRewriter implements QueryRewriter {
    @Override
    public String rewrite(String query, Sort sort) {
        return query.replace("#sort", "");
    }
}
