#!/bin/bash
curl -X DELETE "localhost:9200/word?pretty"

curl -X PUT "localhost:9200/word?pretty" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "number_of_shards": 2,
    "number_of_replicas": 2,
    "analysis": {
      "filter": {
        "ngram_filter": {
          "type": "edge_ngram",
          "min_gram": 1,
          "max_gram": 5
        }
      },
      "analyzer": {
        "ngram_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "ngram_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "content": {
        "type": "keyword",
        "copy_to": "content_ext"
      },
      "content_ext": {
        "type": "text",
        "analyzer": "ngram_analyzer"
      },
      "languageCode": {
        "type": "keyword"
      },
      "description": {
        "type": "keyword"
      },
      "tags": {
        "type": "keyword"
      },
      "meanings": {
        "type": "long"
      }
    }
  }
}'