/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.ingestion;

import com.emc.ia.sdk.support.rest.StandardLinkRelations;


interface InfoArchiveLinkRelations extends StandardLinkRelations {

  String LINK_PREFIX = "http://identifiers.emc.com/";
  String LINK_ADD = LINK_PREFIX + "add";
  String LINK_AIPS = LINK_PREFIX + "aips";
  String LINK_INGEST = LINK_PREFIX + "ingest";
  String LINK_APPLICATIONS = LINK_PREFIX + "applications";
  String LINK_DATABASES = LINK_PREFIX + "xdb-databases";
  String LINK_FEDERATIONS = LINK_PREFIX + "federations";
  String LINK_HOLDINGS = LINK_PREFIX + "holdings";
  String LINK_RECEIVER_NODES = LINK_PREFIX + "receiver-nodes";
  String LINK_SPACES = LINK_PREFIX + "spaces";
  String LINK_TENANT = LINK_PREFIX + "tenant";

}
