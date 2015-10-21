(ns lcmap-client.ccdc
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str lcmap/context "/L3/CCDC"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
