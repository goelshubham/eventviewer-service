# eventviewer-service

# User Story
As a system administrator, I want to view events that have occurred in my system during a given time frame (max 30 days). I should be able to filter down the events within a time range. I should also be able to filter the events on any field except for the ID field. I want to see these events on a web page. These events arrive as a JSON file every 5 minutes. Assume that approximately 1k events are being received in each file (a sample JSON is attached at the bottom of this email).


Outcome requested from you

Design artifacts
•    Please present a high-level design that can be used by an engineering team to implement this event viewer.
•    Microservice architecture preferred

Coding artifacts
We would like you to code some of this. The minimal code that we are expecting should include
•    Buildable source code with unit test for at least one java and one UI class (your choice - upload zip file or send a git repo link)
•    UI screenshot
•    Filtering support required for atleast one field.
Preferred technologies
Java, Spring, UI framework (Angular - preferred, Vue.js, React)

Please state any assumptions that you are making.

SAMPLE JSON FILE
{
  "events": [
    {
      "id": "3273bf58-bf03-5cc2-b13b-9101b084ad44",
      "createdAt": "2019-01-16T11:17:10Z",
      "type": "DEBUG",
      "source": "UI",
      "details": "User login success"
    },
    {
      "id": "3273bf58-bf03-5cc2-b13b-9101b0846765",
      "createdAt": "2019-01-17T13:23:30Z",
      "type": "INFO",
      "source": "Data processor",
      "details": "File Processed successfully"
    },
    {
      "id": "3273bf58-bf03-5cc2-b13b-9101b084111",
      "createdAt": "2019-01-18T12:34:20Z",
      "type": "ERROR",
      "source": "AuthService",
      "details": "NullPointerException"
    }
  ]
}


# eventviewer

EventViewer is a cloud based application which processes input log files and enables an administrator to check events.

#### Technologies Used
* Java 1.8
* Spring Boot 1.5
* Elastic Search 2.4.4
* GitHub
* Eclipse IDE
* Cloud Foundry

#### System Design
##### Proposed Technical Solution #1
I am assuming that eventing log files (.json extension) are being placed on a Linux server. As shown in below design diagram:

We can create a standalone Java application (EventReader) and schedule it to run on a linux server using Quartz and Cron job scheduling. EventReader should be a multi-threader application which reads the input JSON file in a very short time and it makes REST api call to Elastic Search and inserts individual Events into the elastic search. Below is a snapshot from elastic search of three events I inserted using EventReader application.

```
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "failed": 0
    },
    "hits": {
        "total": 3,
        "max_score": 1,
        "hits": [
            {
                "_index": "events",
                "_type": "event",
                "_id": "3273bf58-bf03-5cc2-b13b-9101b084ad44",
                "_score": 1,
                "_source": {
                    "createdAt": "2019-01-16T11:17:10Z",
                    "details": "User login success",
                    "id": "3273bf58-bf03-5cc2-b13b-9101b084ad44",
                    "source": "UI",
                    "type": "DEBUG"
                }
            },
            {
                "_index": "events",
                "_type": "event",
                "_id": "3273bf58-bf03-5cc2-b13b-9101b0846765",
                "_score": 1,
                "_source": {
                    "createdAt": "2019-01-17T13:23:30Z",
                    "details": "File Processed successfully",
                    "id": "3273bf58-bf03-5cc2-b13b-9101b0846765",
                    "source": "Data processor",
                    "type": "INFO"
                }
            },
            {
                "_index": "events",
                "_type": "event",
                "_id": "3273bf58-bf03-5cc2-b13b-9101b084111",
                "_score": 1,
                "_source": {
                    "createdAt": "2019-01-18T12:34:20Z",
                    "details": "NullPointerException",
                    "id": "3273bf58-bf03-5cc2-b13b-9101b084111",
                    "source": "AuthService",
                    "type": "ERROR"
                }
            }
        ]
    }
}
```

EventViewer microservice has to be distributed and scalable and so it can be deployed as AWS Serverless API or Pivotal Cloud Foundry with autoscaling. I have used Spring Boot and Elastic Search integration to read data from Elasticsearch. Elastic search is able to achieve fast search responses because, instead of searching the text directly, it searches an index instead. I have chosen elastic search because of its following advantages:
1. It's open source and provides powerful full-text search capabilities
2. Elastic Search implements a lot of features, such as customized splitting text into words, customized stemming, facetted search, etc.
3. Elastic search is document-oriented. It stores real world complex entities as structured JSON documents
4. Highly scalable so best suited for our event logging application
5. Has a powerful JSON based queries language
6. Exposes simple RESTful APIs
7. Cloud based distributed system (Data redundancy so protection from data loss)

For the simplisity of this project, I have exposed only three REST endpoints which can be consumed by user interface.

1. GET /eventviewer/events/ -> Returns a list of ALL events in JSON format
2. GET /eventviewer/events/type/{type} -> Returns a list of events in JSON format filtered by 'type' field in the event. For example, if {type} is passed as ERROR then all events which has type field as ERROR will be returned to UI
3. /eventviewer/events/days/{days} -> Returns a list of events in JSON format for a given timeframe. For example, if {days} is given as 30 then all the events in last 30 days will be returned
