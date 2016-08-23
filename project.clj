(defproject gov.usgs.eros/lcmap-client-clj "1.0.0-SNAPSHOT"
  :description "Clojure Client for USGS LCMAP REST Service"
  :url "https://github.com/USGS-EROS/lcmap-client-clj"
  :license {:name "NASA Open Source Agreement, Version 1.3"
            :url "http://ti.arc.nasa.gov/opensource/nosa/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.stuartsierra/component "0.3.1"]
                 [clj-http "3.1.0"]
                 [dire "0.5.4"]
                 [leiningen-core "2.6.1"
                   :exclusions [org.apache.maven.wagon/wagon-provider-api]]
                 ;; LCMAP Components
                 [gov.usgs.eros/lcmap-config "1.0.0-SNAPSHOT"]
                 [gov.usgs.eros/lcmap-logger "1.0.0-SNAPSHOT"
                   :exclusions [org.slf4j/slf4j-api
                                cheshire
                                com.fasterxml.jackson.core/jackson-core
                                com.fasterxml.jackson.dataformat/jackson-dataformat-cbor
                                com.fasterxml.jackson.dataformat/jackson-dataformat-smile]]]
  :repl-options {:init-ns lcmap.client.dev}
  :codox {
    :project {
      :name "lcmap.client (Clojure)"
      :description "LCMAP Client Library for Clojure"}
    :namespaces [#"^lcmap.client\."]
    :output-path "docs/master/current"
    :doc-paths ["docs/source"]
    :metadata {
      :doc/format :markdown
      :doc "Documentation forthcoming"}}
  :profiles {
    :uberjar {:aot :all}
    :dev {
      :env {
        :log-level :trace
        :logging-namespaces [lcmap.client]}
      :dependencies [[org.clojure/tools.namespace "0.3.0-alpha3"]
                     [pandect "0.6.0"]
                     [slamhound "1.5.5"]]
      :plugins [[lein-codox "0.9.6"]]
      :aliases {"slamhound" ["run" "-m" "slam.hound"]}
      :source-paths ["dev-resources/src"]}
    :testing {
      :plugins [[lein-kibit "0.1.2" :exclusions [org.clojure/clojure]]
                [jonase/eastwood "0.2.3"]]
      :env {
        :log-level :info}}})
