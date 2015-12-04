(ns lcmap-client.config
  (:require [clojure.core.memoize :as memo]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojure-ini.core :as ini])
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

(defn make-env-name [key]
  (-> (name key)
      (string/replace #"-" "-")
      (string/upper-case)
      (#(str "LCMAP_" %))))

(defn get-env [key]
  (let [value (System/getenv (make-env-name key))]
    (when-not (= value "") value)))

(defn get-value [key]
  (let [cfg-data ((read) (keyword "LCMAP Client"))]
    (or (get-env key)
        (cfg-data key))))

(defn get-username []
  (get-value :username))

(defn get-password []
  (get-value :password))

(defn get-version
  ([]
    (get-version nil))
  ([version]
    (or (get-value :version) version)))

(defn get-endpoint []
  (get-value :endpoint))

(defn get-content-type
  ([]
    (get-content-type nil))
  ([content-type]
    (or (get-value :content-type) content-type)))

(defn get-log-level []
  (keyword (get-value :log-level)))
