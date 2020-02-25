(ns practice01.cfg
  (:require [clojure.edn :as edn]))

(defn read-cfg [path]
  (edn/read-string (slurp path)))
