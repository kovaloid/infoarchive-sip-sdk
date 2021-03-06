/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.sdk.sip;

import java.io.InputStream;

import javax.validation.ValidationException;


/**
 * Validate that the contents of a stream meets the expectations.
 */
@FunctionalInterface
public interface Validator {

  /**
   * Validate that the contents of a stream meets the expectations.
   * @param stream The stream to validate
   * @throws ValidationException When the stream doesn't meet expectations
   */
  void validate(InputStream stream);

}
