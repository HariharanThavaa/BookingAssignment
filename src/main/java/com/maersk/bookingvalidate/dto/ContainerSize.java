package com.maersk.bookingvalidate.dto;


public enum ContainerSize {
    SMALL(20), BIG(40);

    private int size;
    ContainerSize(final int size) {
        this.size = size;
    }

    int getSize() {
        return size;
    }
}
