create view game_count_vw as
    select g.game_id, g.name, count(gc.game_id) as amount  from games g
    join game_copies gc
    on g.game_id = gc.game_id
    where gc.copy_id not in(
        select cb.copy_id from curr_borrowed cb
    )
    group by g.game_id;