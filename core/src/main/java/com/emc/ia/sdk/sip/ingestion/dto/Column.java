/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion.dto;


public class Column {

  private String name;
  private String value;

  public Column() {
    setName("lastName");
    setValue("InfoArchive");
  }

  public String getName() {
    return name;
  }

  public final void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public final void setValue(String value) {
    this.value = value;
  }
}
