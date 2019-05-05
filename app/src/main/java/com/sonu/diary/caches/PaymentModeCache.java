package com.sonu.diary.caches;

import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.util.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentModeCache {

    private static Map<String, PaymentMode> paymentModes = new HashMap<>();

    public List<PaymentMode> getPaymentModes(){
        if(paymentModes.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(paymentModes.values());
    }

    public void loadCache() {
        try {
            List<PaymentMode> lst = DBUtil.getPaymentModes();
            lst.forEach(t -> {
                paymentModes.put(t.getDescription(), t);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPaymentModeDescriptions(){
        if(paymentModes.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(paymentModes.keySet());
    }
}
