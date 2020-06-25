package com.cy.pj.common.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Node implements Serializable{
	private static final long serialVersionUID = 8556485044700393868L;
	private Integer id;
	private String name;
	private Integer parentId;
}
