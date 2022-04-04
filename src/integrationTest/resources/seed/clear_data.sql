delete from public.player;
delete from public.machine;
SELECT setval('player_id_seq', 1);
SELECT setval('machine_id_seq', 1);