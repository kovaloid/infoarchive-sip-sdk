/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.yaml.configuration;

import java.util.stream.Stream;

import com.opentext.ia.yaml.core.Value;
import com.opentext.ia.yaml.core.YamlMap;


class ReplacePdiIndexNamespaceWithUri extends ReplaceIndexNamespaceWithUri {

  ReplacePdiIndexNamespaceWithUri() {
    super("pdi");
  }

  @Override
  Stream<Value> getIndexParents(YamlMap content) {
    return content.get(DATA).toList().stream();
  }

}
