(ns lcmap.client.models.ndvi
  (:require [lcmap.client.http :as http]
            [lcmap.client.models :as models]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str models/context "/ndvi"))

(defn get-resources [client & {keys [] :as args}]
  (http/get (str context "/")
            :client client
            :lcmap-opts (or args {})))

(defn run [client & {:keys [arg1 arg2] :as args}]
  (http/post context
             :client client
             :clj-http-opts {:form-params {:arg-1 arg1 :arg-2 arg2}}
             :lcmap-opts args))
