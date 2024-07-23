package com.netflix.springdatabugs;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowsRepository extends CrudRepository<Show, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from show #sort",
            queryRewriter = SortFixQueryRewriter.class //This query rewriter should not be needed!
    )
    List<Show> showsNativeWithWorkaround(Sort sort);

    //This query fails, because it doesn't have the queryRewriter workaround.
    @Query(
            nativeQuery = true,
            value = "select * from show #sort"
    )
    List<Show> showsNativeWithoutWorkaround(Sort sort);

}
