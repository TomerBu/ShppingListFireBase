package tomerbu.edu.shppinglistfirebase.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by tomerbuzaglo on 04/07/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
@IgnoreExtraProperties

//TODO: Consider adding UID To Base Model with Getter and Setter and Constructor to enforce global usage
public class BaseModel {

    @Exclude
    public HashMap<String, Object> toMap() {
        TypeReference<HashMap<String, Object>> type =
                new TypeReference<HashMap<String, Object>>() {
                };

        return new ObjectMapper().convertValue(this, type);
    }
}
