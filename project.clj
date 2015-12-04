(defproject gov.usgs.eros/lcmap-client "0.1.0-dev"
  :description "Clojure Client for USGS LCMAP REST Service"
  :url "https://github.com/USGS-EROS/lcmap-client-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.memoize "0.5.8"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [clj-http "2.0.0"]
                 [dire "0.5.3"]
                 [leiningen-core "2.5.3"]
                 [clojure-ini "0.0.2"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :repl-options {:init-ns lcmap-client.dev})
