/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.sdk.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFetchingJobDefinition {

  private static final String HANDLERNAME = "TestHandler";

  private final JobDefinition instance = new JobDefinition();

  @Test
  public void fetchDefaultHandlerName() {
    assertEquals("Job Definition Default Handler Name", "ConfirmationJob", instance.getHandlerName());
  }

  @Test
  public void fetchHandlerName() {
    instance.setHandlerName(HANDLERNAME);
    assertEquals("Reception Format", HANDLERNAME, instance.getHandlerName());
  }

}
