package com.retroexchanges.rest.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ProductStatus {
	AVAILABLE(0), RESERVED(1), SOLD(2);

    private int value;
    private static Map map = new HashMap<>();

    private ProductStatus (int value) {
        this.value = value;
    }

    static {
        for (ProductStatus productStatus : ProductStatus.values()) {
            map.put(productStatus.value, productStatus);
        }
    }

    public static ProductStatus valueOf(int productStatus) {
        return (ProductStatus) map.get(productStatus);
    }

    public int getValue() {
        return value;
    }
}

