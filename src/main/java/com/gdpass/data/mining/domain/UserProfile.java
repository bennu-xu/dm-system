package com.gdpass.data.mining.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -1467138946L;

    private String username;
    private String name;
    private String branch;
    private String branchName;
}