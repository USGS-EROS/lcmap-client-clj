(ns lcmap.client.jobs.ccdc
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.jobs :as jobs]
            [lcmap.client.lcmap :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str jobs/context "/ccdc"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
