/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.sdk.yaml.configuration;

import java.util.Locale;
import java.util.function.Predicate;

import com.opentext.ia.sdk.yaml.core.Entry;


class IsQuery implements Predicate<Entry> {

  @Override
  public boolean test(Entry entry) {
    return entry.getKey().toLowerCase(Locale.ENGLISH).endsWith("query")
        && entry.getValue().toMap().containsKey("text");
  }

}
