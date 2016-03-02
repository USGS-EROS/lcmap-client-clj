(defproject gov.usgs.eros/lcmap-client-clj "0.0.2-SNAPSHOT"
  :description "Clojure Client for USGS LCMAP REST Service"
  :url "https://github.com/USGS-EROS/lcmap-client-clj"
  :license {:name "NASA Open Source Agreement, Version 1.3"
            :url "http://ti.arc.nasa.gov/opensource/nosa/"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/core.memoize "0.5.8"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [com.stuartsierra/component "0.3.0"]
                 [clj-http "2.0.0"]
                 [dire "0.5.3"]
                 [leiningen-core "2.5.3"]
                 [clojure-ini "0.0.2"]
                 [twig "0.1.4"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :repl-options {:init-ns lcmap.client.dev})
