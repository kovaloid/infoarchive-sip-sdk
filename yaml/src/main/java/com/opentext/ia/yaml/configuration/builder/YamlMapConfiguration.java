/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.yaml.configuration.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.atteo.evo.inflector.English;

import com.opentext.ia.configuration.Configuration;
import com.opentext.ia.yaml.core.Value;
import com.opentext.ia.yaml.core.YamlMap;


/**
 * An InfoArchive configuration in YAML.
 * @author Ray Sinnema
 * @since 9.4.0
 */
public class YamlMapConfiguration implements Configuration<YamlMap> {

  private static final String NAME = "name";
  private static final String TENANT = "tenant";
  private static final String APPLICATION = "application";
  private static final String SPACE = "space";
  private static final String XDB_FEDERATION = "xdbFederation";
  private static final String XDB_DATABASE = "xdbDatabase";
  private static final String XDB_CLUSTER = "xdbCluster";

  private final YamlMap yaml;

  public YamlMapConfiguration(YamlMap yaml) {
    this.yaml = yaml;
  }

  /**
   * Returns the YAML representation of the configuration.
   * @return The YAML representation of the configuration
   */
  public YamlMap getYaml() {
    return yaml;
  }

  @Override
  public String toString() {
    return yaml.toString();
  }

  @Override
  public List<YamlMap> getTenants() {
    return toList(streamOfType(TENANT));
  }

  private List<YamlMap> toList(Stream<YamlMap> stream) {
    return stream.collect(Collectors.toList());
  }

  private Stream<YamlMap> streamOfType(String type) {
    String collection = English.plural(type);
    if (yaml.containsKey(collection)) {
      return yaml.get(collection).toList().stream().map(Value::toMap);
    }
    if (yaml.containsKey(type)) {
      return Stream.of(yaml.get(type).toMap());
    }
    return Stream.empty();
  }

  @Override
  public List<YamlMap> getApplications(YamlMap tenant) {
    return childList(tenant, TENANT, APPLICATION);
  }

  private List<YamlMap> childList(YamlMap parent, String parentType, String type) {
    return toList(streamOfType(type)
        .filter(application -> parent.get(NAME).equals(application.get(parentType))));
  }

  @Override
  public List<YamlMap> getSearches(YamlMap application) {
    return childList(application, APPLICATION, "search");
  }

  @Override
  public List<YamlMap> getFileSystemRoots() {
    return toList(streamOfType("fileSystemRoot"));
  }

  @Override
  public List<YamlMap> getSpaces(YamlMap application) {
    return childList(application, APPLICATION, SPACE);
  }

  @Override
  public List<YamlMap> getSpaceRootFolders(YamlMap space) {
    return childList(space, SPACE, "spaceRootFolder");
  }

  @Override
  public List<YamlMap> getSpaceRootXdbLibraries(YamlMap space) {
    return childList(space, SPACE, "spaceRootXdbLibrary");
  }

  @Override
  public List<YamlMap> getXdbLibraries(YamlMap spaceRootXdbLibrary) {
    return childList(spaceRootXdbLibrary, "spaceRootXdbLibrary", "xdbLibrary");
  }

  @Override
  public List<YamlMap> getPdiSchemas(YamlMap application) {
    return childList(application, APPLICATION, "pdiSchema");
  }

  @Override
  public List<YamlMap> getPdis(YamlMap application) {
    return childList(application, APPLICATION, "pdi");
  }

  @Override
  public List<YamlMap> getHoldings(YamlMap application) {
    return childList(application, APPLICATION, "holding");
  }

  @Override
  public List<YamlMap> getCryptoObjects() {
    return toList(streamOfType("cryptoObject"));
  }

  @Override
  public List<YamlMap> getXdbFederations() {
    return toList(streamOfType(XDB_FEDERATION));
  }

  @Override
  public List<YamlMap> getXdbDatabases(YamlMap xdbFederationOrCluster) {
    List<YamlMap> result = new ArrayList<>();
    result.addAll(childList(xdbFederationOrCluster, XDB_FEDERATION, XDB_DATABASE));
    result.addAll(childList(xdbFederationOrCluster, XDB_CLUSTER, XDB_DATABASE));
    return result;
  }

  @Override
  public List<YamlMap> getJobDefinitions() {
    return toList(streamOfType("jobDefinition"));
  }

  @Override
  public List<YamlMap> getXdbClusters() {
    return toList(streamOfType(XDB_CLUSTER));
  }

  @Override
  public List<YamlMap> getContentOwnedBy(YamlMap owner) {
    return owner.get("content").toList().stream()
        .map(Value::toMap)
        .collect(Collectors.toList());
  }

}
