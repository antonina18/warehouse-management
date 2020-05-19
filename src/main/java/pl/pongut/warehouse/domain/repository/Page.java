package pl.pongut.warehouse.domain.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page<T> {
    private int pageSize;
    private int pageNumber;
    private int totalItems;
    @NonNull
    private List<T> items;
}
