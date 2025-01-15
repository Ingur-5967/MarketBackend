package ru.solomka.market.mapper;

public interface SchemaMapping<F, T> {
    T map(F from);
}