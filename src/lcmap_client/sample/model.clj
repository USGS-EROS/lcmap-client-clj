(ns lcmap-client.sample.model
  (:require [clojure.tools.logging :as log]
            [lcmap-client.sample :as sample]
            [lcmap-client.http :as http]
            [lcmap-client.lcmap :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str sample/context "/model"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

(defn run [job-id & {:keys [] :as args}]
  (http/post (str context "/run/" job-id)
             :lcmap-opts args))
