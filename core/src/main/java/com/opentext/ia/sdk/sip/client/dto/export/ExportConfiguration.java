/*
 * Copyright (c) 2016-2017 by OpenText Corporation. All Rights Reserved.
 */
package com.opentext.ia.sdk.sip.client.dto.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opentext.ia.sdk.sip.client.dto.NamedLinkContainer;


public class ExportConfiguration extends NamedLinkContainer {

  private String description;
  private String exportType;
  private String pipeline;
  private List<Transformation> transformations = new ArrayList<>();
  private Map<String, String> options = new HashMap<>();
  private Map<String, String> encryptedOptions = new HashMap<>();

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getExportType() {
    return exportType;
  }

  public void setExportType(String exportType) {
    this.exportType = exportType;
  }

  public String getPipeline() {
    return pipeline;
  }

  public void setPipeline(String pipeline) {
    this.pipeline = pipeline;
  }

  public List<Transformation> getTransformations() {
    return transformations;
  }

  public void setTransformations(List<Transformation> transformations) {
    this.transformations = transformations;
  }

  public void addTransformation(Transformation transformation) {
    if (transformation != null && transformation.getPortName() != null && transformation.getName() != null) {
      this.transformations.add(transformation);
    }
  }

  public Map<String, String> getOptions() {
    return options;
  }

  public void setOptions(Map<String, String> options) {
    this.options = options;
  }

  public void addOption(DefaultOption option, String value) {
    addOption(option.getName(), value);
  }

  public void addOption(String key, String value) {
    if (key != null && value != null) {
      this.options.put(key, value);
    }
  }

  public Map<String, String> getEncryptedOptions() {
    return encryptedOptions;
  }

  public void setEncryptedOptions(Map<String, String> encryptedOptions) {
    this.encryptedOptions = encryptedOptions;
  }

  public void addEncryptedOption(String key, String value) {
    if (key != null && value != null) {
      this.encryptedOptions.put(key, value);
    }
  }


  public enum DefaultOption {

    XSL_RESULT_FORMAT("xslResultFormat"), XQUERY_RESULT_FORMAT("xqueryResultFormat");

    private final String name;

    DefaultOption(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

  }


  @SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
  public static class Transformation {

    private String portName;
    private String name;

    public String getPortName() {
      return portName;
    }

    public void setPortName(String portName) {
      this.portName = portName;
    }

    public String getName() {
      return name;
    }

    public void setName(String transformation) {
      this.name = transformation;
    }
  }
}