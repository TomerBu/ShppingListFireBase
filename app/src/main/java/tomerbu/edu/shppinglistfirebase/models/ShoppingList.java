package tomerbu.edu.shppinglistfirebase.models;

/**
 * Created by tomerbuzaglo on 30/06/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class ShoppingList extends BaseModel {
    private String owner;
    private String listName;

    //Default constructor
    public ShoppingList() {
    }

    public ShoppingList(String owner, String listName) {
        this.owner = owner;
        this.listName = listName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

//    @Override
//    public HashMap<String, Object> toMap() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("owner", this.owner);
//        map.put("listName", this.listName);
//        return map;
//    }
}
