(ns lcmap-client.l8.surface-reflectance
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as http]
            [lcmap-client.util :as util]
            [lcmap-client.l8.core :as l8]))

(def context (str l8/context "/SurfaceReflectance"))

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

(defn get-rod [& {:keys [point time band] :as args}]
  (let [args (apply dissoc args [:point :time :band])]
    (http/get (str context "/rod")
              :lcmap-opts args
              :request {:query-params {:point (util/point->str point)
                                       :time time
                                       :band band}})))

  
