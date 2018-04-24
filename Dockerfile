FROM clojure:lein-2.8.1
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD ["lein", "run"]
