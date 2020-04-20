FROM docker.elastic.co/logstash/logstash:7.1.1

COPY pipeline/logstash.conf /usr/share/logstash/pipeline/

WORKDIR /usr/share/logstash/pipeline/kafka
COPY pipeline/client_jass.conf /usr/share/logstash/pipeline/kafka/

ADD config/ /usr/share/logstash/config/
