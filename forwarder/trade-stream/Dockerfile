FROM docker.elastic.co/logstash/logstash:7.1.1

COPY pipeline/logstash.conf /usr/share/logstash/pipeline/

WORKDIR /usr/share/logstash/pipeline/kafka
COPY config/kafka/client_jass.conf /usr/share/logstash/pipeline/kafka/

ADD config/logstash.yml /usr/share/logstash/config/
