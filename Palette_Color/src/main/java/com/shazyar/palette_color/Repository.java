package com.shazyar.palette_color;

import java.util.List;

public interface Repository<T> {

    T findOne(int id);

    List<T> findAll();

    int add(T t);

    int remove(int id);



}
