(ns lcmap-client.dev
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :as repl]
            [lcmap-client.auth :as auth]
            [lcmap-client.config :as config]
            [lcmap-client.http :as http]
            [lcmap-client.lcmap :as lcmap]))

(defn reset []
  (repl/refresh)
  (config/read :force-reload))

(def reload #'reset)
