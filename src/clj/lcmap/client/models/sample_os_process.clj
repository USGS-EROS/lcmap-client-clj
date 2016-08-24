(ns lcmap.client.models.sample-os-process
  (:require [lcmap.client.http :as http]
            [lcmap.client.models :as models]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str models/context "/sample/os-process"))

(defn get-resources [client & {keys [] :as args}]
  (http/get (str context "/")
            :client client
            :lcmap-opts (or args {})))

(defn run [client & {:keys [year delay] :as args}]
  (http/post context
             :client client
             :clj-http-opts {:form-params {:year year :delay delay}}
             :lcmap-opts args))
