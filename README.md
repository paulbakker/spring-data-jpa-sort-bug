This project reproduces a bug with using #sort in a native query with spring-data-jpa.

The @Repository is defined in `ShowsRepository`, which has a native query.
The native query is written as `select * from show #sort`, and the method has a `Sort` parameter.

Without the `SortFixQueryRewriter`, the example test (`SpringDataBugsApplicationTests`) fails because of the incorrect query 

```sql
select * from show #sort order by title asc
```

Note that it _almost_ did the right thing; it appended `order by title asc`.
However, it failed to remove the `#sort` placeholder.

The `SortFixQueryRewriter` works around the problem, but obviously shouldn't be needed.

The `SpringDataBugsApplicationTests` uses a Postgres Testcontaier, no setup is required.