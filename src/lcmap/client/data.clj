(ns lcmap.client.data
  (:require [lcmap.client.http :as http]
            [lcmap.client.lcmap :as lcmap]
            [lcmap.client.util :as util]))

(def context (str lcmap/context "/data"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

(defn get-tiles [& {:keys [point extent time band] :as args}]
  (let [[point extent] (util/update-point-extent point extent)
        args (apply dissoc args [:point :extent :time :band])]
    (http/get (str context "/tiles")
              :lcmap-opts args
              :request {:query-params {:point point
                                       :extent extent
                                       :time time
                                       :band band}})))

(defn get-scene [& {:keys [scene] :as args}]
  (let [args (apply dissoc args [:scene])]
    (http/get (str context "/scenes")
              :lcmap-opts args
              :request {:query-params {:scene scene}})))

(defn get-specs [& {:keys [band] :as args}]
  (let [args (apply dissoc args [:band])]
    (http/get (str context "/specs")
              :lcmap-opts args
              :request {:query-params {:band band}})))
