(ns lcmap.client.models.ccdc-piped-processes
  (:require [lcmap.client.http :as http]
            [lcmap.client.models :as models]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str models/context "/ccdc/piped-processes"))

(defn get-resources [client & {keys [] :as args}]
  (http/get (str context "/")
            :client client
            :lcmap-opts (or args {})))

(defn run [client & {:keys [spectra x-val y-val start-time end-time
                            row col in-dir out-dir scene-list verbose] :as args}]
  (http/post context
             :client client
             :clj-http-opts {:form-params {:spectra spectra
                                           :x-val x-val
                                           :y-val y-val
                                           :start-time start-time
                                           :end-time end-time
                                           :row row
                                           :col col
                                           :in-dir in-dir
                                           :out-dir out-dir
                                           :scene-list scene-list
                                           :verbose verbose}}
             :lcmap-opts args))
