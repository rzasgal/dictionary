package com.usb.dictionary.configuration;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class TestElasticSearchContainer  extends ElasticsearchContainer {
    private static final String ELASTIC_SEARCH_DOCKER = "docker.elastic.co/elasticsearch/elasticsearch:7.16.3";

    private static final String CLUSTER_NAME = "cluster.name";

    private static final String ELASTIC_SEARCH = "elasticsearch";

    public TestElasticSearchContainer() {
        super(ELASTIC_SEARCH_DOCKER);
        this.addFixedExposedPort(9500, 9200);
        this.addFixedExposedPort(9300, 9300);
        this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
