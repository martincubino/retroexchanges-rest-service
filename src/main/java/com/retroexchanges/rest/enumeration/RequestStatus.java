package com.retroexchanges.rest.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum RequestStatus {
	  PENDING(0), ACCEPTED(1), DENIED(2), FINISHED(3);
	
    private int value;
    private static Map map = new HashMap<>();

    private RequestStatus(int value) {
        this.value = value;
    }

    static {
        for (RequestStatus requestStatus: RequestStatus.values()) {
            map.put(requestStatus.value, requestStatus);
        }
    }

    public static RequestStatus valueOf(int requestStatus) {
        return (RequestStatus) map.get(requestStatus);
    }

    public int getValue() {
        return value;
    }
}
