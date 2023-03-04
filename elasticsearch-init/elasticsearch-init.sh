#!/bin/bash
curl -X DELETE "localhost:9200/entry?pretty"
curl -X PUT "localhost:9200/entry?pretty" -H 'Content-Type: application/json' -d'
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
      "words": {
        "type": "nested",
        "properties": {
          "name": {
            "type": "keyword"
          },
          "nameExt": {
            "type": "text",
            "analyzer": "ngram_analyzer"
          },
          "type": {
            "type": "keyword"
          },
          "languageCode": {
            "type": "keyword"
          },
          "description": {
            "type": "text"
          }
        }
      },
      "tags": {
        "type": "keyword"
      }
    }
  }
}
'