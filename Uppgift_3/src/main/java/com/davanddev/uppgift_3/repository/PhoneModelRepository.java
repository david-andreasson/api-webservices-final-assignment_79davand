package com.davanddev.uppgift_3.repository;

import com.davanddev.uppgift_3.model.PhoneModelData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PhoneModelRepository {

    private final Map<Long, PhoneModelData> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        PhoneModelData phone1 = new PhoneModelData(1L, "Nokia 3210", "Nokia", 1999);
        PhoneModelData phone2 = new PhoneModelData(2L, "Nokia 3310", "Nokia", 2000);
        PhoneModelData phone3 = new PhoneModelData(3L, "Ericsson T28", "Ericsson", 2001);

        store.put(phone1.getId(), phone1);
        store.put(phone2.getId(), phone2);
        store.put(phone3.getId(), phone3);

        idGenerator.set(3);
    }

    public Long createPhoneModel(PhoneModelData data) {
        long newId = idGenerator.incrementAndGet();
        data.setId(newId);
        store.put(newId, data);
        return newId;
    }

    public PhoneModelData findPhoneModelById(Long id) {
        return store.get(id);
    }
}
