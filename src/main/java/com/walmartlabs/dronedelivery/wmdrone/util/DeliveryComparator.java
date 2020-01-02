package com.walmartlabs.dronedelivery.wmdrone.util;

import java.util.Comparator;

import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData;
/**
 * First it sort with tags, then with minutes to reach the location and then the order stamp 
 */
public class DeliveryComparator implements Comparator<OrderData> {
    @Override
    public int compare(final OrderData o1, final OrderData o2) {

        int result = o1.getTag().compareTo(o2.getTag());
        if (result == 0) {
            result = o1.getTimeToLocation().compareTo(o2.getTimeToLocation());
        }
        if (result == 0) {
            result = o1.getTimeStamp().compareTo(o2.getTimeStamp());
        }
        return result;
    }
}