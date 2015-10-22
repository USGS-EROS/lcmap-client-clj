(ns lcmap-client.ccdc.model
  (:require [clojure.tools.logging :as log]
            [lcmap-client.ccdc :as ccdc]
            [lcmap-client.http :as http]
            [lcmap-client.lcmap :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str ccdc/context "/model"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
