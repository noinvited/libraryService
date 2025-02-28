create table if not exists public.library (
    id bigserial primary key,
    name varchar(255),
    location varchar(127)
);