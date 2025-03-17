package com.davanddev.uppgift_3.endpoint;

import com.davanddev.uppgift_3.generated.CreatePhoneModelRequest;
import com.davanddev.uppgift_3.generated.CreatePhoneModelResponse;
import com.davanddev.uppgift_3.generated.GetPhoneModelRequest;
import com.davanddev.uppgift_3.generated.GetPhoneModelResponse;
import com.davanddev.uppgift_3.generated.PhoneModelType;
import com.davanddev.uppgift_3.model.PhoneModelData;
import com.davanddev.uppgift_3.repository.PhoneModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PhoneModelEndpoint {

    private static final String NAMESPACE_URI = "http://example.org/phonemodels";

    private final PhoneModelRepository repository;

    @Autowired
    public PhoneModelEndpoint(PhoneModelRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetPhoneModelRequest")
    @ResponsePayload
    public GetPhoneModelResponse getPhoneModel(@RequestPayload GetPhoneModelRequest request) {

        var phoneData = repository.findPhoneModelById(request.getId());
        GetPhoneModelResponse response = new GetPhoneModelResponse();

        if (phoneData != null) {
            PhoneModelType phoneModelType = new PhoneModelType();
            phoneModelType.setId(phoneData.getId());
            phoneModelType.setModelName(phoneData.getModelName());
            phoneModelType.setManufacturer(phoneData.getManufacturer());
            phoneModelType.setReleaseYear(phoneData.getReleaseYear());
            response.setPhoneModel(phoneModelType);
        } else {
            PhoneModelType empty = new PhoneModelType();
            empty.setId(-1);
            empty.setModelName("NotFound");
            empty.setManufacturer("N/A");
            empty.setReleaseYear(0);
            response.setPhoneModel(empty);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreatePhoneModelRequest")
    @ResponsePayload
    public CreatePhoneModelResponse createPhoneModel(@RequestPayload CreatePhoneModelRequest request) {
        PhoneModelData data = new PhoneModelData();
        data.setModelName(request.getModelName());
        data.setManufacturer(request.getManufacturer());
        data.setReleaseYear(request.getReleaseYear());

        Long newId = repository.createPhoneModel(data);

        CreatePhoneModelResponse response = new CreatePhoneModelResponse();
        response.setSuccess(true);
        response.setId(newId);
        return response;
    }
}
