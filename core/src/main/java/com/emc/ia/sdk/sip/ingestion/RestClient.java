/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.message.BasicHeader;

import com.emc.ia.sdk.sip.ingestion.dto.Link;
import com.emc.ia.sdk.sip.ingestion.dto.LinkContainer;


public class RestClient implements Closeable {

  private static final String LINK_SELF = "self";
  private static final String LINK_ADD = "http://identifiers.emc.com/add";

  private final HttpClient httpClient;
  private List<Header> headers;

  public RestClient(HttpClient client) {
    this.httpClient = Objects.requireNonNull(client, "Missing HTTP client");
  }

  public List<Header> getHeaders() {
    return headers;
  }

  public void setHeaders(List<Header> headers) {
    this.headers = headers;
  }

  public <T> T get(String uri, final Class<T> type) throws IOException {
    return httpClient.execute(httpClient.httpGetRequest(uri, headers), type);
  }

  @Override
  public void close() {
    httpClient.close();
  }

  public <T> T ingest(String uri, InputStream sip, Class<T> type) throws IOException {
    // TODO - what should be the file name here ? IASIP.zip is Ok ?
    InputStreamBody file = new InputStreamBody(sip, ContentType.APPLICATION_OCTET_STREAM, "IASIP.zip");
    HttpEntity entity = MultipartEntityBuilder.create()
        .addTextBody("format", "sip_zip")
        .addPart("sip", file)
        .build();
    return post(uri, headers, entity, type);
  }

  public <T> T post(String uri, List<Header> httpHeaders, HttpEntity entity, Class<T> type) throws IOException {
    HttpPost postRequest = httpClient.httpPostRequest(uri, httpHeaders);
    postRequest.setEntity(entity);
    return httpClient.execute(postRequest, type);
  }

  public <T> T put(String uri, Class<T> type) throws IOException {
    return httpClient.execute(httpClient.httpPutRequest(uri, headers), type);
  }

  public <T> T follow(LinkContainer state, String relation, Class<T> type) throws IOException {
    return get(linkIn(state, relation).getHref(), type);
  }

  private Link linkIn(LinkContainer state, String relation) {
    Link result = state.getLinks().get(relation);
    Objects.requireNonNull(result, String.format("Missing link %s in %s", relation, state));
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T> T createCollectionItem(LinkContainer collection, T item) throws IOException {
    String uri = linkIn(collection, LINK_ADD).getHref();
    T result = (T)post(uri, withJsonBody(), toJson(item), item.getClass());
    Objects.requireNonNull(result, String.format("Could not create item in %s%n%s", uri, item));
    return result;
  }

  private List<Header> withJsonBody() {
    List<Header> result = new ArrayList<>(headers);
    result.add(new BasicHeader("Content-Type", "application/json"));
    return result;
  }

  private StringEntity toJson(Object object) throws UnsupportedEncodingException {
    return new StringEntity(new JsonFormatter().format(object));
  }

  @SuppressWarnings("unchecked")
  public <T extends LinkContainer> T refresh(T state) throws IOException {
    return (T)follow(state, LINK_SELF, state.getClass());
  }

}
