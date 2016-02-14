(ns lcmap-client.models.sample-piped-processes
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as http]
            [lcmap-client.models :as models]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str models/context "/sample/piped-processes"))

(defn get-resources [client & {keys [] :as args}]
  (http/get (str context "/")
            :client client
            :lcmap-opts (or args {})))

(defn run [client & {:keys [number count bytes words lines] :as args}]
  (http/post context
             :client client
             :clj-http-opts {:form-params {:number number
                                           :count count
                                           :bytes bytes
                                           :words words
                                           :lines lines}}
             :lcmap-opts args))
