/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.yaml.resource;

import java.io.IOException;


/**
 * Exception that indicates that a resource name could not be {@linkplain ResourceResolver resolved} to its content.
 */
public class UnknownResourceException extends IllegalArgumentException {

  private static final long serialVersionUID = -1518038744103447630L;

  public UnknownResourceException(String name, IOException cause) {
    super("Unknown resource: " + name, cause);
  }

}
