(ns lcmap-client.config
  (:require [clojure.core.memoize :as memo]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojure-ini.core :as ini]
            [lcmap-client.lcmap :as lcmap])
  (:refer-clojure :exclude [read]))

(def filename "~/.usgs/lcmap.ini")

(defn -read []
  (memo/lu
    (fn []
      (log/debug "Memoizing LCMAP config ini ...")
      (ini/read-ini filename :keywordize? true))))

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

(defn get-value [key]
  (or (System/getenv (str "LCMAP_" (string/upper-case key)))
    ))

(defn get-username []
  (get-value :username))

(defn get-password []
  (get-value :password))
