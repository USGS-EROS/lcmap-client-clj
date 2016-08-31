(defproject gov.usgs.eros/lcmap-client-clj "1.0.0-SNAPSHOT"
  :parent-project {
    :coords [gov.usgs.eros/lcmap-system "1.0.0-SNAPSHOT"]
    :inherit [
      :deploy-repositories
      :license
      :managed-dependencies
      :plugins
      :pom-addition
      :repositories
      :target
      ;; XXX The following can be un-commented once this issue is resolved:
      ;;     * https://github.com/achin/lein-parent/issues/3
      ;; [:profiles [:uberjar :dev]]
      ]}
  :description "Clojure Client for USGS LCMAP REST Service"
  :url "https://github.com/USGS-EROS/lcmap-client-clj"
  :dependencies [[org.clojure/clojure]
                 [org.clojure/core.async]
                 [org.clojure/data.json]
                 [org.clojure/tools.namespace]
                 [com.stuartsierra/component]
                 [clj-http]
                 [dire]
                 [leiningen-core]
                 ;; LCMAP Components
                 [gov.usgs.eros/lcmap-config]
                 [gov.usgs.eros/lcmap-logger]
                 ;; ClojureScript
                 [org.clojure/clojurescript]
                 [cljs-http]
                 [com.taoensso/timbre]]
  :source-paths ["src/clj" "src/cljc"]
  :repl-options {:init-ns lcmap.client.dev}
  :plugins [[lein-parent "0.3.0"]]
  ; :prep-tasks ["compile" ["cljsbuild" "once"]]
  ; :cljsbuild {
  ;   :repl-listen-port 9000
  ;   :repl-launch-commands {
  ;     "firefox-repl" ["firefox" "http://localhost:9001/lcmap-client.html"]
  ;     "terminal-repl" ["phantomjs" "http://localhost:9001/lcmap-client.html"]}
  ;   :builds [{:source-paths ["src/cljs" "src/cljc"]
  ;             :compiler {:output-to "resources/public/js/lcmap/lcmap-client.js"}}
  ;            {:id "dev"
  ;             :source-paths ["dev-resources/src/cljs" "dev-resources/src/cljc"]
  ;             :compiler {:output-to "es/public/js/lcmap/dev.js"}}]}
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
      :aliases {"slamhound" ["run" "-m" "slam.hound"]}
      :source-paths ["dev-resources/src/clj" "dev-resources/src/cljs"]}
    :testing {
      :env {
        :log-level :info}}})
