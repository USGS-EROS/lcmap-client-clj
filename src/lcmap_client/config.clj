(ns lcmap-client.config
  (:require [clojure.core.memoize :as memo]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojure-ini.core :as ini]
            [lcmap-client.lcmap :as lcmap])
  (:refer-clojure :exclude [read]))

(def home (System/getProperty "user.home"))
(def ini-file (io/file home ".usgs" "lcmap.ini"))

(def -read
  (memo/lu
    (fn []
      (log/debug "Memoizing LCMAP config ini ...")
      (ini/read-ini ini-file :keywordize? true))))

(defn read
  ([]
    (-read))
  ([arg]
    (if-not (= arg :force-reload)
      ;; The only arg we've defined so far is :force-reload -- anything other
      ;; than that, simply ignore.
      (-read)
      (do (memo/memo-clear! -read)
          (-read)))))

(defn get-env [key]
  (let [value (System/getenv key)]
    (if (= value "")
      nil
      value)))

(defn get-value [key]
  (let [cfg-data ((read) (keyword "LCMAP Client"))]
    (or (get-env (str "LCMAP_" (string/upper-case (name key))))
      (cfg-data key))))

(defn get-username []
  (get-value :username))

(defn get-password []
  (get-value :password))
