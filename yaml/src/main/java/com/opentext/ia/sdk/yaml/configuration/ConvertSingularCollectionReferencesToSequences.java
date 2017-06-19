/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.sdk.yaml.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.atteo.evo.inflector.English;

import com.opentext.ia.sdk.yaml.core.PathVisitor;
import com.opentext.ia.sdk.yaml.core.Visit;
import com.opentext.ia.sdk.yaml.core.YamlMap;


public class ConvertSingularCollectionReferencesToSequences extends PathVisitor {

  private static final Map<String, Collection<String>> COLLECTION_REFERENCES_BY_PATH_REGEX
      = collectionReferencesByPathRegex();

  private static Map<String, Collection<String>> collectionReferencesByPathRegex() {
    Map<String, Collection<String>> result = new HashMap<>();
    result.put("/confirmations/\\d", Arrays.asList("holding"));
    return result;
  }


  public ConvertSingularCollectionReferencesToSequences() {
    super(COLLECTION_REFERENCES_BY_PATH_REGEX.keySet());
  }

  @Override
  public void accept(Visit visit) {
    YamlMap map = visit.getMap();
    pathRegexesMatching(visit)
        .flatMap(regex -> COLLECTION_REFERENCES_BY_PATH_REGEX.get(regex).stream())
        .filter(property -> map.containsKey(property))
        .forEach(property -> replaceSingleReferenceWithSequence(map, property));
  }


  private void replaceSingleReferenceWithSequence(YamlMap map, String type) {
    map.put(English.plural(type), Arrays.asList(map.get(type)))
        .remove(type);
  }

}