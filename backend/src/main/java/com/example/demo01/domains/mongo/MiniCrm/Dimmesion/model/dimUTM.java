package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model;

import jakarta.persistence.Id;

public class dimUTM {

    @Id
    private String _id;

    private String utmName;
    private String utmShortName;

    private String channel;

}
