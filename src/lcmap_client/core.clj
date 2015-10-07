(ns lcmap-client.core
  (:require [leiningen.core.project :as lein]))

(def version (System/getProperty "lcmap-client.version"))
(def project-url (:url (lein/read)))
(def user-agent (str "LCMAP REST Client/"
                     version
                     " (Clojure "
                     (clojure-version)
                     "; Java "
                     (System/getProperty "java.version")
                     ") (+"
                     project-url
                     ")"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
