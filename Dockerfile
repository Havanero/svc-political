FROM openjdk:8

SHELL ["/bin/bash", "-c"]

ENV SBT_VERSION 1.5.2
ENV BASEDIR /opt/scala
ENV APPDIR ${BASEDIR}/app

RUN mkdir -p $APPDIR

COPY . ${APPDIR} 

RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" |  tee /etc/apt/sources.list.d/sbt.list
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian /" |  tee /etc/apt/sources.list.d/sbt_old.list
RUN curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" |  apt-key add

RUN set -ex \
    && RUN_DEPS=" \
        sbt \
    " \
    && mkdir -p /var/log/scala && \
    apt-get update && apt-get install -y --no-install-recommends $RUN_DEPS \
    && apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false $BUILD_DEPS \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* 


WORKDIR ${APPDIR} 

RUN sbt update 

EXPOSE 9000
ENTRYPOINT ["sbt", "start"]
