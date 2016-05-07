(ns lcmap.client.config
  (:require [clojure.core.memoize :as memo]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojure-ini.core :as ini]
            [schema.core :as schema])
  (:refer-clojure :exclude [read]))

;; CLI options for config only, not command parameters for CLI tools!
(def opt-spec [])

(def http-cfg-schema
  {schema/Keyword schema/Str})

(def cfg-schema
  {:lcmap.client.http http-cfg-schema
   ;; permits configs maps for other components
   schema/Keyword schema/Any})

(def defaults
  {:ini (clojure.java.io/file (System/getenv "HOME") ".usgs" "lcmap.ini")
   :spec opt-spec
   :args *command-line-args*
   :schema cfg-schema})

;;; Original Implementation

(def env-prefix "LCMAP")
(def home (System/getProperty "user.home"))
(def config-dir ".usgs")
(def config-file "lcmap.ini")
(def empty-config {})

(declare serialize)

(defn file-exists?
  [file-name]
  (.exists (io/as-file file-name)))

(def -read
  (memo/lu
    (fn []
      (let [ini-file (io/file home config-dir config-file)]
        (if (file-exists? ini-file)
          (do
            (log/debug "Memoizing LCMAP config ini ...")
            (serialize
              (ini/read-ini ini-file :keywordize? true)))
          (do
            (log/warn "No client configuration file found; will use ENV")
            empty-config))))))

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
      (#(str env-prefix "_" %))))

(defn get-env [key]
  (let [value (System/getenv (make-env-name key))]
    (when-not (= value "") value)))

(defn get-section
  ([section-name]
    (get-section (read) section-name))
  ([cfg section-name]
    (cfg (keyword section-name))))

(defn get-value [key]
  (log/debug "Getting configuration value for key:" key)
  (let [cfg-data (get-section "LCMAP Client")]
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

(defn get-endpoint
  ([]
    (get-endpoint nil))
  ([endpoint]
    (or (get-value :endpoint) endpoint)))

(defn get-content-type
  ([]
    (get-content-type nil))
  ([content-type]
    (or (get-value :content-type) content-type)))

(defn get-log-level []
  (keyword (get-value :log-level)))

(defn serialize-logging-namespaces [client-cfg]
  (into client-cfg
        {:logging-namespaces
          (->> (string/split (:logging-namespaces client-cfg) #" ")
               (map symbol)
               (into []))}))

(defn serialize-loglevel [client-cfg]
  (into client-cfg
    {:log-level
      (-> (:log-level client-cfg)
          (string/lower-case)
          (keyword))}))

(defn update-section
  ([section-name values]
    (update-section (read) section-name values))
  ([top-cfg section-name values]
    (into top-cfg {(keyword section-name) values})))

(defn serialize-section
  ([update-data]
    (serialize-section (read) update-data))
  ([top-cfg [section-name update-functions]]
    (let [section-cfg (get-section top-cfg section-name)
          section-vals (reduce #(%2 %1) section-cfg update-functions)]
      (update-section top-cfg section-name section-vals))))

(defn serialize
  ([]
    (serialize (read)))
  ([top-cfg]
    (serialize top-cfg
               [["LCMAP Client" [#'serialize-logging-namespaces
                                 #'serialize-loglevel]]]))
  ([top-cfg update-data]
    (into {} (map #(serialize-section top-cfg %) update-data))))
